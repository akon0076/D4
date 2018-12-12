package com.cisdi.info.simple.dto.organization;


import com.cisdi.info.simple.entity.organization.Department;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Organization;
public class DepartmentEditDto{

    private Department department;


    //外键实体是：Organization
    private List<Organization> organizationOrganizations;


    public  Department getDepartment()
    {
        return this.department;
    }
    public  void setDepartment(Department department)
    {
        this.department = department;
    }

    public List<Organization> getOrganizationOrganizations()
    {
        return this.organizationOrganizations;
    }
    public void setOrganizationOrganizations(List<Organization> organizationOrganizations)
    {
        this.organizationOrganizations = organizationOrganizations;
    }
}
