package top.buukle.security .service;

import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.common.mvc.BaseService;
import top.buukle.security .entity.vo.DeptQuery;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description DeptService 接口类
*/
public interface DeptService extends BaseService{

    CommonResponse saveOrEdit(DeptQuery query, HttpServletRequest request, HttpServletResponse response);

    PageResponse getDeptTree(Integer applicationId, HttpServletRequest request, HttpServletResponse response);

    PageResponse getUserDept(String userId, HttpServletRequest request, HttpServletResponse response);
}