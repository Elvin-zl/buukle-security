package top.buukle.security .service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.common.call.vo.FuzzyVo;
import top.buukle.common.status.StatusConstants;
import top.buukle.common.mvc.CommonMapper;
import top.buukle.security .dao.DeptMapper;
import top.buukle.security.entity.User;
import top.buukle.security .entity.Dept;
import top.buukle.security .entity.DeptExample;
import top.buukle.common.mvc.BaseQuery;
import top.buukle.security.entity.vo.*;
import top.buukle.security.plugin.util.SessionUtil;
import top.buukle.security .service.DeptService;
import top.buukle.security.service.constants.SystemReturnEnum;
import top.buukle.security .entity.constants.DeptEnums;
import top.buukle.security .service.exception.SystemException;
import top.buukle.security .service.util.ConvertHumpUtil;
import top.buukle.util.DateUtil;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @author elvin
* @description DeptService实现类
*/
@Service("deptService")
public class DeptServiceImpl implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private CommonMapper commonMapper;

    /**
     * 分页获取列表
     * @param query 查询对象
     * @return PageResponse
     */
    @Override
    public PageResponse getPage(BaseQuery query) {
        PageHelper.startPage(((DeptQuery)query).getPage(),((DeptQuery)query).getPageSize());
        List<Dept> list = deptMapper.selectByExample(this.assExampleForList(((DeptQuery)query)));
        PageInfo<Dept> pageInfo = new PageInfo<>(list);
        return new PageResponse.Builder().build(list,pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal());
    }

    /**
     * 根据id删除记录状态数据
     * @param id 删除数据实例
     * @param request httpServletRequest
     * @param response
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse delete(Integer id, HttpServletRequest request, HttpServletResponse response) {
        if(deptMapper.updateByPrimaryKeySelective(this.assQueryForUpdateStatus(id, DeptEnums.status.DELETED.value(),request,response)) != 1){
            throw new SystemException(SystemReturnEnum.DELETE_INFO_EXCEPTION);
        }
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 批量删除
     * @param ids
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/4
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse deleteBatch(String ids, HttpServletRequest request, HttpServletResponse response) {
        String trimIds = ids.trim();
        String[] split = trimIds.split(",");
        if(StringUtil.isEmpty(trimIds) || split.length<1){
            throw new SystemException(SystemReturnEnum.USER_BATCH_DELETE_IDS_NULL);
        }
        List<Integer> idList = JsonUtil.parseArray(JsonUtil.toJSONString(Arrays.asList(split)), Integer.class);
        DeptExample deptExample = new DeptExample();
        DeptExample.Criteria criteria = deptExample.createCriteria();
        criteria.andIdIn(idList);
        Dept dept = new Dept();

        User operator = SessionUtil. getOperator(request, response);
        dept.setGmtModified(new Date());
        dept.setModifier(operator.getUsername());
        dept.setModifierCode(operator.getUserId());

        dept.setStatus(DeptEnums.status.DELETED.value());
        deptMapper.updateByExampleSelective(dept,deptExample);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 根据id查询
     * @param id
     * @return top.top.buukle.security .entity.Dept
     * @Author elvin
     * @Date 2019/8/4
     */
    @Override
    public DeptCrudModelVo selectByPrimaryKeyForCrud(HttpServletRequest httpServletRequest, Integer id) {
        if(id == null){
            return new DeptCrudModelVo();
        }
        DeptCrudModelVo deptCrudModelVo = new DeptCrudModelVo();
        Dept dept = deptMapper.selectByPrimaryKey(id);
        if(dept != null){
            BeanUtils.copyProperties(dept,deptCrudModelVo);
            if(dept.getPid() != null){
                if(dept.getPid() == 0){
                    deptCrudModelVo.setSuperName("root");
                }else{
                    Dept pDept = deptMapper.selectByPrimaryKey(dept.getPid());
                    deptCrudModelVo.setSuperName(pDept.getDeptName());
                }
            }
        }
        return deptCrudModelVo;
    }

    /**
     * @description 新增或者修改
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/5
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse saveOrEdit(DeptQuery query, HttpServletRequest request, HttpServletResponse response) {

        User operator = SessionUtil.getOperator(request, response);
//        query.setCreatorRoleId(SessionUtil.getUserRoleId(request, SpringContextUtil.getBean(Environment.class).getProperty("spring.application.name")).getId());
        query.setCreator(operator.getUsername());
        query.setCreatorCode(operator.getUserId());

        validateParamForSaveOrEdit(query);
        // 新增
        if(query.getId() == null){
            this.save(query,request,response);
        }
        // 更新
        else{
            this.update(query,request,response);
        }
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 获取部门树
     * @param applicationId
     * @param request
     * @param response
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/12/11
     */
    @Override
    public PageResponse getDeptTree(Integer applicationId, HttpServletRequest request, HttpServletResponse response) {
        // 获取操作者部门列表
        List<Integer> operatorSubDeptIds = SessionUtil.getUserSubDeptIdList(request);

        DeptExample applicationExample = new DeptExample();
        List<Dept> depts = deptMapper.selectByExample(applicationExample);
        SelectTreeNodeResult rootNode = new SelectTreeNodeResult();
        rootNode.setId(0);
        rootNode.setTitle("root");
        rootNode.setSpread(true);
        rootNode.setDisabled(false);
        List<SelectTreeNodeResult> nodes = new ArrayList<>();
        nodes.add(rootNode);
        this.findChildren(rootNode,depts,operatorSubDeptIds);
        return new PageResponse.Builder().build(nodes,0,0,0);
    }

    /**
     * @description 获取用户部门列表
     * @param userId
     * @param request
     * @param response
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/12/11
     */
    @Override
    public PageResponse getUserDept(String userId, HttpServletRequest request, HttpServletResponse response) {
        // 查询部门列表
        DeptExample applicationExample = new DeptExample();
        List<Dept> depts = deptMapper.selectByExample(applicationExample);
        // 从 session 查询操作者下辖的部门id
        List<Integer> userSubDeptIdList = SessionUtil.getUserSubDeptIdList(request);
        // 从 session 查询操作者所属部门
        DeptSessionVo userDept = SessionUtil.getUserDept(request);
        // 查询被操作者的当前部门
        DeptSessionVo operatedUserDept = deptMapper.selectUserDept(userId);
        boolean isSelfLeader = userDept.getLeader().equals(DeptEnums.leader.SELF_LEADER.level());
        List<DeptTreeResult> deptTreeResults = new ArrayList<>();
        for (Dept dept : depts) {
            DeptTreeResult treeResult = new DeptTreeResult();
            BeanUtils.copyProperties(dept,treeResult);
            if(!isSelfLeader){
                treeResult.setNocheck(!userSubDeptIdList.contains(dept.getId()));
            }else{
                treeResult.setNocheck(true);
            }
            if(operatedUserDept != null && operatedUserDept.getId().equals(dept.getId())){
                treeResult.setChecked(true);
            }
            deptTreeResults.add(treeResult);
        }
        return new PageResponse.Builder().build(deptTreeResults, 0, 0, 0);
    }

    private void findChildren(SelectTreeNodeResult node, List<Dept> depts, List<Integer> operatorSubDeptIds) {
        List<SelectTreeNodeResult> nodes = new ArrayList<>();
            for (Dept dept: depts) {
                if(dept.getPid().equals(node.getId())){
                    SelectTreeNodeResult nodeNew = new SelectTreeNodeResult();
                    nodeNew.setDisabled(!operatorSubDeptIds.contains(dept.getId()));
                    nodeNew.setId(dept.getId());
                    nodeNew.setTitle(dept.getDeptName());
                    nodeNew.setSpread(true);
                    nodes.add(nodeNew);
                    this.findChildren(nodeNew,depts, operatorSubDeptIds);
                }
            }
        node.setChildren(nodes);
    }

    /**
     * 模糊搜素
     * @param text 模糊的字符
     * @param fieldName 模糊字段名
     * @return FuzzyResponse
     */
    @Override
    public FuzzyResponse fuzzySearch(String text, String fieldName) {
        FuzzyVo fuzzyVo = new FuzzyVo();
        fuzzyVo.setText(text);
        fuzzyVo.setFieldName(fieldName);
        fuzzyVo.setTableName(ConvertHumpUtil.humpToLine("Dept"));
        return new FuzzyResponse.Builder().build(commonMapper.fuzzySearch(fuzzyVo));
    }

    /**
     * 保存记录
     * @param query  查询实体
     * @param request httpServletRequest
     * @param response
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse save(BaseQuery query, HttpServletRequest request, HttpServletResponse response) {

        deptMapper.insert(this.assQueryForInsert((DeptQuery)query,request,response));
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * 更新记录
     * @param query
     * @param request
     * @param response
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT ,rollbackFor = Exception.class)
    public CommonResponse update(BaseQuery query, HttpServletRequest request, HttpServletResponse response) {
        DeptQuery deptQuery = ((DeptQuery)query);

        DeptExample example = new DeptExample();
        DeptExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(deptQuery.getId());
        User operator = SessionUtil. getOperator(request, response);
        deptQuery.setGmtModified(new Date());
        deptQuery.setModifier(operator.getUsername());
        deptQuery.setModifierCode(operator.getUserId());
        deptMapper.updateByExampleSelective(deptQuery,example);
        return new CommonResponse.Builder().buildSuccess();
    }

    /**
     * @description 校验参数 saveOrEdit
     * @param query
     * @return void
     * @Author elvin
     * @Date 2019/8/5
     */
    private void validateParamForSaveOrEdit(DeptQuery query) {
        // TODO
    }

    /**
     * 组装新增实体
     * @param query
     * @param request
     * @param response
     * @return
     */
    private Dept assQueryForInsert(DeptQuery query, HttpServletRequest request, HttpServletResponse response) {
        this.validateParamForSaveOrEdit(query);
        query.setStatus(DeptEnums.status.INIT.value());
        query.setGmtCreated(new Date());
        User operator = SessionUtil.getOperator(request, response);
        query.setCreator(operator.getUsername());
        query.setCreatorCode(operator.getUserId());
        query.setGmtModified(new Date());
        query.setModifier(operator.getUsername());
        query.setModifierCode(operator.getUserId());

        return query;
    }

    /**
     * 组装更新状态实体
     * @param id
     * @param status
     * @param request
     * @param response
     * @return
     */
    private Dept assQueryForUpdateStatus(Integer id, Integer status, HttpServletRequest request, HttpServletResponse response) {
        DeptQuery infoQuery = new DeptQuery();
        User operator = SessionUtil. getOperator(request, response);
        infoQuery.setId(id);
        infoQuery.setStatus(status);
        infoQuery.setGmtModified(new Date());
        infoQuery.setModifier(operator.getUsername());
        infoQuery.setModifierCode(operator.getUserId());
        return infoQuery;
    }

    /**
     * 组装分页参数
     * @param query
     * @return
     */
    private DeptExample assExampleForList(DeptQuery query) {
        DeptExample example = new DeptExample();
        DeptExample.Criteria criteria = example.createCriteria();
        if(StringUtil.isNotEmpty(query.getStartTime())){
            criteria.andGmtCreatedGreaterThanOrEqualTo(DateUtil.parse(query.getStartTime()));
        }
        if(StringUtil.isNotEmpty(query.getEndTime())){
            criteria.andGmtCreatedLessThanOrEqualTo(DateUtil.parse(query.getEndTime()));
        }
        if(query.getId() != null){
            criteria.andIdEqualTo(query.getId());
        }
        // TODO
        if(StringUtil.isNotEmpty(query.getStates())){
            List list = new ArrayList();
            for (String state : query.getStates().split(",")) {
                list.add(Integer.parseInt(state));
            }
            criteria.andStatusIn(list);
        }
        criteria.andStatusNotEqualTo(StatusConstants.DELETED);
        example.setOrderByClause("gmt_modified desc");
        return example;
    }
}