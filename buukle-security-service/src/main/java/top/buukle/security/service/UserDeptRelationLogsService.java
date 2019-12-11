package top.buukle.security .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.security .entity.vo.UserDeptRelationLogsQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description UserDeptRelationLogsService 接口类
*/
public interface UserDeptRelationLogsService extends BaseService{

    CommonResponse saveOrEdit(UserDeptRelationLogsQuery query, HttpServletRequest request, HttpServletResponse response);

}