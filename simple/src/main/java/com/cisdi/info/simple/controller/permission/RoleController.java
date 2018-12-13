

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.RoleEditDto;
import com.cisdi.info.simple.entity.permission.Role;
import com.cisdi.info.simple.service.permission.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/permission/Role": {
"code": "simple/permission/Role",
"name1": "角色",
"url": "/simple/permission/Role",
"route": "/simple/permission/Role",
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
	"code": "simple_permission_Role_Add",
	"name1": "新增",
	"fullName": "simple.授权.角色.新增",
	"moduleCode": "simple/permission/Role",
	urls:[
		"/simple/permission/Role/createRole",
		"/simple/permission/Role/saveRole"
	]
	},
	{
	"code": "simple_permission_Role_Edit",
	"name1": "编辑",
	"fullName": "simple.授权.角色.编辑",
	"moduleCode": "simple/permission/Role",
	urls:[
		"/simple/permission/Role/findRoleForEdit",
		"/simple/permission/Role/updateRole"
	]
	},
	{
	"code": "simple_permission_Role_Delete",
	"name1": "删除",
	"fullName": "simple.授权.角色.删除",
	"moduleCode": "simple/permission/Role",
	urls:[
	"/simple/permission/Role/deleteRole"
	]
	},
	{
	"code": "simple_permission_Role_View",
	"name1": "查看",
	"fullName": "simple.授权.角色.查看",
	"moduleCode": "simple/permission/Role",
	urls:[
	"/simple/permission/Role/findRoles",
	"/simple/permission/Role/findRoleForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/permission/Role")
@CrossOrigin(allowCredentials = "true")
public class RoleController {
	private static Logger logger = LogManager.getLogger();



	@Autowired private RoleService roleService;

	@GetMapping("/findAllRoles")
	public List<Role> findAllRoles(){
		return this.roleService.findAllRoles();
	}

	@PostMapping("/findRoles")
	public PageResultDTO findRoles(@RequestBody PageDTO pageDTO){
		return this.roleService.findRoles(pageDTO);
	}

	@GetMapping("/findRole")
	public Role findRole(@RequestParam Long roleId)
	{
		return this.roleService.findRole(roleId);
	}

	@GetMapping("/findRoleForView")
	public Role findRoleForView(@RequestParam Long roleId)
	{
		return this.roleService.findRoleWithForeignName(roleId);
	}

	@GetMapping("/findRoleForEdit")
	public RoleEditDto findRoleForEdit(@RequestParam Long roleId)
	{
		RoleEditDto roleEditDto = new RoleEditDto();
		roleEditDto.setRole(this.roleService.findRoleWithForeignName(roleId));

		this.prepareRoleEditDto(roleEditDto);

		return roleEditDto;
	}

	//创建新的角色
	@GetMapping("/createRole")
	public RoleEditDto createRole()
	{
		RoleEditDto roleEditDto = new RoleEditDto();
		roleEditDto.setRole(new Role());

		this.prepareRoleEditDto(roleEditDto);
		return roleEditDto;
	}

	private void prepareRoleEditDto(RoleEditDto roleEditDto)
	{
	}

	@PostMapping("/saveRole")
	public Long saveRole(@RequestBody Role role)
	{
		return this.roleService.saveRole(role);
	}

	@PostMapping("/updateRole")
	public Role updateRole(@RequestBody Role role)
	{
		return this.roleService.updateRole(role);
	}

	@GetMapping("/deleteRole")
	public void deleteRole(@RequestParam Long roleId)
	{
		this.roleService.deleteRole(roleId);
	}
	@GetMapping("/findRolesWithIdNameById")
	public Role findRolesWithIdNameById(@RequestParam Long roleId)
	{
		return null;//this.roleService.findRolesWithIdNameById(roleId);
	}

	@GetMapping("/findRolesWithIdNameByName")
	public List<Role> findRolesWithIdNameByName(String roleName)
	{
		return null;//this.roleService.findRolesWithIdNameByName(roleName);
	}
}

