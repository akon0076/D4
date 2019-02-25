package com.cisdi.info.simple.service.task.impl;

import com.cisdi.info.simple.dao.task.TaskConfigDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.task.TaskConfig;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.task.TaskConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class TaskConfigServiceBean extends BaseService implements TaskConfigService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private TaskConfigDao taskConfigDao;

	/**
	 * 根据分页参数查询定时任务集合
	 *
	 * @param pageDTO 分页条件
	 */
	public PageResultDTO findTaskConfigs(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<TaskConfig> taskConfigDTOS = this.taskConfigDao.findTaskConfigs(pageDTO);
		Long totalCount = this.taskConfigDao.findTaskConfigTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(taskConfigDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部定时任务集合
	 *
	 */
	public List<TaskConfig> findAllTaskConfigs(){
		return this.taskConfigDao.findAllTaskConfigs();
	}

	/**
	 * 查询所有定时任务集合(只提取ID 和 Name)
	 *
	 */
	public List<TaskConfig> findAllTaskConfigsWithIdName(){
		return this.taskConfigDao.findAllTaskConfigsWithIdName();
	}

	/**
	 * 根据名称查询定时任务集合(只提取ID 和 Name)
	 *
	 * @param taskConfigName 名称
	 */
	public List<TaskConfig> findTaskConfigsWithIdNameByName(String taskConfigName){
		return this.taskConfigDao.findTaskConfigsWithIdNameByName(taskConfigName);
	}

	/**
	 * 根据ID查询指定的定时任务(只提取ID 和 Name)
	 *
	 * @param taskConfigId Id
	 */
	public TaskConfig findTaskConfigsWithIdNameById(Long taskConfigId){
		return this.taskConfigDao.findTaskConfigsWithIdNameById(taskConfigId);
	}

	/**
	 * 根据ID查询指定的定时任务
	 *
	 * @param taskConfigId Id
	 */
	public TaskConfig findTaskConfig(Long taskConfigId){
		return this.taskConfigDao.findTaskConfig(taskConfigId);
	}

	/**
	 * 根据ID查询指定的定时任务(包含外键)
	 *
	 * @param taskConfigId Id
	 */
	public TaskConfig findTaskConfigWithForeignName(Long taskConfigId){
		return this.taskConfigDao.findTaskConfigWithForeignName(taskConfigId);
	}

	/**
	 * 新增定时任务
	 *
	 * @param taskConfig 实体对象
	 */
	public TaskConfig saveTaskConfig(TaskConfig taskConfig){
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(taskConfig);
		Integer rows = this.taskConfigDao.saveTaskConfig(taskConfig);
		if(rows != 1)
		{
			String error = "新增保存定时任务出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return taskConfig;
	}

	/**
	 * 更新定时任务
	 *
	 * @param taskConfig 实体对象
	 */
	public TaskConfig updateTaskConfig(TaskConfig taskConfig){
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(taskConfig);
		Integer rows = this.taskConfigDao.updateTaskConfig(taskConfig);
		if(rows != 1)
		{
			String error = "修改保存定时任务出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return taskConfig;
	}

	/**
	 * 根据ID删除定时任务
	 *
	 * @param taskConfigId ID
	 */
	public void deleteTaskConfig(Long taskConfigId){
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(TaskConfig.class, taskConfigId);
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

		Integer rows = this.taskConfigDao.deleteTaskConfig(taskConfigId);
		if(rows != 1){
			String error = "删除定时任务出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
