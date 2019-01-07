package com.cisdi.info.simple.entity.organization;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;


import java.util.List;

@DEntity(label="部门",comment="",moduleLabel="部门")
@Entity(name="simple_department")
public class Department extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="部门编码",comment="部门编码")
	@Column(name="code",length=255,nullable=false,unique=true)
	private String code;

	@DColumn(index=4,label="所属单位",foreignEntity="Organization",comment="所属单位")
	@Column(name="organization_id",length=255,nullable=true,unique=false)
	private Long organizationId;

	@Transient
	private Organization organization;

	@Transient
	@DColumn(index=4,label="所属单位",foreignEntity="Organization",comment="所属单位")
	private String organizationName;

	@DColumn(index=5,label="办公电话",comment="办公电话")
	@Column(name="office_phone_number",length=255,nullable=true,unique=false)
	private String officePhoneNumber;

	@DColumn(index=6,label="传真",comment="传真")
	@Column(name="fax_number",length=255,nullable=true,unique=false)
	private String faxNumber;

	@DColumn(index=7,label="部门地址",comment="部门地址")
	@Column(name="address",length=255,nullable=true,unique=false)
	private String address;

	@Transient
	private List<Employee> employees;

	@DColumn(index=9,label="是否启用",comment="是否启用")
	@Column(name="enable",length=255,nullable=true,unique=false)
	private Boolean enable;


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOfficePhoneNumber() {
		return this.officePhoneNumber;
	}

	public void setOfficePhoneNumber(String officePhoneNumber) {
		this.officePhoneNumber = officePhoneNumber;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Boolean getEnable() {
		return this.enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
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


	
}