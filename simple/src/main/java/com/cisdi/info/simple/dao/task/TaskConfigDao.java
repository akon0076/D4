package com.cisdi.info.simple.dao.task;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.task.TaskConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "taskConfigDao")
public interface TaskConfigDao {

    /**
    * 根据分页参数查询定时任务集合
    *
    * @param pageDTO 分页条件
    */
    public List<TaskConfig> findTaskConfigs(PageDTO pageDTO);

    /**
    * 查询全部定时任务集合
    *
    */
    public List<TaskConfig> findAllTaskConfigs();

    /**
    * 查询所有定时任务集合(只提取ID 和 Name)
    *
    */
    public List<TaskConfig> findAllTaskConfigsWithIdName();

    /**
    * 根据名称查询定时任务集合(只提取ID 和 Name)
    *
    * @param taskConfigName 名称
    */
    public List<TaskConfig> findTaskConfigsWithIdNameByName(@Param("taskConfigName") String taskConfigName);

    /**
    * 根据ID查询指定的定时任务(只提取ID 和 Name)
    *
    * @param taskConfigId Id
    */
    public TaskConfig findTaskConfigsWithIdNameById(@Param(" taskConfigId") Long taskConfigId);

    /**
    * 根据分页参数查询定时任务集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTaskConfigTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的定时任务
    *
    * @param taskConfigId Id
    */
    public TaskConfig findTaskConfig(@Param("taskConfigId") Long taskConfigId);

    /**
    * 根据ID查询指定的定时任务(包含外键)
    *
    * @param taskConfigId Id
    */
    public TaskConfig findTaskConfigWithForeignName(@Param("taskConfigId") Long taskConfigId);

    /**
    * 新增定时任务
    *
    * @param taskConfig 实体对象
    */
    public Integer saveTaskConfig(TaskConfig taskConfig);

    /**
    * 更新定时任务
    *
    * @param taskConfig 实体对象
    */
    public Integer updateTaskConfig(TaskConfig taskConfig);

    /**
    * 根据ID删除定时任务
    *
    * @param taskConfigId ID
    */
    public Integer deleteTaskConfig(@Param("taskConfigId") Long taskConfigId);
}
