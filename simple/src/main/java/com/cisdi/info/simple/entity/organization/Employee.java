package com.cisdi.info.simple.entity.organization;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="职员",comment="",moduleLabel="组织机构")
@Entity(name="simple_employee")
public class Employee extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="编码",comment="编码")
	@Column(name="code",length=200,nullable=false,unique=true)
	private String code;

	@DColumn(index=4,label="联系电话",comment="联系电话")
	@Column(name="link_tel",length=100,nullable=true,unique=false)
	private String linkTel;

	@DColumn(index=5,label="所属单位",foreignEntity="Organization",comment="所属单位")
	@Column(name="organization_id",length=250,nullable=true,unique=false)
	private Long organizationId;

	@Transient
	private Organization organization;

	@Transient
	@DColumn(index=5,label="所属单位",foreignEntity="Organization",comment="所属单位")
	private String organizationName;

	@DColumn(index=6,label="性别",codeTable="Gender",comment="性别")
	@Column(name="sex",length=250,nullable=true,unique=false)
	private String sex;

	@DColumn(index=7,label="出生日期",comment="出生日期")
	@Column(name="birth_date",length=250,nullable=true,unique=false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDate;

	@DColumn(index=8,label="所属部门",foreignEntity="Department",comment="所属部门")
	@Column(name="department_id",length=250,nullable=true,unique=false)
	private Long departmentId;

	@Transient
	private Department department;

	@Transient
	private String passWord;

	@Transient
	@DColumn(index=8,label="所属部门",foreignEntity="Department",comment="所属部门")
	private String departmentName;

	@DColumn(index=9,label="邮箱",comment="邮箱")
	@Column(name="email",length=200,nullable=true,unique=false)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}


	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}