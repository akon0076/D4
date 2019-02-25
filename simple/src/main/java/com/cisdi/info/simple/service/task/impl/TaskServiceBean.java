package com.cisdi.info.simple.service.task.impl;

import com.cisdi.info.simple.dao.task.TaskConfigDao;
import com.cisdi.info.simple.dao.task.TaskDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.util.D4Util;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.task.Task;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class TaskServiceBean extends BaseService implements TaskService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private TaskConfigDao taskConfigDao;

	/**
	 * 根据分页参数查询定时任务集合
	 *
	 * @param pageDTO 分页条件
	 */
	public PageResultDTO findTasks(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<Task> taskDTOS = this.taskDao.findTasks(pageDTO);
		Long totalCount = this.taskDao.findTaskTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(taskDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部定时任务集合
	 *
	 */
	public List<Task> findAllTasks(){
		return this.taskDao.findAllTasks();
	}

	/**
	 * 查询所有定时任务集合(只提取ID 和 Name)
	 *
	 */
	public List<Task> findAllTasksWithIdName(){
		return this.taskDao.findAllTasksWithIdName();
	}

	/**
	 * 根据名称查询定时任务集合(只提取ID 和 Name)
	 *
	 * @param taskName 名称
	 */
	public List<Task> findTasksWithIdNameByName(String taskName){
		return this.taskDao.findTasksWithIdNameByName(taskName);
	}

	/**
	 * 根据ID查询指定的定时任务(只提取ID 和 Name)
	 *
	 * @param taskId Id
	 */
	public Task findTasksWithIdNameById(Long taskId){
		return this.taskDao.findTasksWithIdNameById(taskId);
	}

	/**
	 * 根据ID查询指定的定时任务
	 *
	 * @param taskId Id
	 */
	public Task findTask(Long taskId){
		return this.taskDao.findTask(taskId);
	}

	/**
	 * 根据ID查询指定的定时任务(包含外键)
	 *
	 * @param taskId Id
	 */
	public Task findTaskWithForeignName(Long taskId){
		return this.taskDao.findTaskWithForeignName(taskId);
	}

	/**
	 * 新增定时任务
	 *
	 * @param task 实体对象
	 */
	public Task saveTask(Task task){
		// 插入任务ID
		task.setTaskId(generateSecretToken());
		// 新增任务默认为未激活状态
		task.setTaskStatus("未激活");
		// 所有任务分组均为ddd4（后期可以自行增加）
		task.setTaskGroup("ddd4");
		this.setSavePulicColumns(task);
		Integer rows = this.taskDao.saveTask(task);
		if(rows != 1)
		{
			String error = "新增保存定时任务出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return task;
	}

	/**
	 * 更新定时任务
	 *
	 * @param task 实体对象
	 */
	public Task updateTask(Task task){
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(task);
		Integer rows = this.taskDao.updateTask(task);
		if(rows != 1)
		{
			String error = "修改保存定时任务出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return task;
	}

	/**
	 * 根据ID删除定时任务
	 *
	 * @param taskId ID
	 */
	public void deleteTask(Long taskId){
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Task.class, taskId);
		if(entityUsageMap != null && entityUsageMap.size() >0){
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values()){
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() ){
					errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
				}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new DDDException(errors.toString());
		}

		Integer rows = this.taskDao.deleteTask(taskId);
		if(rows != 1){
			String error = "删除定时任务出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}

	@Override
	public  List<TaskConfig> getTaskConfigList() {
		return this.taskConfigDao.findAllTaskConfigs();
	}

	/**
	 * 生成安全的taskId
	 * @return 生成结果
	 */
	private String generateSecretToken() {
		SecureRandom secRandom = new SecureRandom();
		byte[] result = new byte[32];
		secRandom.nextBytes(result);
		return Hex.encodeHexString(result);
	}
}
