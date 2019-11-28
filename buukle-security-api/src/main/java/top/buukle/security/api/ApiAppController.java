/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: AppRsourceController
 * Author:   elvin
 * Date:     2019/8/2 22:34
 * Description: app资源加载controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.api;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.buukle.common.call.AppResourceResponse;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.call.CommonResponse;
import top.buukle.security.entity.User;
import top.buukle.security.plugin.util.SessionUtil;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @description 〈app资源加载controller〉
 * @author elvin
 * @create 2019/8/2
 * @since 1.0.0
 */
@Controller
@RequestMapping("/api/app/")
public class ApiAppController{

    @Autowired
    private ApiAppService appResourceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String SPRING_SESSION_KEY_PREFIX = "spring:session:sessions:";
    private static final String SPRING_SESSION_KEY_EXPIRE_PREFIX = "spring:session:sessions:expires:";
    public static final String SESSION_ATTR_PREFIX = "sessionAttr:";

    /**
     * @description 获取app资源目录
     * @param request
     * @return top.buukle.common.call.AppResourceResponse
     * @Author elvin
     * @Date 2019/8/2
     */
    @RequestMapping("/getAppResource")
    @ResponseBody
    public AppResourceResponse getAppResource(CommonRequest request) {
        return appResourceService.getAppResource(request);
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
        stringRedisTemplate.delete(SPRING_SESSION_KEY_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId));
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
        String oldSid = stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
        if(!StringUtil.isEmpty(oldSid)){
            stringRedisTemplate.opsForHash().put(SPRING_SESSION_KEY_PREFIX + oldSid, SESSION_ATTR_PREFIX + SessionUtil.USER_SESSION_KEY, JsonUtil.toJSONString(user, SerializerFeature.WriteClassName));
            stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + oldSid,expire, TimeUnit.SECONDS);
        }
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
        String oldSid = stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
        if(!StringUtil.isEmpty(oldSid)){
            stringRedisTemplate.opsForHash().put(SPRING_SESSION_KEY_PREFIX + oldSid, SESSION_ATTR_PREFIX + k, JsonUtil.toJSONString(v, SerializerFeature.WriteClassName));
            stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + oldSid,expire,TimeUnit.SECONDS);
        }
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
        stringRedisTemplate.expire(SPRING_SESSION_KEY_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId),expire,TimeUnit.SECONDS);
        stringRedisTemplate.expire(SPRING_SESSION_KEY_EXPIRE_PREFIX + stringRedisTemplate.opsForValue().get(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX+userId),expire,TimeUnit.SECONDS);
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
        stringRedisTemplate.opsForValue().set(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId, request.getSession().getId());
        stringRedisTemplate.expire(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId, expire, TimeUnit.SECONDS);
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
        stringRedisTemplate.delete(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + userId);
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
        CommonResponse commonResponse = new CommonResponse.Builder().buildSuccess();
        commonResponse.getHead().setMsg(stringRedisTemplate.keys(SessionUtil.SECURITY_SESSION_CONTEXT_KEY_PREFIX + "*").size()+StringUtil.EMPTY);
        return  commonResponse;
    }
}