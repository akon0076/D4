

package com.cisdi.info.simple.controller.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.organization.DepartmentEditDto;
import com.cisdi.info.simple.entity.organization.Department;
import com.cisdi.info.simple.service.organization.DepartmentService;
import com.cisdi.info.simple.service.organization.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/organization/Department": {
"code": "simple/organization/Department",
"name1": "部门",
"url": "/simple/organization/Department",
"route": "/simple/organization/Department",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/organization",
"parentName": "部门",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_organization_Department_Add",
	"name1": "新增",
	"fullName": "simple.部门.部门.新增",
	"moduleCode": "simple/organization/Department",
	urls:[
		"/simple/organization/Department/createDepartment",
		"/simple/organization/Department/saveDepartment"
			
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_organization_Department_Edit",
	"name1": "编辑",
	"fullName": "simple.部门.部门.编辑",
	"moduleCode": "simple/organization/Department",
	urls:[
		"/simple/organization/Department/findDepartmentForEdit",
		"/simple/organization/Department/updateDepartment"
		
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_organization_Department_Delete",
	"name1": "删除",
	"fullName": "simple.部门.部门.删除",
	"moduleCode": "simple/organization/Department",
	urls:[
	"/simple/organization/Department/deleteDepartment"
	]
	},
	{
	"code": "simple_organization_Department_View",
	"name1": "查看",
	"fullName": "simple.部门.部门.查看",
	"moduleCode": "simple/organization/Department",
	urls:[
	"/simple/organization/Department/findDepartments",
	"/simple/organization/Department/findDepartmentForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/organization/Department")
@CrossOrigin(allowCredentials = "true")
public class DepartmentController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired private DepartmentService departmentService;
	@Autowired private OrganizationService organizationService;

	@PostMapping("/findDepartments")
	public PageResultDTO findDepartments(@RequestBody PageDTO pageDTO){
		return this.departmentService.findDepartments(pageDTO);
	}

	@GetMapping("/findAllDepartmentsByOrgId")
	public List<Department> findDepartments(@RequestParam Long orgId){
		return this.departmentService.findAllDepartmentsByOrgId(orgId);
	}

	@GetMapping("/findDepartment")
	public Department findDepartment(@RequestParam Long departmentId)
	{
		return this.departmentService.findDepartment(departmentId);
	}

	@GetMapping("/findDepartmentForView")
	public Department findDepartmentForView(@RequestParam Long departmentId)
	{
		return this.departmentService.findDepartmentWithForeignName(departmentId);
	}

	@GetMapping("/findDepartmentForEdit")
	public DepartmentEditDto findDepartmentForEdit(@RequestParam Long departmentId)
	{
		DepartmentEditDto departmentEditDto = new DepartmentEditDto();
		departmentEditDto.setDepartment(this.departmentService.findDepartmentWithForeignName(departmentId));

		this.prepareDepartmentEditDto(departmentEditDto);

		return departmentEditDto;
	}

	//创建新的部门
	@GetMapping("/createDepartment")
	public DepartmentEditDto createDepartment()
	{
		DepartmentEditDto departmentEditDto = new DepartmentEditDto();
		departmentEditDto.setDepartment(new Department());

		this.prepareDepartmentEditDto(departmentEditDto);
		return departmentEditDto;
	}

	private void prepareDepartmentEditDto(DepartmentEditDto departmentEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		departmentEditDto.setOrganizationOrganizations(this.organizationService.findAllOrganizationsWithIdName());
	}

	@PostMapping("/saveDepartment")
	public Department saveDepartment(@RequestBody Department department)
	{
		return this.departmentService.saveDepartment(department);
	}

	@PostMapping("/updateDepartment")
	public Department updateDepartment(@RequestBody Department department)
	{
		return this.departmentService.updateDepartment(department);
	}

	@GetMapping("/deleteDepartment")
	public void deleteDepartment(@RequestParam Long departmentId)
	{
		this.departmentService.deleteDepartment(departmentId);
	}
	@GetMapping("/findDepartmentsWithIdNameById")
	public Department findDepartmentsWithIdNameById(@RequestParam Long departmentId)
	{
		return this.departmentService.findDepartmentsWithIdNameById(departmentId);
	}

	@GetMapping("/findDepartmentsWithIdNameByName")
	public List<Department> findDepartmentsWithIdNameByName(String departmentName)
	{
		return this.departmentService.findDepartmentsWithIdNameByName(departmentName);
	}
}

