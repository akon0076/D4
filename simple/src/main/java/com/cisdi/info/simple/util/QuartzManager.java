package com.cisdi.info.simple.util;

import com.cisdi.info.simple.dao.task.TaskDao;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;

/**
 * @author gjt
 * @version 1.0
 * @className 使用这个版本代码
 * @date 2019/2/23 20:44
 * @description
 */
@Configuration
public class QuartzManager {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private TaskDao taskDao;

    /**
     * 启动一个任务
     * @param id
     * @throws SchedulerException
     */
    public void start(Integer id) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            startJob(scheduler,cronEntity.get("quarzName").toString(), cronEntity.get("cron").toString(), cronEntity.get("schedulerClass").toString(), Integer.valueOf(cronEntity.get("eid").toString()));
        }
    }

    /**
     * 暂停一个任务
     * @param id 任务id
     * @throws SchedulerException
     */
    public void pauseSchedulerJob(Integer id) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            pauseJob(cronEntity.get("quarzName").toString(), cronEntity.get("quarzGroup").toString(), Integer.valueOf(cronEntity.get("eid").toString()));
        }
    }

    /**
     * 恢复一个定时任务
     * @param id 任务id
     * @throws SchedulerException
     */
    public void resumeSchedulerJob(Integer id) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            resumeJob(cronEntity.get("quarzName").toString(), cronEntity.get("quarzGroup").toString(), Integer.valueOf(cronEntity.get("eid").toString()));
        }
    }

    /**
     * 获取定时任务信息
     * @param id 任务id
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(Integer id) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            return getJobInfo(cronEntity.get("quarzName").toString(), cronEntity.get("quarzGroup").toString());
        }
        return null;
    }

    /**
     * 删除定时任务
     * @param id 任务ID
     * @throws SchedulerException 捕获定时任务有关的异常
     */
    public void deleteJob(Integer id) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            deleteJob(cronEntity.get("quarzName").toString(), cronEntity.get("quarzGroup").toString(), Integer.valueOf(cronEntity.get("eid").toString()));
        }
    }

    /**
     * 修改定时任务
     * @param id 任务ID
     * @param time 任务名称
     */
    public boolean modifyJob(Integer id, String time) throws SchedulerException {
        HashMap cronEntity = this.taskDao.getTaskById(id);
        if(cronEntity!= null){
            return modifyJob(cronEntity.get("quarzName").toString(), cronEntity.get("quarzGroup").toString(), time);
        }
        return false;
    }


    private void startJob(Scheduler scheduler,String name,String cron,String className, Integer id) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        Class<Job> jobClass = null;
        try {
            //实例化具体的Job任务
            jobClass = (Class<Job>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(name, "ddd4").build();
        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(name, "ddd4")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        this.taskDao.updateStatus(id, "已激活");
    }

    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    private String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 修改某个任务的执行时间
     *
     * @param name
     * @param group
     * @param time
     * @return
     * @throws SchedulerException
     */
    private boolean modifyJob(String name, String group, String time) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(time)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    private void pauseJob(String name, String group, Integer id) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
        this.taskDao.updateStatus(id, "已暂停");
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    private void resumeJob(String name, String group, Integer id) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }

        scheduler.resumeJob(jobKey);
        this.taskDao.updateStatus(id, "已激活");
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    private void deleteJob(String name, String group, Integer id) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
        // 当任务被删除的时候，状态变为已经结束
        this.taskDao.finishTheJobWork(id, "已结束", new Date());
    }
}
