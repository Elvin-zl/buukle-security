package top.buukle.security .controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.FuzzyResponse;
import top.buukle.security .entity.vo.UserQuery;
import top.buukle.security .service.UserService;
import top.buukle.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author elvin
* @description User controller
*/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @description 二级页面
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/userHome")
    public ModelAndView userHome(ModelAndView modelAndView) {
        modelAndView.setViewName("user/userHome");
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
    @RequestMapping("/userCrudView")
    public ModelAndView userCrudView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = userService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("user/userCrudView");
        return modelAndView;
    }

    /**
     * @description 设置角色页面
     * @param id
     * @param request
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/userRoleSetView")
    public ModelAndView userRoleSetView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = userService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("user/userRoleSetView");
        return modelAndView;
    }
    /**
     * @description 分配部门页面
     * @param id
     * @param request
     * @param modelAndView
     * @return org.springframework.web.servlet.ModelAndView
     * @Author elvin
     * @Date 2019/12/25
     */
    @RequestMapping("/userDeptSetView")
    public ModelAndView userSetView( Integer id, HttpServletRequest request, ModelAndView modelAndView) {
        Object o = userService.selectByPrimaryKeyForCrud(request, id);
        modelAndView.addObject("record",o);
        modelAndView.setViewName("user/userDeptSetView");
        return modelAndView;
    }

    /**
     * 获取列表
     * @return
     * @throws Exception
     */
    @RequestMapping("/userPage")
    public ModelAndView userPage( UserQuery query, ModelAndView modelAndView) throws Exception {
        modelAndView.addObject("response", userService.getPage(query));
        modelAndView.setViewName("user/userPage");
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
    @RequestMapping("/userCrudJson")
    public void userCrudJson( Integer id, HttpServletRequest request,HttpServletResponse response) throws IOException {
        userService.selectByPrimaryKeyForCrud(request,id);
        CommonResponse delete = userService.delete(id, request, response);
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
    @RequestMapping("/userBatchDeleteJson")
    public void userBatchDeleteJson( String ids , HttpServletRequest request,HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = userService.deleteBatch(ids, request, response);
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
        return userService.fuzzySearch(text, fieldName);
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
    public CommonResponse saveOrEdit(UserQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.saveOrEdit(query,request,response);
    }

    /**
     * @description 设置用户角色
     * @param ids
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/8/13
     */
    @RequestMapping("/userRoleSet")
    @ResponseBody
    public CommonResponse userRoleSet(Integer applicationId,String ids, UserQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.userRoleSet(applicationId,ids,query,request,response);
    }

    /**
     * @description
     * @param query
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/11
     */
    @RequestMapping("/userDeptSet")
    @ResponseBody
    public CommonResponse userDeptSet(Integer deptId,Integer leader, UserQuery query, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return userService.userDeptSet(deptId,leader,query,request,response);
    }
}