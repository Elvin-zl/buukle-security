package top.buukle.security.api;


import top.buukle.security.entity.common.AppResourceResponse;
import top.buukle.common.call.CommonRequest;

/**
* @author elvin
* @description ApiAppService 接口类
*/
public interface ApiAppService {



    /**
     * @description 查询app资源目录
     * @param request
     * @param buukleAppResourceKeyPrefix
     * @return top.buukle.security.entity.common.AppResourceResponse
     * @Author elvin
     * @Date 2019/8/2
     */
    AppResourceResponse getAppResource(CommonRequest request, String buukleAppResourceKeyPrefix);

}