/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: SecurityInterceptorCache
 * Author:   elvin
 * Date:     2019/8/2 20:37
 * Description: security插件缓存
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.plugin.cache;

import com.google.common.cache.*;
import top.buukle.security.entity.common.AppResourceResponse;
import top.buukle.common.call.CommonRequest;
import top.buukle.security.plugin.enums.SecurityExceptionEnum;
import top.buukle.security.plugin.exception.SecurityPluginException;
import top.buukle.security.plugin.invoker.SecurityInterceptorInvoker;
import top.buukle.util.JsonUtil;
import top.buukle.common.log.BaseLogger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @description 〈security插件缓存〉
 * @author elvin
 * @create 2019/8/2
 * @since 1.0.0
 */
public class SecurityInterceptorCache {

    private static BaseLogger LOGGER = BaseLogger.getLogger(SecurityInterceptorCache.class);
    private static final String APP_RESOURCE_KEY = "APP_RESOURCE_KEY";

    /** app资源缓存*/
    private static LoadingCache<String,AppResourceResponse> appResourceResponseCache;

    /**
     * @description 加载app资源缓存
     * @param invoker
     * @param applicationName
     * @return void
     * @Author elvin
     * @Date 2019/8/2
     */
    public static void loadAppResourceCache(SecurityInterceptorInvoker invoker, String applicationName) {

        appResourceResponseCache = CacheBuilder.newBuilder().concurrencyLevel(1).expireAfterWrite(10, TimeUnit.MINUTES).initialCapacity(1).maximumSize(1).recordStats().removalListener(notification ->
                LOGGER.info( " 应用资源列表key :{} 已被移除,原因:{} " ,notification.getKey(), notification.getCause())
        ).build(
                new CacheLoader<String, AppResourceResponse>() {
                    @Override
                    public AppResourceResponse load(String s) {

                        long startTime = System.currentTimeMillis();
                        CommonRequest commonRequest = new CommonRequest.Builder().build(applicationName);
                        AppResourceResponse appResource;
                        try{
                            LOGGER.info("应用 : {} 开始加载security资源目录,参数 :{}",applicationName,JsonUtil.toJSONString(commonRequest));
                            appResource = invoker.getAppResource(commonRequest);
                        }catch (Exception e){
                            e.printStackTrace();
                            LOGGER.info("应用 : {} 加载security资源出现异常,原因:{} ",applicationName,e.getMessage());
                            throw new SecurityPluginException(SecurityExceptionEnum.SECURITY_PLUGIN_LOAD_EXCEPTION);
                        }
                        LOGGER.info("应用 : {} 加载security资源目录完成,耗时:{} ms",applicationName,System.currentTimeMillis() - startTime);
                        LOGGER.debug("应用 : {} security资源目录 : {}",applicationName,JsonUtil.toJSONString(appResource));
                        return appResource;
                    }
                }
            );
    }

    /**
     * @description 刷新app资源缓存
     * @return void
     * @Author elvin
     * @Date 2019/8/2
     */
    public static void refresh() {
        LOGGER.info("开始刷新security资源目录...");
        appResourceResponseCache.refresh(APP_RESOURCE_KEY);
    }

    /**
     * @description 获取app资源
     * @return void
     * @Author elvin
     * @Date 2019/8/2
     */
    public static AppResourceResponse get() throws ExecutionException {
        return appResourceResponseCache.get(APP_RESOURCE_KEY);
    }
}