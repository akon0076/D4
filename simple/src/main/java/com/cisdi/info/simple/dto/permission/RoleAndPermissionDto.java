package com.cisdi.info.simple.dto.permission;

/**
 * @author:chengbg
 * @date:2018/9/6
 */
public class RoleAndPermissionDto {
    private Long roleId;
    private String[] permissionsCode;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String[] getPermissionsCode() {
        return permissionsCode;
    }

    public void setPermissionsCode(String[] permissionsCode) {
        this.permissionsCode = permissionsCode;
    }
}
