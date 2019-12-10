package top.buukle.security.plugin.invoker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import top.buukle.security.entity.common.AppResourceResponse;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.CommonResponse;
import top.buukle.security.entity.User;

/**
 * @Author elvin
 * @Date Created by elvin on 2018/10/1.
 * @Description : security FeignClient代理执行层对象
 */
@FeignClient(name = "${security.server.name}")
public interface SecurityInterceptorInvoker {

    /**
     * @description 获取应用资源列表
     * @param request
     * @return top.buukle.common.call.PageResponse
     * @Author elvin
     * @Date 2019/8/2
     */
    @PostMapping(value = "/api/app/getAppResource")
    AppResourceResponse getAppResource(@RequestBody CommonRequest request);

    /**
     * @description 删除session
     * @param userId
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/deleteSession")
    void deleteSession(@RequestParam("userId") String userId);

    /**
     * @description 强制下线用户
     * @param userId
     * @param user
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/kickOutUser")
    void kickOutUser(@RequestParam("userId") String userId, @RequestBody User user, @RequestParam("expire") int expire);

    /**
     * @description 刷新session
     * @param userId
     * @param k
     * @param v
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/refreshSession")
    void refreshSession(@RequestParam("userId") String userId,@RequestParam("k") String k, @RequestBody Object v, @RequestParam("expire") int expire);

    /**
     * @description 刷新过期时间
     * @param userId
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/refreshDDL")
    void refreshDDL(@RequestParam("userId") String userId,@RequestParam("expire")  int expire);

    /**
     * @description 注册session
     * @param sessionId
     * @param userId
     * @param expire
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/registerInSessionContext")
    void registerInSessionContext(@RequestParam("sessionId") String sessionId, @RequestParam("userId") String userId, @RequestParam("expire") Integer expire);

    /**
     * @description 移除session
     * @param userId
     * @return void
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/removeFromSessionContext")
    void removeFromSessionContext(@RequestParam("userId") String userId);

    /**
     * @description 计数
     * @param
     * @return top.buukle.common.call.CommonResponse
     * @Author zhanglei1102
     * @Date 2019/11/28
     */
    @PostMapping(value = "/api/user/countSessionContext")
    CommonResponse countSessionContext();
}