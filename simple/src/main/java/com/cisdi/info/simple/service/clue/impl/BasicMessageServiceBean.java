package com.cisdi.info.simple.service.clue.impl;

import com.cisdi.info.simple.dao.clue.BasicMessageDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.clue.BasicMessage;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.clue.BasicMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class BasicMessageServiceBean extends BaseService implements BasicMessageService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private BasicMessageDao basicMessageDao;

	/**
	 * 根据分页参数查询基础信息集合
	 *
	 * @param pageDTO 分页条件
	 */
	public PageResultDTO findBasicMessages(PageDTO pageDTO){
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<BasicMessage> basicMessageDTOS = this.basicMessageDao.findBasicMessages(pageDTO);
		Long totalCount = this.basicMessageDao.findBasicMessageTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(basicMessageDTOS);

		return pageResultDTO;
	}

	/**
	 * 查询全部基础信息集合
	 *
	 */
	public List<BasicMessage> findAllBasicMessages(){
		return this.basicMessageDao.findAllBasicMessages();
	}

	/**
	 * 查询所有基础信息集合(只提取ID 和 Name)
	 *
	 */
	public List<BasicMessage> findAllBasicMessagesWithIdName(){
		return this.basicMessageDao.findAllBasicMessagesWithIdName();
	}

	/**
	 * 根据名称查询基础信息集合(只提取ID 和 Name)
	 *
	 * @param basicMessageName 名称
	 */
	public List<BasicMessage> findBasicMessagesWithIdNameByName(String basicMessageName){
		return this.basicMessageDao.findBasicMessagesWithIdNameByName(basicMessageName);
	}

	/**
	 * 根据ID查询指定的基础信息(只提取ID 和 Name)
	 *
	 * @param basicMessageId Id
	 */
	public BasicMessage findBasicMessagesWithIdNameById(Long basicMessageId){
		return this.basicMessageDao.findBasicMessagesWithIdNameById(basicMessageId);
	}

	/**
	 * 根据ID查询指定的基础信息
	 *
	 * @param basicMessageId Id
	 */
	public BasicMessage findBasicMessage(Long basicMessageId){
		return this.basicMessageDao.findBasicMessage(basicMessageId);
	}

	/**
	 * 根据ID查询指定的基础信息(包含外键)
	 *
	 * @param basicMessageId Id
	 */
	public BasicMessage findBasicMessageWithForeignName(Long basicMessageId){
		return this.basicMessageDao.findBasicMessageWithForeignName(basicMessageId);
	}

	/**
	 * 新增基础信息
	 *
	 * @param basicMessage 实体对象
	 */
	public BasicMessage saveBasicMessage(BasicMessage basicMessage){
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(basicMessage);
		Integer rows = this.basicMessageDao.saveBasicMessage(basicMessage);
		if(rows != 1)
		{
			String error = "新增保存基础信息出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return basicMessage;
	}

	/**
	 * 更新基础信息
	 *
	 * @param basicMessage 实体对象
	 */
	public BasicMessage updateBasicMessage(BasicMessage basicMessage){
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(basicMessage);
		Integer rows = this.basicMessageDao.updateBasicMessage(basicMessage);
		if(rows != 1)
		{
			String error = "修改保存基础信息出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return basicMessage;
	}

	/**
	 * 根据ID删除基础信息
	 *
	 * @param basicMessageId ID
	 */
	public void deleteBasicMessage(Long basicMessageId){
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(BasicMessage.class, basicMessageId);
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

		Integer rows = this.basicMessageDao.deleteBasicMessage(basicMessageId);
		if(rows != 1){
			String error = "删除基础信息出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
