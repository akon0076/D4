package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@DEntity(label="角色权限点",comment="",moduleLabel="授权")
@Entity(name="simple_role_and_permission")
public class RoleAndPermission extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="权限点编码",comment="权限点编码")
	@Column(name="permission_code",length=200,nullable=false,unique=false)
	private String permissionCode;

	@DColumn(index=4,label="角色Id",comment="角色Id")
	@Column(name="roleId",length=200,nullable=false,unique=false)
	private Long roleId;

	@DColumn(index=5,label="模块编码",comment="模块编码")
	@Column(name="moduleCode",length=200,nullable=false,unique=false)
	private String moduleCode;


	public RoleAndPermission() {
	}

	public RoleAndPermission(Long roleId,String permissionCode) {
		this.permissionCode = permissionCode;
		this.roleId = roleId;
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}


	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
}