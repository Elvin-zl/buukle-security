package top.buukle.security .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.security.entity.vo.UserTrailQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description UserTrailService 接口类
*/
public interface UserTrailService extends BaseService{

    CommonResponse saveOrEdit(UserTrailQuery query, HttpServletRequest request, HttpServletResponse response);

}