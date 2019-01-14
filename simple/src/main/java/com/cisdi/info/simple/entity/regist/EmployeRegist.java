package com.cisdi.info.simple.entity.regist;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;


import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.organization.Employee;

@DEntity(label="人员注册",comment="",moduleLabel="注册管理")
@Entity(name="simple_employe_regist")
public class EmployeRegist extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="联系电话",comment="联系电话")
	@Column(name="link_tel",length=100,nullable=true,unique=false)
	private String linkTel;

	@DColumn(index=4,label="性别",codeTable="Gender",comment="性别")
	@Column(name="sex",length=255,nullable=true,unique=false)
	private String sex;

	@DColumn(index=5,label="出生日期",comment="出生日期")
	@Column(name="birth_date",length=255,nullable=true,unique=false)
	private Date birthDate;

	@DColumn(index=6,label="机构名称",comment="机构名称")
	@Column(name="organization_name",length=200,nullable=true,unique=false)
	private String organizationName;

	@DColumn(index=7,label="密码",comment="密码")
	@Column(name="password",length=200,nullable=true,unique=false)
	private String password;

	@DColumn(index=8,label="审核状态",comment="审核状态:通过、未通过")
	@Column(name="audit_state",length=100,nullable=true,unique=false)
	private String auditState;

	@DColumn(index=9,label="邮箱",comment="邮箱")
	@Column(name="email",length=100,nullable=true,unique=false)
	private String email;

	@DColumn(index=10,label="审核人员",foreignEntity="Employee",comment="审核人员")
	@Column(name="audit_emloyee_id",length=255,nullable=true,unique=false)
	private Long auditEmloyeeId;

	@Transient
	private Employee auditEmloyee;

	@Transient
	@DColumn(index=10,label="审核人员",foreignEntity="Employee",comment="审核人员")
	private String auditEmloyeeName;

	@DColumn(index=11,label="审核时间",comment="审核时间")
	@Column(name="audit_date",length=255,nullable=true,unique=false)
	private Date auditDate;

	@DColumn(index=13,label="审核意见",comment="审核意见")
	@Column(name="audit_remark",length=200,nullable=true,unique=false)
	private String auditRemark;

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAuditEmloyeeId() {
		return this.auditEmloyeeId;
	}

	public void setAuditEmloyeeId(Long auditEmloyeeId) {
		this.auditEmloyeeId = auditEmloyeeId;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Employee getAuditEmloyee() {
		return this.auditEmloyee;
	}

	public void setAuditEmloyee(Employee auditEmloyee) {
		if(auditEmloyee == null){
			return;
		}
		this.auditEmloyeeId = auditEmloyee.getEId();
		this.auditEmloyee = auditEmloyee;
	}

	public String getAuditEmloyeeName() {
		return this.auditEmloyeeName;
	}

	public void setAuditEmloyeeName(String auditEmloyeeName) {
		this.auditEmloyeeName = auditEmloyeeName;
	}



}