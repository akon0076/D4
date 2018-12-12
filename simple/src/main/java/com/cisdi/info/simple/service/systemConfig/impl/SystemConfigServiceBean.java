package com.cisdi.info.simple.service.systemConfig.impl;

import com.cisdi.info.simple.dao.systemConfig.SystemConfigDao;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.base.Condition;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.systemConfig.SystemConfig;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.systemConfig.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class SystemConfigServiceBean extends BaseService implements SystemConfigService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private SystemConfigDao systemConfigDao;

	public PageResultDTO findSystemConfigs(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<SystemConfig> systemConfigDTOS = this.systemConfigDao.findSystemConfigs(pageDTO);
		Long totalCount = this.systemConfigDao.findSystemConfigTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(systemConfigDTOS);

		return pageResultDTO;
	}

	public List<SystemConfig> findAllSystemConfigs()
	{
		return this.systemConfigDao.findAllSystemConfigs();
	}

	public List<SystemConfig> findAllSystemConfigsWithIdName()
	{
		return this.systemConfigDao.findAllSystemConfigsWithIdName();
	}

	public List<SystemConfig> findSystemConfigsWithIdNameByName(String systemConfigName)
	{
		return this.systemConfigDao.findSystemConfigsWithIdNameByName(systemConfigName);
	}

	public SystemConfig findSystemConfigsWithIdNameById(Long systemConfigId)
	{
		return this.systemConfigDao.findSystemConfigsWithIdNameById(systemConfigId);
	}

	public SystemConfig findSystemConfig(Long systemConfigId)
	{
		return this.systemConfigDao.findSystemConfig(systemConfigId);
	}

	//所有外键的Name都以加载
	public SystemConfig findSystemConfigWithForeignName(Long systemConfigId)
	{
		return this.systemConfigDao.findSystemConfigWithForeignName(systemConfigId);
	}

	public SystemConfig saveSystemConfig(SystemConfig systemConfig)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(systemConfig);
		Integer rows = this.systemConfigDao.saveSystemConfig(systemConfig);
		if(rows != 1)
		{
			String error = "新增保存系统参数出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return systemConfig;
	}

	public SystemConfig updateSystemConfig(SystemConfig systemConfig)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(systemConfig);
		Integer rows = this.systemConfigDao.updateSystemConfig(systemConfig);
		if(rows != 1)
		{
			String error = "修改保存系统参数出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return systemConfig;
	}

	public void deleteSystemConfig(Long systemConfigId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(SystemConfig.class, systemConfigId);
		if(entityUsageMap != null && entityUsageMap.size() >0)
		{
			StringBuilder errors = new StringBuilder();
			errors.append("计划删除的数据正在被以下数引用\n");
			for(EntityUsage entityUsage : entityUsageMap.values())
			{
				errors.append("\t").append(entityUsage.getEntityLabel()).append("\n");
				for(Map.Entry<Long,String> entry : entityUsage.getUsageIdNames().entrySet() )
				{
errors.append("\t\t").append(entry.getKey()).append("\t").append(entry.getValue()).append("\n");
}
			}
			errors.append("，不能删除，请检查处理后再删除");
			throw  new DDDException(errors.toString());
		}

		Integer rows = this.systemConfigDao.deleteSystemConfig(systemConfigId);
		if(rows != 1)
		{
			String error = "删除系统参数出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
