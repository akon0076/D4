package com.cisdi.info.simple.service.task;

import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.dto.base.PageDTO;

import java.util.List;

public interface TaskConfigService {
    /**
     * 根据分页参数查询定时任务集合
     *
     * @param pageDTO 分页条件
     */
    public PageResultDTO findTaskConfigs(PageDTO pageDTO);

    /**
     * 查询全部定时任务集合
     *
     */
    public List<TaskConfig> findAllTaskConfigs();

    /**
     * 根据名称查询定时任务集合(只提取ID 和 Name)
     *
     * @param taskConfigName 名称
     */
    public List<TaskConfig> findTaskConfigsWithIdNameByName(String taskConfigName);

    /**
     * 查询所有定时任务集合(只提取ID 和 Name)
     *
     */
    public List<TaskConfig> findAllTaskConfigsWithIdName();

    /**
     * 根据ID查询指定的定时任务(只提取ID 和 Name)
     *
     * @param taskConfigId Id
     */
    public TaskConfig findTaskConfigsWithIdNameById(Long taskConfigId);

    /**
     * 根据ID查询指定的定时任务
     *
     * @param taskConfigId Id
     */
    public TaskConfig findTaskConfig(Long taskConfigId);

    /**
     * 根据ID查询指定的定时任务(包含外键)
     *
     * @param taskConfigId Id
     */
    public TaskConfig findTaskConfigWithForeignName(Long taskConfigId);

    /**
     * 新增定时任务
     *
     * @param taskConfig 实体对象
     */
    public TaskConfig saveTaskConfig(TaskConfig taskConfig);

    /**
     * 更新定时任务
     *
     * @param taskConfig 实体对象
     */
    public TaskConfig updateTaskConfig(TaskConfig taskConfig);

    /**
     * 根据ID删除定时任务
     *
     * @param taskConfigId ID
     */
    public void deleteTaskConfig(Long taskConfigId);
}
