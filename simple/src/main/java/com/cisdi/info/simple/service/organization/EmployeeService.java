package com.cisdi.info.simple.service.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.organization.Employee;

import java.util.List;

public interface EmployeeService {

    public PageResultDTO findEmployees(PageDTO pageDTO);

    public List<Employee> findAllEmployees();

    public List<Employee> findEmployeesWithIdNameByName(String employeeName);

    public List<Employee> findAllEmployeesWithIdName();

    public Employee findEmployeesWithIdNameById(Long employeeId);

    public Employee findEmployee(Long employeeId);


    /*public Member findMember(Long memberId);*/
    //所有外键的Name都以加载
    public Employee findEmployeeWithForeignName(Long employeeId);

    public Employee saveEmployee(Employee employee);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);
}
