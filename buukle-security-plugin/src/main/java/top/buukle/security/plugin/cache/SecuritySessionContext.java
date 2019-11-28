package top.buukle.security.plugin.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.buukle.common.call.CommonResponse;
import top.buukle.security.entity.User;
import top.buukle.security.plugin.invoker.SecurityInterceptorInvoker;
import javax.servlet.http.HttpServletRequest;

/**
 * @description session 实时在线session操作
 * @Author zhanglei1102
 * @Date 2019/8/19
 */
@Component
public class SecuritySessionContext {

    @Autowired
    private SecurityInterceptorInvoker invoker;

    /**
     * @description 失效指定user session
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    public void deleteSession(String userId) {
        invoker.deleteSession(userId);
    }
    /**
     * @description 踢掉指定user 
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    public void kickOutUser(String userId, User user, int expire ) {
        invoker.kickOutUser(userId,user,expire);
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
    public void refreshSession(String userId, String k, Object v, int expire) {
        invoker.refreshSession(userId,k,v,expire);
    }
    /**
     * @description 刷新指定user session 过期时间
     *@param userId
     * @param expire   @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    public void refreshDDL( String userId, int expire) {
        invoker.refreshDDL(userId,expire);
    }
    /**
     * @description 将 指定user userId 和 sessionId 绑定
     * @param request
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    public void registerInSessionContext(HttpServletRequest request, String userId,Integer expire) {
        invoker.registerInSessionContext(request.getSession().getId(),userId,expire);

    }
    /**
     * @description 将 指定user userId 和 sessionId 解绑
     * @param userId
     * @return void
     * @Author elvin
     * @Date 2019/8/21
     */
    public void removeFromSessionContext(String userId) {
        invoker.removeFromSessionContext(userId);
    }
    /**
     * @description 统计实时在线人数
     * @return int
     * @Author zhanglei1102
     * @Date 2019/8/22
     */
    public CommonResponse countSessionContext() {
        return  invoker.countSessionContext();
    }

}