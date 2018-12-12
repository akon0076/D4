package com.cisdi.info.simple.service.organization.impl;

import com.cisdi.info.simple.dao.organization.OrganizationDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class OrganizationServiceBean extends BaseService implements OrganizationService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private OrganizationDao organizationDao;

	public PageResultDTO findOrganizations(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<Organization> organizationDTOS = this.organizationDao.findOrganizations(pageDTO);
		Long totalCount = this.organizationDao.findOrganizationTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(organizationDTOS);

		return pageResultDTO;
	}

	public List<Organization> findAllOrganizations()
	{
		return this.organizationDao.findAllOrganizations();
	}

	public List<Organization> findAllOrganizationsWithIdName()
	{
		return this.organizationDao.findAllOrganizationsWithIdName();
	}

	public List<Organization> findOrganizationsWithIdNameByName(String organizationName)
	{
		return this.organizationDao.findOrganizationsWithIdNameByName(organizationName);
	}

	public Organization findOrganizationsWithIdNameById(Long organizationId)
	{
		return this.organizationDao.findOrganizationsWithIdNameById(organizationId);
	}

	public Organization findOrganization(Long organizationId)
	{
		return this.organizationDao.findOrganization(organizationId);
	}

	//所有外键的Name都以加载
	public Organization findOrganizationWithForeignName(Long organizationId)
	{
		return this.organizationDao.findOrganizationWithForeignName(organizationId);
	}

	public Organization saveOrganization(Organization organization)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(organization);
		Integer rows = this.organizationDao.saveOrganization(organization);
		if(rows != 1)
		{
			String error = "新增保存单位出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return organization;
	}

	public Organization updateOrganization(Organization organization)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(organization);
		Integer rows = this.organizationDao.updateOrganization(organization);
		if(rows != 1)
		{
			String error = "修改保存单位出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return organization;
	}

	public void deleteOrganization(Long organizationId)
	{
		Integer rows = this.organizationDao.deleteOrganization(organizationId);
		if(rows != 1)
		{
			String error = "删除单位出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
