package com.cisdi.info.simple.dto.qingTui;


import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import com.cisdi.info.simple.entity.system.CodeTable;
import java.util.List;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.member.Member;
public class EmpOpenIdEditDto{

    private EmpOpenId empOpenId;


    //外键实体是：Employee
    private List<Employee> employeeEmployees;
    //外键实体是：Operator
    private List<Operator> operatorOperators;
    //外键实体是：Member
    private List<Member> memberMembers;


    public  EmpOpenId getEmpOpenId()
    {
        return this.empOpenId;
    }
    public  void setEmpOpenId(EmpOpenId empOpenId)
    {
        this.empOpenId = empOpenId;
    }

    public List<Employee> getEmployeeEmployees()
    {
        return this.employeeEmployees;
    }
    public void setEmployeeEmployees(List<Employee> employeeEmployees)
    {
        this.employeeEmployees = employeeEmployees;
    }
    public List<Operator> getOperatorOperators()
    {
        return this.operatorOperators;
    }
    public void setOperatorOperators(List<Operator> operatorOperators)
    {
        this.operatorOperators = operatorOperators;
    }
    public List<Member> getMemberMembers()
    {
        return this.memberMembers;
    }
    public void setMemberMembers(List<Member> memberMembers)
    {
        this.memberMembers = memberMembers;
    }
}
