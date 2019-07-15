package com.cisdi.info.simple.service.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.organization.Department;

import java.util.List;

public interface DepartmentService {

    public PageResultDTO findDepartments(PageDTO pageDTO);

    public List<Department> findAllDepartments();

    public List<Department> findAllDepartmentsByOrgId(Long orgId);

    public List<Department> findDepartmentsWithIdNameByName(String departmentName);

    public List<Department> findAllDepartmentsWithIdName();

    public Department findDepartmentsWithIdNameById(Long departmentId);

    public Department findDepartment(Long departmentId);

    //所有外键的Name都以加载
    public Department findDepartmentWithForeignName(Long departmentId);

    public Department saveDepartment(Department department);

    public Department updateDepartment(Department department);

    public void deleteDepartment(Long departmentId);
}
