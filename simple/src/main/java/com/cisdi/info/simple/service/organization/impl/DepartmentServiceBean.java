package com.cisdi.info.simple.service.organization.impl;

import com.cisdi.info.simple.dao.organization.DepartmentDao;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.organization.Department;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.organization.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

@Service
@Transactional
public class DepartmentServiceBean extends BaseService implements DepartmentService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private DepartmentDao departmentDao;

	public PageResultDTO findDepartments(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<Department> departmentDTOS = this.departmentDao.findDepartments(pageDTO);
		Long totalCount = this.departmentDao.findDepartmentTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(departmentDTOS);

		return pageResultDTO;
	}

	public List<Department> findAllDepartments()
	{
		return this.departmentDao.findAllDepartments();
	}

	public List<Department> findAllDepartmentsWithIdName()
	{
		return this.departmentDao.findAllDepartmentsWithIdName();
	}

	public List<Department> findDepartmentsWithIdNameByName(String departmentName)
	{
		return this.departmentDao.findDepartmentsWithIdNameByName(departmentName);
	}

	public Department findDepartmentsWithIdNameById(Long departmentId)
	{
		return this.departmentDao.findDepartmentsWithIdNameById(departmentId);
	}

	public Department findDepartment(Long departmentId)
	{
		return this.departmentDao.findDepartment(departmentId);
	}

	//所有外键的Name都以加载
	public Department findDepartmentWithForeignName(Long departmentId)
	{
		return this.departmentDao.findDepartmentWithForeignName(departmentId);
	}

	public Department saveDepartment(Department department)
	{
		//TODO:请在此校验参数的合法性
		this.setSavePulicColumns(department);
		Integer rows = this.departmentDao.saveDepartment(department);
		if(rows != 1)
		{
			String error = "新增保存部门出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return department;
	}

	public Department updateDepartment(Department department)
	{
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(department);
		Integer rows = this.departmentDao.updateDepartment(department);
		if(rows != 1)
		{
			String error = "修改保存部门出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}
		return department;
	}

	public void deleteDepartment(Long departmentId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(Department.class, departmentId);
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

		Integer rows = this.departmentDao.deleteDepartment(departmentId);
		if(rows != 1)
		{
			String error = "删除部门出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}
}
