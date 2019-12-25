package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security .entity.vo.DeptQuery;
import top.buukle.security .service.DeptService;
import top.buukle.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * @description 二级页面
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/deptHome")
    public ModelAndView deptHome(ModelAndView modelAndView) {
        modelAndView.setViewName("dept/deptHome");
        return modelAndView;
    }
    /**
     * @description 增改页面
     * @param id
     * @param request
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/deptCrudView")
    public ModelAndView deptCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = deptService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("dept/deptCrudView");
        return modelAndView;
    }



    /**
     * 获取列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/deptPage")
    public ModelAndView deptPage( DeptQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", deptService.getPage(query));
        modelAndView.setViewName("dept/deptPage");
        return modelAndView;
    }

    /**
     * @description 删除单条
     * @param id
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/deptCrudJson")
    public void deptCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        deptService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = deptService.delete(id, request, response);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJSONString(delete));
    }
    /**
     * @description 批量删除
     * @param request
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/deptBatchDeleteJson")
    public void deptBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = deptService.deleteBatch(ids, request, response);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJSONString(commonResponse));
    }

    /**
     * @description 模糊搜索
     * @param text
     * @param fieldName
     * @param request
     * @return top.buukle.common.call.FuzzyResponse
     * @Author elvin
     * @Date 2019/8/4
     */
    @RequestMapping("/fuzzy/search")
    @ResponseBody
    public FuzzyResponse fuzzySearch(String text, String fieldName, HttpServletRequest request) {
        return deptService.fuzzySearch(text, fieldName);
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