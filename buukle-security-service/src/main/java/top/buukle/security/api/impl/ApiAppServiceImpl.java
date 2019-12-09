package top.buukle.security.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.buukle.common.call.AppResourceResponse;
import top.buukle.common.call.CommonRequest;
import top.buukle.common.exception.CommonException;
import top.buukle.security.api.ApiAppService;
import top.buukle.security.dao.*;
import top.buukle.security.entity.*;
import top.buukle.security.service.constants.ApplicationEnums;
import top.buukle.security.service.constants.SystemReturnEnum;
import top.buukle.security.service.exception.SystemException;
import top.buukle.util.JsonUtil;
import top.buukle.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
* @author elvin
* @description UserService实现类
*/
@Service
public class ApiAppServiceImpl implements ApiAppService {


    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ButtonMapper buttonMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    /**
     * @description 查询app资源目录
     * @param request
     * @param buukleAppResourceKeyPrefix
     * @return top.buukle.common.call.AppResourceResponse
     * @Author elvin
     * @Date 2019/8/2
     */
    @Override
    public AppResourceResponse getAppResource(CommonRequest request, String buukleAppResourceKeyPrefix) {

        String appResource = stringRedisTemplate.opsForValue().get(buukleAppResourceKeyPrefix + request.getHead().getApplicationCode());
        if(!StringUtils.isEmpty(appResource)){
            return JsonUtil.parseObject(appResource,AppResourceResponse.class);
        }
        // 查询应用
        ApplicationExample applicationExample = new ApplicationExample();
        ApplicationExample.Criteria appCriteria = applicationExample.createCriteria();
        appCriteria.andCodeEqualTo(request.getHead().getApplicationCode());
        appCriteria.andStatusEqualTo(ApplicationEnums.status.PUBLISED.value());
        List<Application> applications = applicationMapper.selectByExampleWithoutIsolation(applicationExample);
        if(CollectionUtils.isEmpty(applications) || applications.size()!=1){
            throw SystemException.convert(new SystemException(SystemReturnEnum.APP_RESOURCE_EXCEPTION),new CommonException());
        }
        // 查询应用已录入资源
        List<String> appUrlList = new ArrayList<>();
        MenuExample menuExample = new MenuExample();
        MenuExample.Criteria menuExampleCriteria = menuExample.createCriteria();
        menuExampleCriteria.andApplicationIdEqualTo(applications.get(0).getId());
        List<Menu> menus = menuMapper.selectByExample(menuExample);
        for (Menu menu: menus) {
            if(StringUtil.isNotEmpty(menu.getUrl())){
                appUrlList.add(menu.getUrl());
            }
        }

        ButtonExample buttonExample = new ButtonExample();
        ButtonExample.Criteria buttonExampleCriteria = buttonExample.createCriteria();
        buttonExampleCriteria.andApplicationIdEqualTo(applications.get(0).getId());
        List<Button> buttons = buttonMapper.selectByExample(buttonExample);

        for (Button btn: buttons) {
            if(StringUtil.isNotEmpty(btn.getUrl())){
                appUrlList.add(btn.getUrl());
            }
        }
        AppResourceResponse appResourceResponse = new AppResourceResponse();
        appResourceResponse.setPermResourceList(appUrlList);
        stringRedisTemplate.opsForValue().set(buukleAppResourceKeyPrefix + request.getHead().getApplicationCode(),JsonUtil.toJSONString(appResourceResponse));
        return appResourceResponse;
    }
}