/**
 * Copyright (C), 2015-2019  http://www.buukle.top
 * FileName: JobExecuteServiceTestImpl
 * Author:   elvin
 * Date:     2019/12/5 23:54
 * Description: 测试任务执行类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package top.buukle.security.service.impl.JobExecuteServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.buukle.common.call.CommonResponse;
import top.buukle.common.log.BaseLogger;
import top.buukle.security.dao.ApplicationMapper;
import top.buukle.security.entity.Application;
import top.buukle.util.JsonUtil;
import top.buukle.wjs.plugin.quartzJob.service.JobExecuteService;

/**
 * @description 〈测试任务执行类〉
 * @author elvin
 * @create 2019/12/5
 * @since 1.0.0
 */
@Service
public class JobExecuteServiceImplTest implements JobExecuteService {

    @Autowired
    private ApplicationMapper applicationMapper;

    private static BaseLogger LOGGER = BaseLogger.getLogger(JobExecuteServiceImplTest.class);

    @Override
    public CommonResponse execute(String params) {
        LOGGER.info("开始执行测试应用打印任务！");
        Application application = applicationMapper.selectByPrimaryKey(Integer.parseInt(params));
        LOGGER.info(JsonUtil.toJSONString(application));
        LOGGER.info("执行测试应用打印任务完毕！");
        return new CommonResponse.Builder().buildSuccess();
    }
}