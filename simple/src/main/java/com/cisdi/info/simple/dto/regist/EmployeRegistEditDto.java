package com.cisdi.info.simple.dto.regist;


import com.cisdi.info.simple.entity.regist.EmployeRegist;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Employee;
public class EmployeRegistEditDto{

    private EmployeRegist employeRegist;

    //码表是：Gender
    private List<CodeTable> sexCodeTables;

    //外键实体是：Employee
    private List<Employee> auditEmloyeeEmployees;


    public  EmployeRegist getEmployeRegist()
    {
        return this.employeRegist;
    }
    public  void setEmployeRegist(EmployeRegist employeRegist)
    {
        this.employeRegist = employeRegist;
    }

    public List<CodeTable> getSexCodeTables()
    {
            return this.sexCodeTables;
    }
    public void setSexCodeTables(List<CodeTable> sexCodeTables)
    {
        this.sexCodeTables = sexCodeTables;
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
