/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: Logout
 * Author:   elvin
 * Date:     2019/8/15 1:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.plugin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.call.PageResponse;
import top.buukle.security.entity.Role;
import top.buukle.security.plugin.cache.SecuritySessionContext;
import top.buukle.security.plugin.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 〈〉
 * @author elvin
 * @create 2019/8/15
 * @since 1.0.0
 */
@Controller
public class SecurityDefaultController {

    @Autowired
    private Environment env;
    @Autowired
    private SecuritySessionContext sessionContext;

    /**
     * @description 登出
     * @param request
     * @param response
     * @return void
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request , HttpServletResponse response) throws IOException {
        sessionContext.removeFromSessionContext(SessionUtil.getUser(request,response).getUserId());
        SessionUtil.logout(request,response);
        response.sendRedirect(env.getProperty("security.passport.host"));
    }

    /**
     * @description 计数
     * @param
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    @RequestMapping("/getOnlineCount")
    @ResponseBody
    public CommonResponse getOnlineCount(){
        return sessionContext.countSessionContext();
    }

    /**
     * @description 获取当前用户拥有角色的应用
     * @param request
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/4
     */
    @RequestMapping("/getUserApplication")
    @ResponseBody
    public PageResponse getUserApplication(HttpServletRequest request){
        return SessionUtil.getUserApplication(request);
    }

    /**
     * @description 获取当前用户指定应用下的角色
     * @param request
     * @param applicationCode
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @RequestMapping("/getUserRoleIdByAppCode")
    @ResponseBody
    public CommonResponse getUserRoleIdByAppCode(HttpServletRequest request, String applicationCode){
        Role userRoleId = SessionUtil.getUserRoleId(request, applicationCode);
        CommonResponse commonResponse = new CommonResponse.Builder().buildSuccess();
        commonResponse.setBody(userRoleId);
        return commonResponse;
    }

    /**
     * @description 获取当前用户下辖角色列表
     * @param request
     * @param applicationCode
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/12/10
     */
    @RequestMapping("/getUserSubRolesByAppCode")
    @ResponseBody
    public PageResponse getUserSubRolesByAppCode(HttpServletRequest request, String applicationCode){
        return  SessionUtil.getUserSubRolesByAppCode(request, applicationCode);
    }
}