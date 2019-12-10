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
import org.springframework.web.bind.annotation.RequestBody;
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

    public static final String BUUKLE_APP_RESOURCE_KEY_PREFIX = "buukle:appResource:";

    /**
     * @description 获取app资源目录
     * @param request
     * @return top.buukle.common.call.AppResourceResponse
     * @Author elvin
     * @Date 2019/8/2
     */
    @RequestMapping("/getAppResource")
    @ResponseBody
    public AppResourceResponse getAppResource(@RequestBody CommonRequest request) {
        return appResourceService.getAppResource(request,BUUKLE_APP_RESOURCE_KEY_PREFIX);
    }
}