package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.security .entity.vo.UserDeptRelationQuery;
import top.buukle.security .service.UserDeptRelationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author elvin
* @description  UserDeptRelation controller
*/
@Controller
@RequestMapping("/userDeptRelation")
public class UserDeptRelationController {

    @Autowired
    private UserDeptRelationService userDeptRelationService;

    /**
    * 获取列表
    * @return
    * @throws Exception
    */
    @RequestMapping("/{pageName}")
    public ModelAndView getPage(@PathVariable("pageName") String pageName, UserDeptRelationQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", userDeptRelationService.getPage(query));
        modelAndView.setViewName("userDeptRelation/" + pageName);
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
    public CommonResponse saveOrEdit(UserDeptRelationQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userDeptRelationService.saveOrEdit(query,request,response);
    }
}