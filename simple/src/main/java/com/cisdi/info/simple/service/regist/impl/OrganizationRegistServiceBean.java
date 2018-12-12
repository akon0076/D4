package com.cisdi.info.simple.service.regist.impl;

import com.cisdi.info.simple.dao.attachment.AttachmentDao;
import com.cisdi.info.simple.dao.organization.EmployeeDao;
import com.cisdi.info.simple.dao.organization.OrganizationDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dao.permission.RoleDao;
import com.cisdi.info.simple.dao.regist.OrganizationRegistDao;

import com.cisdi.info.simple.dto.permission.OperatorAndRoleDto;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.entity.permission.Role;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.organization.OrganizationService;
import com.cisdi.info.simple.service.permission.OperatorAndRoleService;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.permission.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.regist.OrganizationRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cisdi.info.simple.DDDException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class OrganizationRegistServiceBean extends BaseService implements OrganizationRegistService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private OrganizationRegistDao organizationRegistDao;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private OperatorService operatorService;
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
	@Autowired
	private AttachmentDao attachmentDao;
	@Autowired
	private AttachmentService attachmentService;

	public PageResultDTO findOrganizationRegists(PageDTO pageDTO)
	{
		pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
		List<OrganizationRegist> organizationRegistDTOS = this.organizationRegistDao.findOrganizationRegists(pageDTO);
		Long totalCount = this.organizationRegistDao.findOrganizationRegistTotalCount(pageDTO);

		PageResultDTO pageResultDTO = new PageResultDTO();
		pageResultDTO.setTotalCount(totalCount);
		pageResultDTO.setDatas(organizationRegistDTOS);

		return pageResultDTO;
	}

	public List<OrganizationRegist> findAllOrganizationRegists()
	{
		return this.organizationRegistDao.findAllOrganizationRegists();
	}

	public List<OrganizationRegist> findAllOrganizationRegistsWithIdName()
	{
		return this.organizationRegistDao.findAllOrganizationRegistsWithIdName();
	}

	public List<OrganizationRegist> findOrganizationRegistsWithIdNameByName(String organizationRegistName)
	{
		return this.organizationRegistDao.findOrganizationRegistsWithIdNameByName(organizationRegistName);
	}

	public OrganizationRegist findOrganizationRegistsWithIdNameById(Long organizationRegistId)
	{
		return this.organizationRegistDao.findOrganizationRegistsWithIdNameById(organizationRegistId);
	}

	public OrganizationRegist findOrganizationRegist(Long organizationRegistId)
	{
		return this.organizationRegistDao.findOrganizationRegist(organizationRegistId);
	}

	//所有外键的Name都以加载
	public OrganizationRegist findOrganizationRegistWithForeignName(Long organizationRegistId)
	{
		return this.organizationRegistDao.findOrganizationRegistWithForeignName(organizationRegistId);
	}

	public OrganizationRegist saveOrganizationRegist(OrganizationRegist organizationRegist)
	{
		organizationRegist.setCreateDatetime(new Date());
		organizationRegist.setAuditState("待审核");
		Integer rows = this.organizationRegistDao.saveOrganizationRegist(organizationRegist);
		if(rows != 1)
		{
			String error = "新增保存单位注册出错，数据库应该返回1,但返回了 "+rows;
			throw new DDDException(error);
		}
		return organizationRegist;
	}

	public OrganizationRegist updateOrganizationRegist(HttpServletRequest request, OrganizationRegist organizationRegist)
	{
		HttpSession session = request.getSession();
		LoginUser loginUser =(LoginUser)session.getAttribute("loginUser");
		organizationRegist.setAuditEmloyee(loginUser.getLoginEmployee());
		organizationRegist.setAuditDate(new Date());
		//TODO:请在此校验参数的合法性
		this.setUpdatePulicColumns(organizationRegist);
		Integer rows = this.organizationRegistDao.updateOrganizationRegist(organizationRegist);
		if(rows != 1)
		{
			String error = "修改保存单位注册出错，数据库应该返回1,但返回了 "+rows+",数据可能被删除";
			throw new DDDException(error);
		}

		//将审核通过的单位添加到Organization、（单位管理员）Employee、（单位管理员）Operator中
		if(organizationRegist.getAuditState().equals("通过"))
			this.savaOrgOrganization(organizationRegist);
		return organizationRegist;
	}

	public void deleteOrganizationRegist(Long organizationRegistId)
	{
		Map<Class<? extends BaseEntity>,EntityUsage> entityUsageMap = this.checkForeignEntity(OrganizationRegist.class, organizationRegistId);
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

		Integer rows = this.organizationRegistDao.deleteOrganizationRegist(organizationRegistId);
		if(rows != 1)
		{
			String error = "删除单位注册出错，数据库应该返回1,但返回了 "+rows+",数据可能已经被删除";
			throw new DDDException(error);
		}
	}

	public void savaOrgOrganization(OrganizationRegist orgRegist){
		Organization organization = new Organization();
		Long code = System.currentTimeMillis();//获取当前时间戳为code
		organization.setCode(code.toString()+orgRegist.getEId().toString());
		organization.setName(orgRegist.getName());
		organization.setAddress(orgRegist.getAddress());
		organization.setBusinessLicenseCode(orgRegist.getBusinessLicenseCode());
		organization.setAmount(orgRegist.getAmount());
		organization.setLegalPerson(orgRegist.getLegalPerson());
		organization.setLandline(orgRegist.getLandline());
		organization.setIndustryAttributes(orgRegist.getIndustryAttributes());
		organization.setMainBusiness(orgRegist.getMainBusiness());
		organization.setAdminName(orgRegist.getAdminName());
		organization.setAdminEmail(orgRegist.getAdminEmail());
		organization.setAdminLinkTel(orgRegist.getAdminLinkTel());
		Organization org = this.organizationService.saveOrganization(organization);

//		将注册时上传的文件关联的机构id改为正式机构的id
		Attachment att = new Attachment();
		att.setAssociateFormId(org.getEId().toString());
		att.setAssociateFormName("simple_organization");
		List<Attachment> atts = this.attachmentDao.findAllUploadedFilesByIdAndName(att);
		for(int i = 0;i < atts.size();i++){
			Attachment attachment = atts.get(i);
			attachment.setAssociateFormId(org.getEId().toString());
			this.attachmentService.updateAttachment(attachment);
		}

		this.saveOrgEmployee(orgRegist,org.getEId());


	}

	public void saveOrgEmployee(OrganizationRegist orgRegist,Long orgId){
		Employee employee = new Employee();
		Long code = System.currentTimeMillis();//获取当前时间戳为code
		employee.setCode(orgRegist.getAdminName());
		employee.setName(orgRegist.getAdminName());
		employee.setLinkTel(orgRegist.getAdminLinkTel());
		employee.setEmail(orgRegist.getAdminEmail());
		employee.setOrganizationName(orgRegist.getName());
		employee.setOrganizationId(orgId);
		this.setSavePulicColumns(employee);
		Integer rows = this.employeeDao.saveEmployee(employee);
		if (rows != 1) {
			String error = "新增保存职员出错，数据库应该返回1,但返回了 " + rows;
			throw new DDDException(error);
		}

		this.saveOperator(orgRegist,employee.getEId());
	}

	public void saveOperator(OrganizationRegist orgRegist,Long employeeId){
		Operator operator = new Operator();

		operator.setCode(orgRegist.getAdminName());//code登录账号
		operator.setName(orgRegist.getAdminName());
		operator.setPassWord(orgRegist.getAdminPassword());
		operator.setPersonId(employeeId);
		operator.setEmail(orgRegist.getAdminEmail());
		operator.setStatus("在用");
		operator.setType("职员");
		Operator oper = this.operatorService.saveOperator(operator);
		Operator newOperator = this.operatorDao.findOperatorByCode(operator.getCode());

		this.saveOperatorAndRole(newOperator);
	}

	//为团队管理人员分配角色
	public void saveOperatorAndRole(Operator oper){
		Role role = this.roleDao.findRoleByName("机构管理员");
		OperatorAndRole operatorAndRole = new OperatorAndRole();
		operatorAndRole.setOperatorId(oper.getEId());
		operatorAndRole.setRoleId(role.getEId());

		this.operatorAndRoleService.saveOperatorAndRole(operatorAndRole);
	}
}
