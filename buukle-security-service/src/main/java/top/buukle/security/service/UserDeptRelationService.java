package top.buukle.security .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.security .entity.vo.UserDeptRelationQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description UserDeptRelationService 接口类
*/
public interface UserDeptRelationService extends BaseService{

    CommonResponse saveOrEdit(UserDeptRelationQuery query, HttpServletRequest request, HttpServletResponse response);

}