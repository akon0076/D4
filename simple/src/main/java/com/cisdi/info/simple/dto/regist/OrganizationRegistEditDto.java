package com.cisdi.info.simple.dto.regist;


import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Employee;
public class OrganizationRegistEditDto{

    private OrganizationRegist organizationRegist;


    //外键实体是：Employee
    private List<Employee> auditEmloyeeEmployees;


    public  OrganizationRegist getOrganizationRegist()
    {
        return this.organizationRegist;
    }
    public  void setOrganizationRegist(OrganizationRegist organizationRegist)
    {
        this.organizationRegist = organizationRegist;
    }

    public List<Employee> getAuditEmloyeeEmployees()
    {
        return this.auditEmloyeeEmployees;
    }
    public void setAuditEmloyeeEmployees(List<Employee> auditEmloyeeEmployees)
    {
        this.auditEmloyeeEmployees = auditEmloyeeEmployees;
    }
}
