

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.OperatorAndRoleDto;
import com.cisdi.info.simple.dto.permission.OperatorAndRoleEditDto;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.service.organization.OrganizationService;
import com.cisdi.info.simple.service.permission.OperatorAndRoleService;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.permission.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/permission/OperatorAndRole": {
"code": "simple/permission/OperatorAndRole",
"name1": "操作员角色",
"url": "/simple/permission/OperatorAndRole",
"route": "/simple/permission/OperatorAndRole",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/permission",
"parentName": "授权",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_permission_OperatorAndRole_Add",
	"name1": "新增",
	"fullName": "simple.授权.操作员角色.新增",
	"moduleCode": "simple/permission/OperatorAndRole",
	urls:[
		"/simple/permission/OperatorAndRole/createOperatorAndRole",
		"/simple/permission/OperatorAndRole/saveOperatorAndRole"
			
			
			
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
		,"/simple/permission/Role/findRolesWithIdNameByName"
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_permission_OperatorAndRole_Edit",
	"name1": "编辑",
	"fullName": "simple.授权.操作员角色.编辑",
	"moduleCode": "simple/permission/OperatorAndRole",
	urls:[
		"/simple/permission/OperatorAndRole/findOperatorAndRoleForEdit",
		"/simple/permission/OperatorAndRole/updateOperatorAndRole"
		
		
		
		,"/simple/permission/Operator/findOperatorsWithIdNameByName"
		,"/simple/permission/Role/findRolesWithIdNameByName"
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_permission_OperatorAndRole_Delete",
	"name1": "删除",
	"fullName": "simple.授权.操作员角色.删除",
	"moduleCode": "simple/permission/OperatorAndRole",
	urls:[
	"/simple/permission/OperatorAndRole/deleteOperatorAndRole"
	]
	},
	{
	"code": "simple_permission_OperatorAndRole_View",
	"name1": "查看",
	"fullName": "simple.授权.操作员角色.查看",
	"moduleCode": "simple/permission/OperatorAndRole",
	urls:[
	"/simple/permission/OperatorAndRole/findOperatorAndRoles",
	"/simple/permission/OperatorAndRole/findOperatorAndRoleForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/permission/OperatorAndRole")
@CrossOrigin(allowCredentials = "true")
public class OperatorAndRoleController {
	private static Logger logger = LogManager.getLogger();



			
			
			
	@Autowired private OperatorAndRoleService operatorAndRoleService;
	@Autowired private OperatorService operatorService;
	@Autowired private RoleService roleService;
	@Autowired private OrganizationService organizationService;


	@GetMapping("/findOperatorAndRoleByOperatorId")
	public List<OperatorAndRole> findOperatorAndRoleByOperatorId(@RequestParam Long operatorId)
	{
		return this.operatorAndRoleService.findOperatorAndRoleByOperatorId(operatorId);
	}

	@PostMapping("/findOperatorAndRoles")
	public PageResultDTO findOperatorAndRoles(@RequestBody PageDTO pageDTO){
		return this.operatorAndRoleService.findOperatorAndRoles(pageDTO);
	}

	@GetMapping("/findOperatorAndRole")
	public OperatorAndRole findOperatorAndRole(@RequestParam Long operatorAndRoleId)
	{
		return this.operatorAndRoleService.findOperatorAndRole(operatorAndRoleId);
	}

	@GetMapping("/findOperatorAndRoleForView")
	public OperatorAndRole findOperatorAndRoleForView(@RequestParam Long operatorAndRoleId)
	{
		return this.operatorAndRoleService.findOperatorAndRoleWithForeignName(operatorAndRoleId);
	}

	@GetMapping("/findOperatorAndRoleForEdit")
	public OperatorAndRoleEditDto findOperatorAndRoleForEdit(@RequestParam Long operatorAndRoleId)
	{
		OperatorAndRoleEditDto operatorAndRoleEditDto = new OperatorAndRoleEditDto();
		operatorAndRoleEditDto.setOperatorAndRole(this.operatorAndRoleService.findOperatorAndRoleWithForeignName(operatorAndRoleId));

		this.prepareOperatorAndRoleEditDto(operatorAndRoleEditDto);

		return operatorAndRoleEditDto;
	}

	//创建新的操作员角色
	@GetMapping("/createOperatorAndRole")
	public OperatorAndRoleEditDto createOperatorAndRole()
	{
		OperatorAndRoleEditDto operatorAndRoleEditDto = new OperatorAndRoleEditDto();
		operatorAndRoleEditDto.setOperatorAndRole(new OperatorAndRole());

		this.prepareOperatorAndRoleEditDto(operatorAndRoleEditDto);
		return operatorAndRoleEditDto;
	}

	private void prepareOperatorAndRoleEditDto(OperatorAndRoleEditDto operatorAndRoleEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		operatorAndRoleEditDto.setOperatorOperators(this.operatorService.findAllOperatorsWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		operatorAndRoleEditDto.setRoleRoles(this.roleService.findAllRolesWithIdName());
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		operatorAndRoleEditDto.setOrganizationOrganizations(this.organizationService.findAllOrganizationsWithIdName());
	}

	@PostMapping("/saveOperatorAndRole")
	public OperatorAndRole saveOperatorAndRole(@RequestBody OperatorAndRole operatorAndRole)
	{
		return this.operatorAndRoleService.saveOperatorAndRole(operatorAndRole);
	}

	@PostMapping("/updateOperatorAndRole")
	public void updateOperatorAndRole(@RequestBody OperatorAndRoleDto operatorAndRoleDto)
	{
		this.operatorAndRoleService.updateOperatorAndRole(operatorAndRoleDto);
	}

	@GetMapping("/deleteOperatorAndRole")
	public void deleteOperatorAndRole(@RequestParam Long operatorAndRoleId)
	{
		this.operatorAndRoleService.deleteOperatorAndRole(operatorAndRoleId);
	}
	@GetMapping("/findOperatorAndRolesWithIdNameById")
	public OperatorAndRole findOperatorAndRolesWithIdNameById(@RequestParam Long operatorAndRoleId)
	{
		return null;//this.operatorAndRoleService.findOperatorAndRolesWithIdNameById(operatorAndRoleId);
	}

	@GetMapping("/findOperatorAndRolesWithIdNameByName")
	public List<OperatorAndRole> findOperatorAndRolesWithIdNameByName(String operatorAndRoleName)
	{
		return null;//this.operatorAndRoleService.findOperatorAndRolesWithIdNameByName(operatorAndRoleName);
	}
}

