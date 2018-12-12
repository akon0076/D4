

package com.cisdi.info.simple.controller.regist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.dto.regist.OrganizationRegistEditDto;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import com.cisdi.info.simple.service.regist.OrganizationRegistService;
import com.cisdi.info.simple.service.organization.EmployeeService;



/**module
{
"simple/regist/OrganizationRegist": {
"code": "simple/regist/OrganizationRegist",
"name1": "单位注册",
"url": "/simple/regist/OrganizationRegist",
"route": "/simple/regist/OrganizationRegist",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/regist",
"parentName": "注册管理",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_regist_OrganizationRegist_Add",
	"name1": "新增",
	"fullName": "simple.注册管理.单位注册.新增",
	"moduleCode": "simple/regist/OrganizationRegist",
	urls:[
		"/simple/regist/OrganizationRegist/createOrganizationRegist",
		"/simple/regist/OrganizationRegist/saveOrganizationRegist"
			
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
	]
	},
	{
	"code": "simple_regist_OrganizationRegist_Edit",
	"name1": "编辑",
	"fullName": "simple.注册管理.单位注册.编辑",
	"moduleCode": "simple/regist/OrganizationRegist",
	urls:[
		"/simple/regist/OrganizationRegist/findOrganizationRegistForEdit",
		"/simple/regist/OrganizationRegist/updateOrganizationRegist"
		
		,"/simple/organization/Employee/findEmployeesWithIdNameByName"
	]
	},
	{
	"code": "simple_regist_OrganizationRegist_Delete",
	"name1": "删除",
	"fullName": "simple.注册管理.单位注册.删除",
	"moduleCode": "simple/regist/OrganizationRegist",
	urls:[
	"/simple/regist/OrganizationRegist/deleteOrganizationRegist"
	]
	},
	{
	"code": "simple_regist_OrganizationRegist_View",
	"name1": "查看",
	"fullName": "simple.注册管理.单位注册.查看",
	"moduleCode": "simple/regist/OrganizationRegist",
	urls:[
	"/simple/regist/OrganizationRegist/findOrganizationRegists",
	"/simple/regist/OrganizationRegist/findOrganizationRegistForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/regist/OrganizationRegist")
@CrossOrigin(allowCredentials = "true")
public class OrganizationRegistController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired private OrganizationRegistService organizationRegistService;
	@Autowired private EmployeeService employeeService;

	@PostMapping("/findOrganizationRegists")
	public PageResultDTO findOrganizationRegists(@RequestBody PageDTO pageDTO){
		return this.organizationRegistService.findOrganizationRegists(pageDTO);
	}

	@GetMapping("/findOrganizationRegist")
	public OrganizationRegist findOrganizationRegist(@RequestParam Long organizationRegistId)
	{
		return this.organizationRegistService.findOrganizationRegist(organizationRegistId);
	}

	@GetMapping("/findOrganizationRegistForView")
	public OrganizationRegist findOrganizationRegistForView(@RequestParam Long organizationRegistId)
	{
		return this.organizationRegistService.findOrganizationRegistWithForeignName(organizationRegistId);
	}

	@GetMapping("/findOrganizationRegistForEdit")
	public OrganizationRegistEditDto findOrganizationRegistForEdit(@RequestParam Long organizationRegistId)
	{
		OrganizationRegistEditDto organizationRegistEditDto = new OrganizationRegistEditDto();
		organizationRegistEditDto.setOrganizationRegist(this.organizationRegistService.findOrganizationRegistWithForeignName(organizationRegistId));

		this.prepareOrganizationRegistEditDto(organizationRegistEditDto);

		return organizationRegistEditDto;
	}

	//创建新的单位注册
	@GetMapping("/createOrganizationRegist")
	public OrganizationRegistEditDto createOrganizationRegist()
	{
		OrganizationRegistEditDto organizationRegistEditDto = new OrganizationRegistEditDto();
		organizationRegistEditDto.setOrganizationRegist(new OrganizationRegist());

		this.prepareOrganizationRegistEditDto(organizationRegistEditDto);
		return organizationRegistEditDto;
	}

	private void prepareOrganizationRegistEditDto(OrganizationRegistEditDto organizationRegistEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		organizationRegistEditDto.setAuditEmloyeeEmployees(this.employeeService.findAllEmployeesWithIdName());
	}

	@PostMapping("/saveOrganizationRegist")
	public OrganizationRegist saveOrganizationRegist(@RequestBody OrganizationRegist organizationRegist)
	{
		return this.organizationRegistService.saveOrganizationRegist(organizationRegist);
	}

	@PostMapping("/updateOrganizationRegist")
	public OrganizationRegist updateOrganizationRegist(HttpServletRequest request, @RequestBody OrganizationRegist organizationRegist)
	{
		return this.organizationRegistService.updateOrganizationRegist(request,organizationRegist);
	}

	@GetMapping("/deleteOrganizationRegist")
	public void deleteOrganizationRegist(@RequestParam Long organizationRegistId)
	{
		this.organizationRegistService.deleteOrganizationRegist(organizationRegistId);
	}
	@GetMapping("/findOrganizationRegistsWithIdNameById")
	public OrganizationRegist findOrganizationRegistsWithIdNameById(@RequestParam Long organizationRegistId)
	{
		return this.organizationRegistService.findOrganizationRegistsWithIdNameById(organizationRegistId);
	}

	@GetMapping("/findOrganizationRegistsWithIdNameByName")
	public List<OrganizationRegist> findOrganizationRegistsWithIdNameByName(String organizationRegistName)
	{
		return this.organizationRegistService.findOrganizationRegistsWithIdNameByName(organizationRegistName);
	}
}

