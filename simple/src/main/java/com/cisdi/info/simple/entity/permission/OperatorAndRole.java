package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.entity.base.*;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.Role;

@DEntity(label="操作员角色",comment="",moduleLabel="授权")
@Entity(name="simple_operator_and_role")
public class OperatorAndRole extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="操作员",foreignEntity="Operator",comment="编码")
	@Column(name="operator_id",length=250,nullable=false,unique=false)
	private Long operatorId;

	@Transient
	private Operator operator;

	@Transient
	@DColumn(index=3,label="操作员",foreignEntity="Operator",comment="编码")
	private String operatorName;

	@DColumn(index=4,label="角色",foreignEntity="Role",comment="角色")
	@Column(name="role_id",length=250,nullable=false,unique=false)
	private Long roleId;

	@Transient
	private Role role;

	@Transient
	@DColumn(index=4,label="角色",foreignEntity="Role",comment="角色")
	private String roleName;

	@DColumn(index=5,label="单位",foreignEntity="Organization",comment="单位")
	@Column(name="organization_id",length=250,nullable=false,unique=false)
	private Long organizationId;

	@Transient
	private Organization organization;

	@Transient
	@DColumn(index=5,label="单位",foreignEntity="Organization",comment="单位")
	private String organizationName;


	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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