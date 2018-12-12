package com.cisdi.info.simple.dto.permission;


import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.entity.permission.Role;

import java.util.List;

public class OperatorAndRoleEditDto {

    private OperatorAndRole operatorAndRole;


    //外键实体是：Operator
    private List<Operator> operatorOperators;
    //外键实体是：Role
    private List<Role> roleRoles;
    //外键实体是：Organization
    private List<Organization> organizationOrganizations;


    public OperatorAndRole getOperatorAndRole() {
        return this.operatorAndRole;
    }

    public void setOperatorAndRole(OperatorAndRole operatorAndRole) {
        this.operatorAndRole = operatorAndRole;
    }

    public List<Operator> getOperatorOperators() {
        return this.operatorOperators;
    }

    public void setOperatorOperators(List<Operator> operatorOperators) {
        this.operatorOperators = operatorOperators;
    }

    public List<Role> getRoleRoles() {
        return this.roleRoles;
    }

    public void setRoleRoles(List<Role> roleRoles) {
        this.roleRoles = roleRoles;
    }

    public List<Organization> getOrganizationOrganizations() {
        return this.organizationOrganizations;
    }

    public void setOrganizationOrganizations(List<Organization> organizationOrganizations) {
        this.organizationOrganizations = organizationOrganizations;
    }
}
