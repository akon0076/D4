package com.cisdi.info.simple.dao.task;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Mapper
@Component(value = "taskDao")
public interface TaskDao {

    /**
    * 根据分页参数查询定时任务集合
    *
    * @param pageDTO 分页条件
    */
    public List<Task> findTasks(PageDTO pageDTO);

    /**
    * 查询全部定时任务集合
    *
    */
    public List<Task> findAllTasks();

    /**
    * 查询所有定时任务集合(只提取ID 和 Name)
    *
    */
    public List<Task> findAllTasksWithIdName();

    /**
    * 根据名称查询定时任务集合(只提取ID 和 Name)
    *
    * @param taskName 名称
    */
    public List<Task> findTasksWithIdNameByName(@Param("taskName") String taskName);

    /**
    * 根据ID查询指定的定时任务(只提取ID 和 Name)
    *
    * @param taskId Id
    */
    public Task findTasksWithIdNameById(@Param(" taskId") Long taskId);

    /**
    * 根据分页参数查询定时任务集合的数量
    *
    * @param pageDTO 分页条件
    */
    public Long findTaskTotalCount(PageDTO pageDTO);

    /**
    * 根据ID查询指定的定时任务
    *
    * @param taskId Id
    */
    public Task findTask(@Param("taskId") Long taskId);

    /**
    * 根据ID查询指定的定时任务(包含外键)
    *
    * @param taskId Id
    */
    public Task findTaskWithForeignName(@Param("taskId") Long taskId);

    /**
    * 新增定时任务
    *
    * @param task 实体对象
    */
    public Integer saveTask(Task task);

    /**
    * 更新定时任务
    *
    * @param task 实体对象
    */
    public Integer updateTask(Task task);

    /**
    * 根据ID删除定时任务
    *
    * @param taskId ID
    */
    public Integer deleteTask(@Param("taskId") Long taskId);

    /**
     * 根据EID获取指定数据
     * @param id
     * @return
     */
    HashMap getTaskById(Integer id);

    Integer finishTheJobWork(@Param("id")Integer id , @Param("taskStatus") String taskStatus, @Param("taskCompleteTime")Date taskCompleteTime);

    Integer updateStatus(@Param("id")Integer id ,@Param("taskStatus") String taskStatus);
}
