package com.cisdi.info.simple.service.regist.impl;

import com.cisdi.info.simple.dao.organization.EmployeeDao;
import com.cisdi.info.simple.dao.organization.OrganizationDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dao.permission.RoleDao;
import com.cisdi.info.simple.dao.regist.EmployeRegistDao;

import com.cisdi.info.simple.dto.permission.OperatorAndRoleDto;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.entity.permission.Role;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.organization.OrganizationService;
import com.cisdi.info.simple.service.permission.OperatorAndRoleService;
import com.cisdi.info.simple.service.permission.OperatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.regist.EmployeRegist;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.regist.EmployeRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.cisdi.info.simple.DDDException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class EmployeRegistServiceBean extends BaseService implements EmployeRegistService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private EmployeRegistDao employeRegistDao;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private OperatorDao operatorDao;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private OperatorAndRoleService operatorAndRoleService;
	@Autowired
	private RoleDao roleDao;

	public PageResultDTO findEmployeRegists(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		LoginUser loginUser = this.getLoginUser();
		pageDTO.setOrganizationName(loginUser.getLoginEmployee().getOrganizationName());
		List<EmployeRegist> employeRegistDTOS = this.employeRegistDao.findEmployeRegists(pageDTO);
		Long totalCount = this.employeRegistDao.findEmployeRegistTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(employeRegistDTOS);

		return pageResultDTO;
	}

	public List<EmployeRegist> findAllEmployeRegists()
	{
		return this.employeRegistDao.findAllEmployeRegists();
	}

	public List<EmployeRegist> findAllEmployeRegistsWithIdName()
	{
		return this.employeRegistDao.findAllEmployeRegistsWithIdName();
	}

	public List<EmployeRegist> findEmployeRegistsWithIdNameByName(String employeRegistName)
	{
		return this.employeRegistDao.findEmployeRegistsWithIdNameByName(employeRegistName);
	}

	public EmployeRegist findEmployeRegistsWithIdNameById(Long employeRegistId)
	{
		return this.employeRegistDao.findEmployeRegistsWithIdNameById(employeRegistId);
	}

	public EmployeRegist findEmployeRegist(Long employeRegistId)
	{
		return this.employeRegistDao.findEmployeRegist(employeRegistId);
	}

	//所有外键的Name都以加载
	public EmployeRegist findEmployeRegistWithForeignName(Long employeRegistId)
	{
		return this.employeRegistDao.findEmployeRegistWithForeignName(employeRegistId);
	}

	public EmployeRegist saveEmployeRegist(EmployeRegist employeRegist)
	{
		//TODO:请在此校验参数的合法性
		employeRegist.setCreateDatetime(new Date());
		employeRegist.setAuditState("待审核");
		Integer rows = this.employeRegistDao.saveEmployeRegist(employeRegist);
		if(rows != 1)
		{
			String error = "新增保存人员注册出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}

		return employeRegist;
	}

	public EmployeRegist updateEmployeRegist(HttpServletRequest request,EmployeRegist employeRegist)
	{
		//TODO:请在此校验参数的合法性
		HttpSession session = request.getSession();
		LoginUser loginUser =(LoginUser)session.getAttribute("loginUser");
		employeRegist.setAuditEmloyee(loginUser.getLoginEmployee());
		employeRegist.setAuditDate(new Date());
		Integer rows = this.employeRegistDao.updateEmployeRegist(employeRegist);
		if(rows != 1)
		{
			String error = "修改保存人员注册出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}

		if(employeRegist.getAuditState().equals("通过"))
			this.saveRegisEmployee(employeRegist);
		return employeRegist;
	}

	public void deleteEmployeRegist(Long employeRegistId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(EmployeRegist.class, employeRegistId);
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

		Integer rows = this.employeRegistDao.deleteEmployeRegist(employeRegistId);
		if(rows != 1)
		{
			String error = "删除人员注册出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}

	public void saveRegisEmployee(EmployeRegist empRegist){
		Organization org = this.organizationDao.findOrganizationByName(empRegist.getOrganizationName());

		Employee employee = new Employee();
		employee.setCode(empRegist.getName());
		employee.setName(empRegist.getName());
		employee.setLinkTel(empRegist.getLinkTel());
		employee.setEmail(empRegist.getEmail());
		employee.setOrganizationName(empRegist.getOrganizationName());
		employee.setOrganizationId(org.getEId());
		employee.setPassWord(empRegist.getPassword());
		this.setSavePulicColumns(employee);
		Integer rows = this.employeeDao.saveEmployee(employee);
		if (rows != 1) {
			String error = "新增保存职员出错，数据库应该返回1,但返回了 " + rows;
			throw new DDDException(error);
		}

		this.saveNewOperator(empRegist,employee.getEId());
	}

	public void saveNewOperator(EmployeRegist empRegist,Long employeeId){
		Operator operator = new Operator();

		operator.setCode(empRegist.getName());//code登录账号
		operator.setName(empRegist.getName());
		operator.setPassWord(empRegist.getPassword());
		operator.setEmail(empRegist.getEmail());
		operator.setPersonId(employeeId);
		operator.setStatus("在用");
		operator.setType("职员");
		Operator oper = this.operatorService.saveOperator(operator);
		Operator newOperator = this.operatorDao.findOperatorByCode(operator.getCode());
		this.saveOperatorAndRole(newOperator);
	}

	//为团队成员分配角色
	public void saveOperatorAndRole(Operator newOperator){
		Role role = this.roleDao.findRoleByName("机构工作人员");
		OperatorAndRole operatorAndRole = new OperatorAndRole();
		operatorAndRole.setOperatorId(newOperator.getEId());
		operatorAndRole.setRoleId(role.getEId());

		this.operatorAndRoleService.saveOperatorAndRole(operatorAndRole);
	}
}
