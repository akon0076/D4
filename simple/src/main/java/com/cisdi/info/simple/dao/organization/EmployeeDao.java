package com.cisdi.info.simple.dao.organization;


import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.organization.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "employeeDao")
public interface EmployeeDao {

    public List<Employee> findEmployees(PageDTO pageDTO);

    public List<Employee> findAllEmployees();

    public List<Employee> findAllEmployeesWithIdName();

    public List<Employee> findEmployeesWithIdNameByName(String employeeName);

    public Employee findEmployeesWithIdNameById(Long employeeId);

    public Long findEmployeeTotalCount(PageDTO pageDTO);

    public Employee findEmployee(@Param("employeeId") Long employeeId);


    /* public Member findMember(@Param("memberId") Long memberId);*/

    //所有外键的Name都以加载
    public Employee findEmployeeWithForeignName(@Param("employeeId") Long employeeId);

    public Integer saveEmployee(Employee employee);

    public Integer updateEmployee(Employee employee);

    public Integer deleteEmployee(@Param("employeeId") Long employeeId);

    public Employee findEmployeeByCode(String code);
}
