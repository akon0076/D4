

package com.cisdi.info.simple.controller.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.organization.EmployeeEditDto;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.service.organization.DepartmentService;
import com.cisdi.info.simple.service.organization.EmployeeService;
import com.cisdi.info.simple.service.organization.OrganizationService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * module
 * {
 * "simple/organization/Employee": {
 * "code": "simple/organization/Employee",
 * "name1": "职员",
 * "url": "/simple/organization/Employee",
 * "route": "/simple/organization/Employee",
 * "iconClass": "",
 * "displayIndex": 1,
 * "parentCode": "simple/organization",
 * "parentName": "组织机构",
 * "moduleType": "电脑模块",
 * "isInUse": "是",
 * "routeParamsObj": "",
 * "permissions":
 * [
 * {
 * "code": "simple_organization_Employee_Add",
 * "name1": "新增",
 * "fullName": "simple.组织机构.职员.新增",
 * "moduleCode": "simple/organization/Employee",
 * urls:[
 * "/simple/organization/Employee/createEmployee",
 * "/simple/organization/Employee/saveEmployee"
 * <p>
 * <p>
 * ,"/simple/organization/Department/findDepartmentsWithIdNameByName"
 * ,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_organization_Employee_Edit",
 * "name1": "编辑",
 * "fullName": "simple.组织机构.职员.编辑",
 * "moduleCode": "simple/organization/Employee",
 * urls:[
 * "/simple/organization/Employee/findEmployeeForEdit",
 * "/simple/organization/Employee/updateEmployee"
 * <p>
 * <p>
 * ,"/simple/organization/Department/findDepartmentsWithIdNameByName"
 * ,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_organization_Employee_Delete",
 * "name1": "删除",
 * "fullName": "simple.组织机构.职员.删除",
 * "moduleCode": "simple/organization/Employee",
 * urls:[
 * "/simple/organization/Employee/deleteEmployee"
 * ]
 * },
 * {
 * "code": "simple_organization_Employee_View",
 * "name1": "查看",
 * "fullName": "simple.组织机构.职员.查看",
 * "moduleCode": "simple/organization/Employee",
 * urls:[
 * "/simple/organization/Employee/findEmployees",
 * "/simple/organization/Employee/findEmployeeForView"
 * ]
 * }
 * ]
 * }
 * }
 */

@RestController
@RequestMapping("/simple/organization/Employee")
@CrossOrigin(allowCredentials = "true")
public class EmployeeController {
    private static Logger logger = LogManager.getLogger();


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CodeTableService codeTableService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/findEmployees")
    public PageResultDTO findEmployees(@RequestBody PageDTO pageDTO) {
        return this.employeeService.findEmployees(pageDTO);
    }

    @GetMapping("/findEmployee")
    public Employee findEmployee(@RequestParam Long employeeId) {
        return this.employeeService.findEmployee(employeeId);
    }

    @GetMapping("/findEmployeeForView")
    public Employee findEmployeeForView(@RequestParam Long employeeId) {
        return this.employeeService.findEmployeeWithForeignName(employeeId);
    }

    @GetMapping("/findEmployeeForEdit")
    public EmployeeEditDto findEmployeeForEdit(@RequestParam Long employeeId) {
        EmployeeEditDto employeeEditDto = new EmployeeEditDto();
        employeeEditDto.setEmployee(this.employeeService.findEmployeeWithForeignName(employeeId));

        this.prepareEmployeeEditDto(employeeEditDto);
        return employeeEditDto;
    }

    //创建新的职员
    @GetMapping("/createEmployee")
    public EmployeeEditDto createEmployee() {
        EmployeeEditDto employeeEditDto = new EmployeeEditDto();
        employeeEditDto.setEmployee(new Employee());

        this.prepareEmployeeEditDto(employeeEditDto);
        return employeeEditDto;
    }

    private void prepareEmployeeEditDto(EmployeeEditDto employeeEditDto) {
        employeeEditDto.setSexCodeTables(this.codeTableService.findCodeTableByCode("sex"));
        employeeEditDto.setDepartmentDepartments(this.departmentService.findAllDepartmentsWithIdName());
        employeeEditDto.setOrganizationOrganizations(this.organizationService.findAllOrganizationsWithIdName());
    }

    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return this.employeeService.saveEmployee(employee);
    }

    @PostMapping("/updateEmployee")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return this.employeeService.updateEmployee(employee);
    }

    @GetMapping("/deleteEmployee")
    public void deleteEmployee(@RequestParam Long employeeId) {
        this.employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("/findEmployeesWithIdNameById")
    public Employee findEmployeesWithIdNameById(@RequestParam Long employeeId) {
        return this.employeeService.findEmployeesWithIdNameById(employeeId);
    }

    @GetMapping("/findEmployeesWithIdNameByName")
    public List<Employee> findEmployeesWithIdNameByName(String employeeName) {
        return this.employeeService.findEmployeesWithIdNameByName(employeeName);
    }

    @GetMapping("/findAllEmployees")
    public List<Employee> findAllEmployees() {
        return this.employeeService.findAllEmployees();
    }
}

