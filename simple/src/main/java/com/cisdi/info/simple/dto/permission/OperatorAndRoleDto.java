package com.cisdi.info.simple.dto.permission;

/**
 * @author:chengbg
 * @date:2018/9/3
 */
public class OperatorAndRoleDto {
    private Long operatorId;
    private Long[] roleId;
    private Long organizationId;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long[] getRoleId() {
        return roleId;
    }

    public void setRoleId(Long[] roleId) {
        this.roleId = roleId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
