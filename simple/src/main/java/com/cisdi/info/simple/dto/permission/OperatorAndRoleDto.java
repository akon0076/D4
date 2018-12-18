package com.cisdi.info.simple.dto.permission;

/**
 * @author:chengbg
 * @date:2018/9/3
 */
public class OperatorAndRoleDto {
    private Long operatorId;
    private Long organizationId;
    private Long[] addRoles;
    private Long[] removeRoles;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long[] getAddRoles() {
        return addRoles;
    }

    public void setAddRoles(Long[] addRoles) {
        this.addRoles = addRoles;
    }

    public Long[] getRemoveRoles() {
        return removeRoles;
    }

    public void setRemoveRoles(Long[] removeRoles) {
        this.removeRoles = removeRoles;
    }
}
