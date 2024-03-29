package com.cisdi.info.simple.service.report.impl;

import com.cisdi.info.simple.dao.report.ModelFileDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.report.ModelFile;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.report.ModelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class ModelFileServiceBean extends BaseService implements ModelFileService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private ModelFileDao modelFileDao;

	public PageResultDTO findModelFiles(PageDTO pageDTO)
	{
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<ModelFile> modelFileDTOS = this.modelFileDao.findModelFiles(pageDTO);
		Long totalCount = this.modelFileDao.findModelFileTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(modelFileDTOS);

		return pageResultDTO;
	}

	public List<ModelFile> findAllModelFiles()
	{
		return this.modelFileDao.findAllModelFiles();
	}

	public List<ModelFile> findAllModelFilesWithIdName()
	{
		return this.modelFileDao.findAllModelFilesWithIdName();
	}

	public List<ModelFile> findModelFilesWithIdNameByName(String modelFileName)
	{
		return this.modelFileDao.findModelFilesWithIdNameByName(modelFileName);
	}

	public ModelFile findModelFilesWithIdNameById(Long modelFileId)
	{
		return this.modelFileDao.findModelFilesWithIdNameById(modelFileId);
	}

	public ModelFile findModelFile(Long modelFileId)
	{
		return this.modelFileDao.findModelFile(modelFileId);
	}

	//所有外键的Name都以加载
	public ModelFile findModelFileWithForeignName(Long modelFileId)
	{
		return this.modelFileDao.findModelFileWithForeignName(modelFileId);
	}

	public ModelFile saveModelFile(ModelFile modelFile)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(modelFile);
		Integer rows = this.modelFileDao.saveModelFile(modelFile);
		if(rows != 1)
		{
			String error = "新增保存模板文件管理出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return modelFile;
	}

	public ModelFile updateModelFile(ModelFile modelFile)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(modelFile);
		Integer rows = this.modelFileDao.updateModelFile(modelFile);
		if(rows != 1)
		{
			String error = "修改保存模板文件管理出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return modelFile;
	}

	public void deleteModelFile(Long modelFileId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(ModelFile.class, modelFileId);
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

		Integer rows = this.modelFileDao.deleteModelFile(modelFileId);
		if(rows != 1)
		{
			String error = "删除模板文件管理出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
