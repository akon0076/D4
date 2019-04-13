package com.cisdi.info.simple.util;

import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author gjt
 * @version 1.0
 * @className
 * @date 2019/2/25 12:11
 * @description
 */
@Component
@DisallowConcurrentExecution
public class Test implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date() + "任务开始------------------------------------");
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        // 提取love字段
        String strData = dataMap.getString("love");
        System.out.println(strData);
        System.out.println(new Date() + "定时任务执行中！");
        System.out.println(new Date() + "任务结束------------------------------------");
    }
}
