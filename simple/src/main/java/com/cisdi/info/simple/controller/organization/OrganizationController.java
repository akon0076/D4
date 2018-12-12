

package com.cisdi.info.simple.controller.organization;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.organization.OrganizationEditDto;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.service.organization.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/organization/Organization": {
"code": "simple/organization/Organization",
"name1": "单位",
"url": "/simple/organization/Organization",
"route": "/simple/organization/Organization",
"iconClass": "",
"displayIndex": 1,
"parentCode": "simple/organization",
"parentName": "组织机构",
"moduleType": "电脑模块",
"isInUse": "是",
"routeParamsObj": "",
"permissions":
	[
	{
	"code": "simple_organization_Organization_Add",
	"name1": "新增",
	"fullName": "simple.组织机构.单位.新增",
	"moduleCode": "simple/organization/Organization",
	urls:[
		"/simple/organization/Organization/createOrganization",
		"/simple/organization/Organization/saveOrganization"
			
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_organization_Organization_Edit",
	"name1": "编辑",
	"fullName": "simple.组织机构.单位.编辑",
	"moduleCode": "simple/organization/Organization",
	urls:[
		"/simple/organization/Organization/findOrganizationForEdit",
		"/simple/organization/Organization/updateOrganization"
		
		,"/simple/organization/Organization/findOrganizationsWithIdNameByName"
	]
	},
	{
	"code": "simple_organization_Organization_Delete",
	"name1": "删除",
	"fullName": "simple.组织机构.单位.删除",
	"moduleCode": "simple/organization/Organization",
	urls:[
	"/simple/organization/Organization/deleteOrganization"
	]
	},
	{
	"code": "simple_organization_Organization_View",
	"name1": "查看",
	"fullName": "simple.组织机构.单位.查看",
	"moduleCode": "simple/organization/Organization",
	urls:[
	"/simple/organization/Organization/findOrganizations",
	"/simple/organization/Organization/findOrganizationForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/organization/Organization")
@CrossOrigin(allowCredentials = "true")
public class OrganizationController {
	private static Logger logger = LogManager.getLogger();



			
	@Autowired private OrganizationService organizationService;

	@PostMapping("/findOrganizations")
	public PageResultDTO findOrganizations(@RequestBody PageDTO pageDTO){
		return this.organizationService.findOrganizations(pageDTO);
	}

	@GetMapping("/findOrganization")
	public Organization findOrganization(@RequestParam Long organizationId)
	{
		return this.organizationService.findOrganization(organizationId);
	}

	@GetMapping("/findOrganizationForView")
	public Organization findOrganizationForView(@RequestParam Long organizationId)
	{
		return this.organizationService.findOrganizationWithForeignName(organizationId);
	}

	@GetMapping("/findOrganizationForEdit")
	public OrganizationEditDto findOrganizationForEdit(@RequestParam Long organizationId)
	{
		OrganizationEditDto organizationEditDto = new OrganizationEditDto();
		organizationEditDto.setOrganization(this.organizationService.findOrganizationWithForeignName(organizationId));

		this.prepareOrganizationEditDto(organizationEditDto);

		return organizationEditDto;
	}

	//创建新的单位
	@GetMapping("/createOrganization")
	public OrganizationEditDto createOrganization()
	{
		OrganizationEditDto organizationEditDto = new OrganizationEditDto();
		organizationEditDto.setOrganization(new Organization());

		this.prepareOrganizationEditDto(organizationEditDto);
		return organizationEditDto;
	}

	private void prepareOrganizationEditDto(OrganizationEditDto organizationEditDto)
	{
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		organizationEditDto.setParentOrganizations(this.organizationService.findAllOrganizationsWithIdName());
	}

	@PostMapping("/saveOrganization")
	public Organization saveOrganization(@RequestBody Organization organization)
	{
		return this.organizationService.saveOrganization(organization);
	}

	@PostMapping("/updateOrganization")
	public Organization updateOrganization(@RequestBody Organization organization)
	{
		return this.organizationService.updateOrganization(organization);
	}

	@GetMapping("/deleteOrganization")
	public void deleteOrganization(@RequestParam Long organizationId)
	{
		this.organizationService.deleteOrganization(organizationId);
	}
	@GetMapping("/findOrganizationsWithIdNameById")
	public Organization findOrganizationsWithIdNameById(@RequestParam Long organizationId)
	{
		return this.organizationService.findOrganizationsWithIdNameById(organizationId);
	}

	@GetMapping("/findOrganizationsWithIdNameByName")
	public List<Organization> findOrganizationsWithIdNameByName(String organizationName)
	{
		return this.organizationService.findOrganizationsWithIdNameByName(organizationName);
	}
}

