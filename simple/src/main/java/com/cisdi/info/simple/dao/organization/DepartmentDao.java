package com.cisdi.info.simple.dao.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.organization.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "departmentDao")
public interface DepartmentDao {

    public List<Department> findDepartments(PageDTO pageDTO);

    public List<Department> findAllDepartments();

    public List<Department> findAllDepartmentsByOrgId(Long orgId);

    public List<Department> findAllDepartmentsWithIdName();

    public List<Department> findDepartmentsWithIdNameByName(@Param("departmentName") String departmentName);

    public Department findDepartmentsWithIdNameById(@Param(" departmentId") Long departmentId);

    public Long findDepartmentTotalCount(PageDTO pageDTO);

    public Department findDepartment(@Param("departmentId") Long departmentId);

    //所有外键的Name都以加载
    public Department findDepartmentWithForeignName(@Param("departmentId") Long departmentId);

    public Integer saveDepartment(Department department);

    public Integer updateDepartment(Department department);

    public Integer deleteDepartment(@Param("departmentId") Long departmentId);
}
