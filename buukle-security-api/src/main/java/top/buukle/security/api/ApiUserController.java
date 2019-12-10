/**
 * Copyright (C), 2015-2019  http://www.jd.com
 * FileName: ApiUserController
 * Author:   zhanglei1102
 * Date:     2019/12/10 10:07
 * Description: 用户api
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.buukle.common.call.CommonResponse;
import top.buukle.security.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 〈用户api〉
 * @author zhanglei1102
 * @create 2019/12/10
 * @since 1.0.0
 */
@Controller
@RequestMapping("/api/user/")
public class ApiUserController {


    @Autowired
    private ApiUserService apiUserService;

    /**
     * @description 内部登陆
     * @param user
     * @param request
     * @param response
     * @return top.buukle.common.call.CommonResponse
     * @Author elvin
     * @Date 2019/8/3
     */
    @RequestMapping("/innerLogin")
    @ResponseBody
    public CommonResponse innerLogin(User user , HttpServletRequest request, HttpServletResponse response) {
        return apiUserService.innerLogin(user,request,response);
    }

    /**
     * @description 失效指定user session
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    @RequestMapping("/deleteSession")
    @ResponseBody
    public void deleteSession(String userId) {
        apiUserService.deleteSession(userId);
    }
    /**
     * @description 踢掉指定user
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    @RequestMapping("/kickOutUser")
    @ResponseBody
    public void kickOutUser(String userId, User user, int expire ) {
        apiUserService.kickOutUser(userId,user,expire);
    }

    /**
     * @description 刷新指定user session信息
     * @param userId
     * @param k
     * @param v
     * @param expire
     * @return void
     * @Author elvin
     * @Date 2019/8/23
     */
    @RequestMapping("/refreshSession")
    @ResponseBody
    public void refreshSession(String userId, String k, Object v, int expire) {
        apiUserService.refreshSession(userId,k,v,expire);
    }

    /**
     * @description 刷新指定user session 过期时间
     *@param userId
     * @param expire   @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    @RequestMapping("/refreshDDL")
    @ResponseBody
    public void refreshDDL( String userId, int expire) {
        apiUserService.refreshDDL(userId,expire);
    }

    /**
     * @description 将 指定user userId 和 sessionId 绑定
     * @param request
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    @RequestMapping("/registerInSessionContext")
    @ResponseBody
    public void registerInSessionContext(HttpServletRequest request, String userId, Integer expire) {
        apiUserService.registerInSessionContext(request,userId,expire);
    }

    /**
     * @description 将 指定user userId 和 sessionId 解绑
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    @RequestMapping("/removeFromSessionContext")
    @ResponseBody
    public void removeFromSessionContext(String userId) {
        apiUserService.removeFromSessionContext(userId);
    }

    /**
     * @description 统计实时在线人数
     * @return int
     * @Author zhanglei1102
     * @Date 2019/8/22
     */
    @RequestMapping("/countSessionContext")
    @ResponseBody
    public CommonResponse countSessionContext() {
        return apiUserService.countSessionContext();
    }

}