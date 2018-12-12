package com.cisdi.info.simple.service.regist.impl;

import com.cisdi.info.simple.dao.regist.MemberRegistDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.util.D4Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.regist.MemberRegist;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.regist.MemberRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class MemberRegistServiceBean extends BaseService implements MemberRegistService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private MemberRegistDao memberRegistDao;

	public PageResultDTO findMemberRegists(PageDTO pageDTO)
	{
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<MemberRegist> memberRegistDTOS = this.memberRegistDao.findMemberRegists(pageDTO);
		Long totalCount = this.memberRegistDao.findMemberRegistTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(memberRegistDTOS);

		return pageResultDTO;
	}

	public List<MemberRegist> findAllMemberRegists()
	{
		return this.memberRegistDao.findAllMemberRegists();
	}

	public List<MemberRegist> findAllMemberRegistsWithIdName()
	{
		return this.memberRegistDao.findAllMemberRegistsWithIdName();
	}

	public List<MemberRegist> findMemberRegistsWithIdNameByName(String memberRegistName)
	{
		return this.memberRegistDao.findMemberRegistsWithIdNameByName(memberRegistName);
	}

	public MemberRegist findMemberRegistsWithIdNameById(Long memberRegistId)
	{
		return this.memberRegistDao.findMemberRegistsWithIdNameById(memberRegistId);
	}

	public MemberRegist findMemberRegist(Long memberRegistId)
	{
		return this.memberRegistDao.findMemberRegist(memberRegistId);
	}

	//所有外键的Name都以加载
	public MemberRegist findMemberRegistWithForeignName(Long memberRegistId)
	{
		return this.memberRegistDao.findMemberRegistWithForeignName(memberRegistId);
	}

	public MemberRegist saveMemberRegist(MemberRegist memberRegist)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(memberRegist);
		Integer rows = this.memberRegistDao.saveMemberRegist(memberRegist);
		if(rows != 1)
		{
			String error = "新增保存商家注册审核出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return memberRegist;
	}

	public MemberRegist updateMemberRegist(MemberRegist memberRegist)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(memberRegist);
		Integer rows = this.memberRegistDao.updateMemberRegist(memberRegist);
		if(rows != 1)
		{
			String error = "修改保存商家注册审核出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return memberRegist;
	}

	public void deleteMemberRegist(Long memberRegistId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(MemberRegist.class, memberRegistId);
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

		Integer rows = this.memberRegistDao.deleteMemberRegist(memberRegistId);
		if(rows != 1)
		{
			String error = "删除商家注册审核出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
