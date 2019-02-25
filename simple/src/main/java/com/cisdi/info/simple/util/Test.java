package com.cisdi.info.simple.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
        System.out.println(new Date() + "定时任务执行中！");
        System.out.println(new Date() + "任务结束------------------------------------");
    }
}
