package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security .entity.vo.DeptQuery;
import top.buukle.security .service.DeptService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description  Dept controller
*/
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
    * 获取列表
    * @return
    * @throws Exception
    */
    @RequestMapping("/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName, DeptQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", deptService.getPage(query));
        modelAndView.setViewName("dept/" + pageName);
        return modelAndView;
    }

    /**
    * @description 新增或者修改
    * @param query
    * @param request
    * @return top.buukle.common.call.CommonResponse
    * @Author elvin
    * @Date 2019/8/5
    */
    @RequestMapping("/saveOrEdit")
    @ResponseBody
    public CommonResponse saveOrEdit(DeptQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return deptService.saveOrEdit(query,request,response);
    }

    /**
     * @description 获取部门完整树
     * @param applicationId
     * @param request
     * @param response
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/12/11
     */
    @RequestMapping("/getDeptTree")
    @ResponseBody
    public PageResponse getDeptTree(Integer applicationId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return deptService.getDeptTree(applicationId,request,response);
    }

    /**
     * @description 获取用户部门树
     * @param request
     * @param response
     * @return top.buukle.common.call.PageResponse
     * @Author zhanglei1102
     * @Date 2019/12/11
     */
    @RequestMapping("/getUserDept")
    @ResponseBody
    public PageResponse getUserDept(String userId,HttpServletRequest request, HttpServletResponse response) throws Exception {
        return deptService.getUserDept(userId,request,response);
    }
}