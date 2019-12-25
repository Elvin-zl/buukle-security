package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security .entity.vo.ApplicationQuery;
import top.buukle.security .service.ApplicationService;
import top.buukle.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author elvin
* @description  Application controller
*/
@Controller
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * @description 二级页面
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/applicationHome")
    public ModelAndView applicationHome(ModelAndView modelAndView) {
        modelAndView.setViewName("application/applicationHome");
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
    @RequestMapping("/applicationCrudView")
    public ModelAndView applicationCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = applicationService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("application/applicationCrudView");
        return modelAndView;
    }



    /**
     * 获取列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/applicationPage")
    public ModelAndView applicationPage( ApplicationQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", applicationService.getPage(query));
        modelAndView.setViewName("application/applicationPage");
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
    @RequestMapping("/applicationCrudJson")
    public void applicationCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        applicationService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = applicationService.delete(id, request, response);
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
    @RequestMapping("/applicationBatchDeleteJson")
    public void applicationBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = applicationService.deleteBatch(ids, request, response);
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
        return applicationService.fuzzySearch(text, fieldName);
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
    public CommonResponse saveOrEdit(ApplicationQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return applicationService.saveOrEdit(query,request,response);
    }

    /**
     * @description 获取应用树
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/8/9
     */
    @RequestMapping("/getApplicationTree")
    @ResponseBody
    public PageResponse getApplicationTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return applicationService.getApplicationTree(request, response);
    }
}