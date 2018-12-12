package com.cisdi.info.simple.dto.organization;


import com.cisdi.info.simple.entity.organization.Department;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.system.CodeTable;

import java.util.List;
public class EmployeeEditDto{

    private Employee employee;

    //码表是：Gender
    private List<CodeTable> sexCodeTables;

    //外键实体是：Department
    private List<Department> departmentDepartments;
    //外键实体是：Organization
    private List<Organization> organizationOrganizations;


    public  Employee getEmployee()
    {
        return this.employee;
    }
    public  void setEmployee(Employee employee)
    {
        this.employee = employee;
    }

    public List<CodeTable> getSexCodeTables()
    {
            return this.sexCodeTables;
    }
    public void setSexCodeTables(List<CodeTable> sexCodeTables)
    {
        this.sexCodeTables = sexCodeTables;
    }

    public List<Department> getDepartmentDepartments()
    {
        return this.departmentDepartments;
    }
    public void setDepartmentDepartments(List<Department> departmentDepartments)
    {
        this.departmentDepartments = departmentDepartments;
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
