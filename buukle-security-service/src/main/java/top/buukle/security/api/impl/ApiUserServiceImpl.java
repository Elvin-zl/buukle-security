package top.buukle.security.api.impl;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.code.BaseReturnEnum;
import top.buukle.common.exception.CommonException;
import top.buukle.security.api.ApiUserService;
import top.buukle.security.dao.*;
import top.buukle.security.entity.*;
import top.buukle.security.entity.constants.DeptEnums;
import top.buukle.security.entity.vo.DeptSessionVo;
import top.buukle.security.entity.vo.MenuTreeNode;
import top.buukle.security.plugin.enums.SecurityExceptionEnum;
import top.buukle.security.plugin.exception.SecurityPluginException;
import top.buukle.security.plugin.util.SessionUtil;
import top.buukle.security.entity.constants.MenuEnums;
import top.buukle.security.entity.constants.RoleEnums;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
* @author elvin
* @description UserService实现类
*/
@Service
public class ApiUserServiceImpl implements ApiUserService{


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private ButtonMapper buttonMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String SPRING_SESSION_KEY_PREFIX = "spring:session:sessions:";
    private static final String SPRING_SESSION_KEY_EXPIRE_PREFIX = "spring:session:sessions:expires:";
    public static final String SESSION_ATTR_PREFIX = "sessionAttr:";
    /**
     * @description 内部登陆
     * @param user
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/3
     */
    @Override
    public CommonResponse innerLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        this.validationParam(user);
        List<User> users = userMapper.selectByUsernamePwd(user.getUsername(),user.getPassword());
        if(CollectionUtils.isEmpty(users) || users.size() != 1){
            throw new SecurityPluginException(SecurityExceptionEnum.LOGIN_WRONG_USER_NULL);
        }
        User userInfo = users.get(0);
        User user1 = new User();
        user1.setId(userInfo.getId());
        user1.setGmtLastLogin(new Date());
        userMapper.updateByPrimaryKeySelective(user1);
        // 剔除已经在线的会话
        this.kickOutUser(userInfo.getUserId(),new User(SecurityExceptionEnum.AUTH_WRONG_COOKIE_OTHER.getCode()),SessionUtil.getUserExpire(userInfo));
        // 创建新的会话
        SessionUtil.cacheUser(userInfo, request, response);
        // 更新用户活跃域
        this.registerInSessionContext(request,userInfo.getUserId(),SessionUtil.getUserExpire(user));
        // 缓存用户信息
        this.sessionUserResource(request,userInfo,false);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 缓存用户信息
     * @param request
     * @param userInfo
     * @param isUpdate
     * @return void
     * @Author elvin
     * @Date 2019/8/23
     */
    @Override
    public void sessionUserResource(HttpServletRequest request, User userInfo, boolean isUpdate) {
        // 查询用户拥有菜单资源目录
        List<Menu> menuList = menuMapper.getUserMenuListByUserId(userInfo.getUserId());
        if(!CollectionUtils.isEmpty(menuList) || isUpdate){
            List<Menu> menuDisplay = new ArrayList<>();
            // 筛选可见菜单
            for (Menu menu: menuList) {
                // 菜单类型 && 菜单可见 && 排序
                if(MenuEnums.type.MENU.value().equals(menu.getType()) && MenuEnums.display.DISPLAY_BLOCK.value().equals(menu.getDisplay())){
                    menuDisplay.add(menu);
                }
            }
            // 组装用户每个应用的可见菜单
            MenuExample menuExample = new MenuExample();
            MenuExample.Criteria menuExampleCriteria = menuExample.createCriteria();
            menuExampleCriteria.andPidEqualTo(0);
            List<Menu> menus = menuMapper.selectByExample(menuExample);
            Map<String,MenuTreeNode> userApplicationMenuDisplayed = new HashMap<>();
            if(!CollectionUtils.isEmpty(menus)){
                for (Menu menu: menus) {
                    if(menu.getPid().equals(0)){
                        MenuTreeNode rootNodeDisplay = new MenuTreeNode();
                        BeanUtils.copyProperties(menu,rootNodeDisplay);
                        Application application = applicationMapper.selectByPrimaryKey(menu.getApplicationId());
                        // 组装应用下用户可见菜单树
                        this.assUserMenuTree(rootNodeDisplay,menuDisplay);
                        userApplicationMenuDisplayed.put(application == null ? "" : application.getCode(),rootNodeDisplay);
                    }
                }
            }

            // 查询所有角色
            RoleExample roleExample = new RoleExample();
            RoleExample.Criteria roleExampleCriteria = roleExample.createCriteria();
            roleExampleCriteria.andStatusEqualTo(RoleEnums.status.PUBLISED.value());
            List<Role> allRoles = roleMapper.selectByExample(roleExample);
            // 查询用户角色
            List<Role> userRoles = roleMapper.selectUserRoles(userInfo.getUserId());
            // 组装用户下辖角色
            Map<String, List<Role>> userSubRoleMap = this.assUserSubRoleMap(userRoles, allRoles);

            // 查询所有部门
            DeptExample deptExample = new DeptExample();
            DeptExample.Criteria criteria = deptExample.createCriteria();
            criteria.andStatusEqualTo(DeptEnums.status.PUBLISED.value());
            List<Dept> allDept = deptMapper.selectByExample(deptExample);
            // 查询用户部门
            DeptSessionVo userDept = deptMapper.selectUserDept(userInfo.getUserId());
            // 初始化并组装用户下辖部门
            List<DeptSessionVo> userSubDeptList = new ArrayList<>();
            List<Integer> userSubDeptIdList = new ArrayList<>();
            if(DeptEnums.leader.LOCAL_LEADER.level().equals(userDept.getLeader()) || DeptEnums.leader.SELF_LEADER.level().equals(userDept.getLeader())){
                userSubDeptList.add(userDept);
                userSubDeptIdList.add(userDept.getId());
            }else if(DeptEnums.leader.PASS_LEADER.level().equals(userDept.getLeader())){
                this.assUserSubDeptList(userDept, allDept,userSubDeptList,userSubDeptIdList);
            }else{
                throw new CommonException(BaseReturnEnum.FAILED,"登陆失败,用户部门信息非法!");
            }

            if(isUpdate){
                // 刷新用户可见菜单数组(用于菜单展示)
                this.refreshSession(userInfo.getUserId(),SessionUtil.USER_MENU_TREE_KEY,userApplicationMenuDisplayed,SessionUtil.getUserExpire(userInfo));
                // 刷新用户下辖部门列表(用于编辑部门)
                this.refreshSession(userInfo.getUserId(),SessionUtil.USER_DEPT_SUB_LIST_KEY,userSubDeptList,SessionUtil.getUserExpire(userInfo));
                // 刷新用户下辖部门id列表(用于数据权限)
                this.refreshSession(userInfo.getUserId(),SessionUtil.USER_DEPT_SUB_ID_LIST_KEY,userSubDeptIdList,SessionUtil.getUserExpire(userInfo));
                // 刷新用户下辖角色映射(用于编辑角色)
                this.refreshSession(userInfo.getUserId(),SessionUtil.USER_ROLE_SUB_MAP_KEY,userSubRoleMap,SessionUtil.getUserExpire(userInfo));
                // 刷新用户所有资源url清单(用于权限校验)
                this.refreshSession(userInfo.getUserId(),SessionUtil.USER_URL_LIST_KEY,this.assUserMenuUrlList(menuList,this.getUserButtonList(userRoles)),SessionUtil.getUserExpire(userInfo));
            }else{
                // 刷新用户可见菜单数组(用于菜单展示)
                SessionUtil.cache(request,SessionUtil.USER_MENU_TREE_KEY,userApplicationMenuDisplayed);
                // 刷新用户下辖部门列表(用于编辑部门)
                SessionUtil.cache(request,SessionUtil.USER_DEPT_SUB_LIST_KEY,userSubDeptList);
                // 刷新用户下辖部门id列表(用于数据权限)
                SessionUtil.cache(request,SessionUtil.USER_DEPT_SUB_ID_LIST_KEY,userSubDeptIdList);
                // 刷新用户下辖角色映射(用于编辑角色)
                SessionUtil.cache(request,SessionUtil.USER_ROLE_SUB_MAP_KEY,userSubRoleMap);
                // 刷新用户所有资源url清单(用于权限校验)
                SessionUtil.cache(request,SessionUtil.USER_URL_LIST_KEY,this.assUserMenuUrlList(menuList,this.getUserButtonList(userRoles)));
            }
        }
    }

    private void assUserSubDeptList(DeptSessionVo userDept, List<Dept> allDept, List<DeptSessionVo> userSubDeptList, List<Integer> userSubDeptIdList) {
        userSubDeptList.add(userDept);
        userSubDeptIdList.add(userDept.getId());
        for (Dept dept: allDept) {
           if(dept.getPid().equals(userDept.getId())){
               DeptSessionVo vo = new DeptSessionVo();
               BeanUtils.copyProperties(dept,vo);
               assUserSubDeptList(vo,allDept,userSubDeptList, userSubDeptIdList);
           }
        }
    }

    /**
     * @description 删除用户会话
     * @param userId
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void deleteSession(String userId) {
        stringRedisTemplate.delete(SPRING_SESSION_KEY_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId));

    }

    /**
     * @description 剔除用户
     * @param userId
     * @param user
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void kickOutUser(String userId, User user, int expire) {
        String oldSid = stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
        if(!StringUtil.isEmpty(oldSid)){
            stringRedisTemplate.opsForHash().put(SPRING_SESSION_KEY_PREFIX + oldSid, SESSION_ATTR_PREFIX + SessionUtil.USER_SESSION_KEY, JsonUtil.toJSONString(user, SerializerFeature.WriteClassName));
            stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + oldSid,expire, TimeUnit.SECONDS);
        }
    }

    /**
     * @description 刷新用户会话
     * @param userId
     * @param k
     * @param v
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void refreshSession(String userId, String k, Object v, int expire) {
        String oldSid = stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
        if(!StringUtil.isEmpty(oldSid)){
            stringRedisTemplate.opsForHash().put(SPRING_SESSION_KEY_PREFIX + oldSid, SESSION_ATTR_PREFIX + k, JsonUtil.toJSONString(v, SerializerFeature.WriteClassName));
            stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + oldSid,expire,TimeUnit.SECONDS);
        }
    }

    /**
     * @description 刷新expire
     * @param userId
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void refreshDDL(String userId, int expire) {
        stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId),expire,TimeUnit.SECONDS);
        stringRedisTemplate.expire(SPRING_SESSION_KEY_EXPIRE_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId),expire,TimeUnit.SECONDS);
    }

    /**
     * @description 注册用户
     * @param request
     * @param userId
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void registerInSessionContext(HttpServletRequest request, String userId, Integer expire) {
        stringRedisTemplate.opsForValue().set(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId, request.getSession().getId());
        stringRedisTemplate.expire(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId, expire, TimeUnit.SECONDS);
    }

    /**
     * @description 删除用户会话key
     * @param userId
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public void removeFromSessionContext(String userId) {
        stringRedisTemplate.delete(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
    }

    /**
     * @description 获取在线人数
     * @param
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @Override
    public CommonResponse countSessionContext() {
        CommonResponse commonResponse = new CommonResponse.Builder().buildSuccess();
        commonResponse.getHead().setMsg(stringRedisTemplate.keys(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + "*").size()+StringUtil.EMPTY);
        return commonResponse;
    }

    /**
     * @description 组装用户的应用,下辖角色映射目录
     * @param allRoles
     *@param userRoles  @return java.lang.Object
     * @Author elvin
     * @Date 2019/8/19
     */
    private Map<String,List<Role>> assUserSubRoleMap(List<Role>  userRoles, List<Role> allRoles) {
        Map<String,List<Role>> map = new HashMap<>();
        Application application;
        for (Role allRole: allRoles) {
            application = applicationMapper.selectByPrimaryKey(allRole.getApplicationId());
            if(application != null){
                List<Role> subRoleList = new ArrayList<>();
                for (Role userRole: userRoles) {
                    if(userRole.getApplicationId().equals(application.getId())){
                        this.getUserSubRoleWithApplicationCode(subRoleList,userRole,allRoles);
                    }
                }
                map.put(application.getCode(),subRoleList);
            }
        }
        return map;
    }

    /**
     * @description 获取用户下辖角色
     * @param subRoleList
     * @param userRole
     * @param allRoles
     * @return void
     * @Author elvin
     * @Date 2019/8/19
     */
    private void getUserSubRoleWithApplicationCode(List<Role> subRoleList, Role userRole, List<Role> allRoles) {
        subRoleList.add(userRole);
        for (Role role: allRoles) {
            if(role.getPid().equals(userRole.getId())){
                getUserSubRoleWithApplicationCode(subRoleList,role,allRoles);
            }
        }
    }

    /**
     * @description 组装用户的应用,角色映射目录
     * @param userRoles
     * @return java.util.Map<java.lang.String,top.buukle.security.entity.Role>
     * @Author elvin
     * @Date 2019/8/17
     */
    private Map<String,Role> assUserRoleMap(List<Role> userRoles) {

        return null;
    }

    /**
     * @description 组装用户所有资源url清单
     * @param menuList
     * @param buttonList
     * @return java.util.List<java.lang.String>
     * @Author elvin
     * @Date 2019/8/3
     */
    private List<String> assUserMenuUrlList(List<Menu> menuList, List<Button> buttonList) {
        List<String> list = new ArrayList<>();
        for (Menu menu: menuList) {
            list.add(menu.getUrl());
        }
        for (Button button: buttonList) {
            list.add(button.getUrl());
        }
        return list;
    }

    /**
     * @description 组装用户按钮目录
     * @param userRoles
     * @return java.util.List<top.buukle.security.entity.Button>
     * @Author elvin
     * @Date 2019/8/3
     */
    private List<Button> getUserButtonList(List<Role> userRoles) {
        List<Button> buttons = null;
        if(!CollectionUtils.isEmpty(userRoles)){
            List<Integer> roleIds=new ArrayList<>();
            for (Role role: userRoles) {
                roleIds.add(role.getId());
            }
            buttons = buttonMapper.getUserButtonList(roleIds);
        }
        return CollectionUtils.isEmpty(buttons) ? new ArrayList<>() : buttons;
    }

    /**
     * @description 组装菜单树
     * @param node
     * @param menuList
     * @return void
     * @Author elvin
     * @Date 2019/8/3
     */
    private void assUserMenuTree(MenuTreeNode node, List<Menu> menuList) {
        List<MenuTreeNode> nodeList = new ArrayList<>();
        for (Menu menu: menuList) {
            if(menu.getPid().equals(node.getId())){
                MenuTreeNode menuTreeNode = new MenuTreeNode();
                BeanUtils.copyProperties(menu,menuTreeNode);
                assUserMenuTree(menuTreeNode,menuList);
                nodeList.add(menuTreeNode);
            }
        }
        node.setSubMenuList(nodeList);
    }

    /**
     * @description 验证参数
     * @param user
     * @return void
     * @Author elvin
     * @Date 2019/8/3
     */
    private void validationParam(User user) {
        if(null == user){
            throw new SecurityPluginException(SecurityExceptionEnum.LOGIN_WRONG_PARAM_EXCEPTION);
        }
        if(StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())){
            throw new SecurityPluginException(SecurityExceptionEnum.LOGIN_WRONG_PARAM_EXCEPTION,"用户名或密码不能为空！");
        }
    }
}