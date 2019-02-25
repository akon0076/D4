package com.cisdi.info.simple.service.task;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.task.Task;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.task.TaskConfig;

import java.util.HashMap;
import java.util.List;

public interface TaskService {
    /**
     * 根据分页参数查询定时任务集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTasks(PageDTO pageDTO);

    /**
     * 查询全部定时任务集合
     *
     */
    public List<Task> findAllTasks();

    /**
     * 根据名称查询定时任务集合(只提取ID 和 Name)
     *
     * @param taskName 名称
     */
    public List<Task> findTasksWithIdNameByName(String taskName);

    /**
     * 查询所有定时任务集合(只提取ID 和 Name)
     *
     */
    public List<Task> findAllTasksWithIdName();

    /**
     * 根据ID查询指定的定时任务(只提取ID 和 Name)
     *
     * @param taskId Id
     */
    public Task findTasksWithIdNameById(Long taskId);

    /**
     * 根据ID查询指定的定时任务
     *
     * @param taskId Id
     */
    public Task findTask(Long taskId);

    /**
     * 根据ID查询指定的定时任务(包含外键)
     *
     * @param taskId Id
     */
    public Task findTaskWithForeignName(Long taskId);

    /**
     * 新增定时任务
     *
     * @param task 实体对象
     */
    public Task saveTask(Task task);

    /**
     * 更新定时任务
     *
     * @param task 实体对象
     */
    public Task updateTask(Task task);

    /**
     * 根据ID删除定时任务
     *
     * @param taskId ID
     */
    public void deleteTask(Long taskId);

    /**
     * 获取任务配置
     * @return
     */
    List<TaskConfig> getTaskConfigList();
}
