package com.cisdi.info.simple.service.systemConfig.impl;

import com.cisdi.info.simple.dao.systemConfig.SystemConfigDao;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.service.attachment.AttachmentService;
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

	@Autowired
	private  AttachmentService attachmentService;

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
		try {
			Integer rows = this.systemConfigDao.saveSystemConfig(systemConfig);
			if(rows != 1)
			{
				String error = "新增保存系统参数出错，数据库应该返回1,但返回了 "+rows;
				throw new DDDException(error);
			}
		}
		catch (Exception e){
			throw new DDDException("系统参数不能有重复的键");
		}


		return systemConfig;
	}

	public SystemConfig updateSystemConfig(SystemConfig systemConfig)
	{
		//TODO:请在此校验参数的合法性
		try {
		this.setUpdatePulicColumns(systemConfig);
		Integer rows = this.systemConfigDao.updateSystemConfig(systemConfig);
		if(rows != 1)
		{
			String error = "修改保存系统参数出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		}
		catch (Exception e){
			throw new DDDException("系统参数不能有重复的键");
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

	/**
	 *
	 * @param key 系统参数的key
	 * @return  系统参数的value,否则返回null
	 */
	@Override
	public String getStringValueByKey(String key) {
		if(key==null||"".equals(key)){
			throw new DDDException("传入的key不能为空");
		}
		String value = this.systemConfigDao.getStringValueByKey(key);
		return value;
	}

	/**
	 *
	 * @param key 系统参数的key
	 * @return 系统参数的配置文件信息,否则返回null
	 */
	@Override
	public  List<Attachment> getFilePathByKey(String key) {
		Long eid = this.systemConfigDao.getFilePathByKey(key);
		if (eid == null)
			return null;
        List<Attachment>  attachments=   this.attachmentService.findAllUploadedFilesByIdAndName(eid+"","simple_system_config");
		return attachments;
	}
}
