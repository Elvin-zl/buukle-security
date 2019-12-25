package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.security .entity.vo.ButtonQuery;
import top.buukle.security .service.ButtonService;
import top.buukle.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author elvin
* @description  Button controller
*/
@Controller
@RequestMapping("/button")
public class ButtonController {

    @Autowired
    private ButtonService buttonService;

    /**
     * @description 二级页面
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/buttonHome")
    public ModelAndView buttonHome(ModelAndView modelAndView) {
        modelAndView.setViewName("button/buttonHome");
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
    @RequestMapping("/buttonCrudView")
    public ModelAndView buttonCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = buttonService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("button/buttonCrudView");
        return modelAndView;
    }



    /**
     * 获取列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/buttonPage")
    public ModelAndView buttonPage( ButtonQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", buttonService.getPage(query));
        modelAndView.setViewName("button/buttonPage");
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
    @RequestMapping("/buttonCrudJson")
    public void buttonCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        buttonService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = buttonService.delete(id, request, response);
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
    @RequestMapping("/buttonBatchDeleteJson")
    public void buttonBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = buttonService.deleteBatch(ids, request, response);
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
        return buttonService.fuzzySearch(text, fieldName);
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
    public CommonResponse saveOrEdit(ButtonQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return buttonService.saveOrEdit(query,request,response);
    }
}