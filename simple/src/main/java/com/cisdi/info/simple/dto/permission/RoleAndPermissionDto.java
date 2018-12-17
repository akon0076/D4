package com.cisdi.info.simple.dto.permission;

/**
 * @author:chengbg
 * @date:2018/9/6
 */
public class RoleAndPermissionDto {
    private Long roleId;
    private String[] addPermissions;
    private String[] removePermissions;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String[] getAddPermissions() {
        return addPermissions;
    }

    public void setAddPermissions(String[] addPermissions) {
        this.addPermissions = addPermissions;
    }

    public String[] getRemovePermissions() {
        return removePermissions;
    }

    public void setRemovePermissions(String[] removePermissions) {
        this.removePermissions = removePermissions;
    }
}
