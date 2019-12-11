/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: SessionUtil
 * Author:   zhanglei1102
 * Date:     2019/8/2 17:33
 * Description: session 工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.plugin.util;

import org.springframework.util.CollectionUtils;
import top.buukle.common.call.PageResponse;
import top.buukle.security.entity.Role;
import top.buukle.security.entity.User;
import top.buukle.security.entity.vo.SelectTreeNodeResult;
import top.buukle.security.plugin.constants.SecurityInterceptorConstants;
import top.buukle.security.plugin.enums.SecurityExceptionEnum;
import top.buukle.security.plugin.exception.SecurityPluginException;
import top.buukle.util.NumberUtil;
import top.buukle.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @description 〈session 工具类〉
 * @author zhanglei1102
 * @create 2019/8/2
 * @since 1.0.0
 */
public class SessionUtil {


    /**  【当前用户】 拥有菜单信息在session中的key*/
    public static final String USER_MENU_TREE_KEY = "USER_MENU_TREE_KEY";
    /**  【当前用户】 下辖角色信息在session中的key*/
    public static final String USER_ROLE_SUB_MAP_KEY = "USER_ROLE_SUB_MAP_KEY";
    /**  【当前用户】 下拥有url信息在session中的key*/
    public static final String USER_URL_LIST_KEY = "USER_URL_LIST_KEY";
    /**  【当前用户】 拥有部门信息在session中的key*/
    public static final String USER_DEPT_SUB_LIST_KEY = "USER_DEPT_LIST_KEY";
    /**  【当前用户】 拥有部门ID信息在session中的key*/
    public static final String USER_DEPT_SUB_ID_LIST_KEY = "USER_DEPT_LIST_KEY";


    /**  【当前用户】 信息在session中的key*/
    public static final String USER_SESSION_KEY = "USER_SESSION_KEY";
    /** sessionContext的key*/
    public static final String SECURITY_SESSION_CONTEXT_KEY_PREFIX = "SECURITY_SESSION_CONTEXT_KEY_PREFIX:";

    /**
     * @description 缓存session【当前用户】信息
     * @param user
     * @param request
     * @param response
     * @return void
     * @Author elvin
     * @Date 2019/8/3
     */
    public static HttpSession cacheUser(User user, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String cookieValue = session.getId();
        CookieUtil.writeUserCookie(cookieValue,response,SecurityInterceptorConstants.LOGIN_COOKIE_DOMAIN, user.getLoginStrategy() ==null ? NumberUtil.INTEGER_ONE_MINUTES_SECOND * 3 : NumberUtil.INTEGER_ONE_WEEK_SECOND);
        session.setAttribute(USER_SESSION_KEY,user);
        return session;
    }

    /**
     * @description 获取【当前用户】session信息
     * @param request
     * @param response
     * @return top.buukle.security.entity.User
     * @Author zhanglei1102
     * @Date 2019/8/2
     */
    public static User getUser(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session != null){
            String userCookie = CookieUtil.getUserCookie(request);
            if(StringUtil.isNotEmpty(userCookie)){
                User user = null;
                try{
                    Object obj = session.getAttribute(USER_SESSION_KEY);
                    if(obj instanceof String){
                        user = new User();
                        user.setUserId((String) obj);
                    }
                    if(obj instanceof User){
                        user = (User) session.getAttribute(USER_SESSION_KEY);
                    }
                }catch(Exception e){
                    throw new SecurityPluginException(SecurityExceptionEnum.AUTH_WRONG_OTHER_LOGIN);
                }
                if(null == user){
                    CookieUtil.delUserCookie(response, SecurityInterceptorConstants.LOGIN_COOKIE_DOMAIN);
                    // 超时
                    throw new SecurityPluginException(SecurityExceptionEnum.AUTH_WRONG_OUT_OF_TIME);
                }
                return user;
            }
        }
        return null;
    }

    /**
     * @description 获取【当前用户】session信息
     * @param request
     * @param response
     * @return top.buukle.security.entity.User
     * @Author zhanglei1102
     * @Date 2019/8/2
     */
    public static User getOperator(HttpServletRequest request, HttpServletResponse response) {
        User user = getUser(request, response);
        if(user == null){
            throw new SecurityPluginException(SecurityExceptionEnum.AUTH_WRONG_OUT_OF_TIME);
        }
        return user;
    }

    /**
     * @description 登出【当前用户】
     * @param request
     * @param response
     * @return void
     * @Author elvin
     * @Date 2019/8/15
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.delUserCookie(response,SecurityInterceptorConstants.LOGIN_COOKIE_DOMAIN);
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate();
        }
    }

    /**
     * @description 获取【当前用户】 拥有管理员角色的应用
     * @param request
     * @return java.util.List<top.buukle.security.entity.Application>
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    public static PageResponse getUserApplication(HttpServletRequest request) {

        Map<String,List<Role>> userSubRolesMap = (Map<String, List<Role>>) SessionUtil.get(request, SessionUtil.USER_ROLE_SUB_MAP_KEY);
        List<Role> roles = new ArrayList<>();
        for (String applicationCode: userSubRolesMap.keySet()) {
            if(!CollectionUtils.isEmpty(userSubRolesMap.get(applicationCode))){
                roles.addAll(userSubRolesMap.get(applicationCode));
            }
        }

        Set<SelectTreeNodeResult> applications = new HashSet<>();
        for (Role role: roles) {
            if(role.getPid().equals(0)){
                SelectTreeNodeResult application = new SelectTreeNodeResult();
                application.setTitle(role.getBak01());
                application.setId(role.getApplicationId());
                applications.add(application);
            }
        }
        PageResponse commonResponse = new PageResponse.Builder().build(new ArrayList<>(applications),0,0,applications.size());
        return commonResponse;
    }

    /**
     * @description 获取【当前用户】部门Id
     * @param request
     * @return top.buukle.security.entity.Role
     * @Author elvin
     * @Date 2019/8/19
     */
    public static Integer getUserDeptId(HttpServletRequest request) {

        return null;
    }

    /**
     * @description 获取【当前用户】下辖部门id列表
     * @param request
     * @return java.util.List<java.lang.Integer>
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    public static List<Integer> getUserSubDeptIdList(HttpServletRequest request) {

        return null;
    }

    /**
     * @description 获取【当前用户】下辖角色id列表
     * @param request
     * @return java.util.List<java.lang.Integer>
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    public static List<Integer> getUserSubRolesIdList(HttpServletRequest request) {
        Map<String,List<Role>> userSubRolesMap = (Map<String, List<Role>>) SessionUtil.get(request, SessionUtil.USER_ROLE_SUB_MAP_KEY);
        List<Role> roles = new ArrayList<>();
        for (String applicationCode: userSubRolesMap.keySet()) {
            if(!CollectionUtils.isEmpty(userSubRolesMap.get(applicationCode))){
                roles.addAll(userSubRolesMap.get(applicationCode));
            }
        }
        List<Integer> operatorSubRoleIds = new ArrayList<>();
        for (Role role: roles) {
            operatorSubRoleIds.add(role.getId());
        }
        return operatorSubRoleIds;
    }

    /**
     * @description 获取session资源
     * @param request
     * @param key
     * @return java.util.List<java.lang.String>
     * @Author zhanglei1102
     * @Date 2019/8/2
     */
    public static Object get(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * @description 缓存session资源
     * @param request
     * @param key
     * @param object
     * @return void
     * @Author elvin
     * @Date 2019/8/3
     */
    public static void cache(HttpServletRequest request, String key, Object object) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.setAttribute(key,object);
        }
    }

    /**
     * @description 获取指定用户的超时时间
     * @param userInfo
     * @return int
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    public static int getUserExpire(User userInfo) {
        return (userInfo.getLoginStrategy() == null || userInfo.getLoginStrategy() == 0)  ? NumberUtil.INTEGER_ONE_MINUTES_SECOND * 6 : NumberUtil.INTEGER_ONE_WEEK_SECOND;
    }

}