package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security .entity.vo.AreaQuery;
import top.buukle.security .service.AreaService;

import top.buukle.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author elvin
* @description  Area controller
*/
@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
    * @description 二级页面
    * @param modelAndView
    * @return org.springframework.web.servlet.ModelAndView
    * @Author elvin
    * @Date 2019/12/25
    */
    @RequestMapping("/areaHome")
    public ModelAndView areaHome(ModelAndView modelAndView) {
        modelAndView.setViewName("area/areaHome");
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
    @RequestMapping("/areaCrudView")
    public ModelAndView areaCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = areaService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("area/areaCrudView");
        return modelAndView;
    }



    /**
    * 获取列表
    * @return
    * @throws Exception
    */
    @RequestMapping("/areaPage")
    public ModelAndView areaPage( AreaQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", areaService.getPage(query));
        modelAndView.setViewName("area/areaPage");
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
    @RequestMapping("/areaCrudJson")
    public void areaCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        areaService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = areaService.delete(id, request, response);
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
    @RequestMapping("/areaBatchDeleteJson")
    public void areaBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = areaService.deleteBatch(ids, request, response);
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
        return areaService.fuzzySearch(text, fieldName);
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
    public CommonResponse saveOrEdit(AreaQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return areaService.saveOrEdit(query,request,response);
    }
}