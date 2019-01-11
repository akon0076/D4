

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.ModuleEditDto;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**module
{
"simple/permission/Module": {
"code": "simple/permission/Module",
"name1": "模块",
"url": "/simple/permission/Module",
"route": "/simple/permission/Module",
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
	"code": "simple_permission_Module_Add",
	"name1": "新增",
	"fullName": "simple.授权.模块.新增",
	"moduleCode": "simple/permission/Module",
	urls:[
		"/simple/permission/Module/createModule",
		"/simple/permission/Module/saveModule"

		,"/simple/permission/Module/findModulesWithIdNameByName"
	]
	},
	{
	"code": "simple_permission_Module_Edit",
	"name1": "编辑",
	"fullName": "simple.授权.模块.编辑",
	"moduleCode": "simple/permission/Module",
	urls:[
		"/simple/permission/Module/findModuleForEdit",
		"/simple/permission/Module/updateModule"

		,"/simple/permission/Module/findModulesWithIdNameByName"
	]
	},
	{
	"code": "simple_permission_Module_Delete",
	"name1": "删除",
	"fullName": "simple.授权.模块.删除",
	"moduleCode": "simple/permission/Module",
	urls:[
	"/simple/permission/Module/deleteModule"
	]
	},
	{
	"code": "simple_permission_Module_View",
	"name1": "查看",
	"fullName": "simple.授权.模块.查看",
	"moduleCode": "simple/permission/Module",
	urls:[
	"/simple/permission/Module/findModules",
	"/simple/permission/Module/findModuleForView"
	]
	}
	]
}
}
*/

@RestController
@RequestMapping("/simple/permission/Module")
@CrossOrigin(allowCredentials = "true")
public class ModuleController {
	private static Logger logger = LogManager.getLogger();





	@Autowired private ModuleService moduleService;
	@Autowired private CodeTableService codeTableService;

	@PostMapping("/findModules")
	public PageResultDTO findModules(@RequestBody PageDTO pageDTO){
		return this.moduleService.findModules(pageDTO);
	}

	@GetMapping("/findAllModules")
	public List<Module> findAllModules()
	{
		return this.moduleService.findAllModules();
	}

	@GetMapping("/findModule")
	public Module findModule(@RequestParam Long moduleId)
	{
		return this.moduleService.findModule(moduleId);
	}

	@GetMapping("/findModuleForEdit")
	public ModuleEditDto findModuleForEdit(@RequestParam String moduleId)
	{
		ModuleEditDto moduleEditDto = new ModuleEditDto();
		moduleEditDto.setModule(this.moduleService.findModuleWithForeignName(moduleId));

		this.prepareModuleEditDto(moduleEditDto);

		return moduleEditDto;
	}

	//创建新的模块
	@GetMapping("/createModule")
	public ModuleEditDto createModule()
	{
		ModuleEditDto moduleEditDto = new ModuleEditDto();
		moduleEditDto.setModule(new Module());

		this.prepareModuleEditDto(moduleEditDto);
		return moduleEditDto;
	}

	private void prepareModuleEditDto(ModuleEditDto moduleEditDto)
	{
		moduleEditDto.setModuleTypeCodeTables(this.codeTableService.findCodeTablesByCodeType("模块类型"));
		moduleEditDto.setIsInUseCodeTables(this.codeTableService.findCodeTablesByCodeType("逻辑"));
        //TODO: 以下代码可以注释掉，此行代码即时加载所有外键对象，以便选择。如果不在此加载，可以在界面上实现延迟加载。如果外键对象超过 500 行，建议采用延迟加载
		moduleEditDto.setParentModules(this.moduleService.findAllModulesWithIdName());
	}

	@PostMapping("/saveModule")
	public void saveModule(@RequestBody Module module) throws Exception {
	this.moduleService.saveModule(module);
	}

	@PostMapping("/updateModule")
	public Module updateModule(@RequestBody Module module)
	{
		return this.moduleService.updateModule(module);
	}

	@GetMapping("/deleteModule")
	public void deleteModule(@RequestParam String moduleId)
	{
		this.moduleService.deleteModule(moduleId);
	}
	@GetMapping("/findModulesWithIdNameById")
	public Module findModulesWithIdNameById(@RequestParam Long moduleId)
	{
		return null;//this.moduleService.findModulesWithIdNameById(moduleId);
	}

	@GetMapping("/findModulesWithIdNameByName")
	public List<Module> findModulesWithIdNameByName(String moduleName)
	{
		return null;//this.moduleService.findModulesWithIdNameByName(moduleName);
	}
}

