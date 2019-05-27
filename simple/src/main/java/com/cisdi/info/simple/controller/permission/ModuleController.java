

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.ModuleEditDto;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.system.CodeTableService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * module
 * {
 * "simple/permission/Module": {
 * "code": "simple/permission/Module",
 * "name1": "模块",
 * "url": "/simple/permission/Module",
 * "route": "/simple/permission/Module",
 * "iconClass": "",
 * "displayIndex": 1,
 * "parentCode": "simple/permission",
 * "parentName": "授权",
 * "moduleType": "电脑模块",
 * "isInUse": "是",
 * "routeParamsObj": "",
 * "permissions":
 * [
 * {
 * "code": "simple_permission_Module_Add",
 * "name1": "新增",
 * "fullName": "simple.授权.模块.新增",
 * "moduleCode": "simple/permission/Module",
 * urls:[
 * "/simple/permission/Module/createModule",
 * "/simple/permission/Module/saveModule"
 * <p>
 * ,"/simple/permission/Module/findModulesWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_permission_Module_Edit",
 * "name1": "编辑",
 * "fullName": "simple.授权.模块.编辑",
 * "moduleCode": "simple/permission/Module",
 * urls:[
 * "/simple/permission/Module/findModuleForEdit",
 * "/simple/permission/Module/updateModule"
 * <p>
 * ,"/simple/permission/Module/findModulesWithIdNameByName"
 * ]
 * },
 * {
 * "code": "simple_permission_Module_Delete",
 * "name1": "删除",
 * "fullName": "simple.授权.模块.删除",
 * "moduleCode": "simple/permission/Module",
 * urls:[
 * "/simple/permission/Module/deleteModule"
 * ]
 * },
 * {
 * "code": "simple_permission_Module_View",
 * "name1": "查看",
 * "fullName": "simple.授权.模块.查看",
 * "moduleCode": "simple/permission/Module",
 * urls:[
 * "/simple/permission/Module/findModules",
 * "/simple/permission/Module/findModuleForView"
 * ]
 * }
 * ]
 * }
 * }
 */

@RestController
@RequestMapping("/simple/permission/Module")
@CrossOrigin(allowCredentials = "true")
public class ModuleController {
    private static Logger logger = LogManager.getLogger();


    @Autowired
    private ModuleService moduleService;
    @Autowired
    private CodeTableService codeTableService;

    @PostMapping("/findModuleTree")
    public PageResultDTO findModuleTree(@RequestBody PageDTO pageDTO) {
        PageResultDTO pageResultDTO = new PageResultDTO();
        List<Module> moduleTree = this.moduleService.findModuleTree(pageDTO);
        pageResultDTO.setDatas(moduleTree);
        pageResultDTO.setTotalCount((long) moduleTree.size());
        return pageResultDTO;
    }

    @GetMapping("/findModuleForView")
    public Module findModules(@RequestParam String moduleId) {
        return this.moduleService.findModuleForDisplay(moduleId);
    }

    @GetMapping("/findAllModules")
    public List<Module> findAllModules() {
        return this.moduleService.findAllModules();
    }

    @GetMapping("/findAllParentModules")
    public List<Module> findAllParentModules() {
        return this.moduleService.findAllParentModules();
    }

    @GetMapping("/findAllLeafModules")
    public List<Module> findAllLeafModules() {
        return this.moduleService.findAllLeafModules();
    }

    @GetMapping("/findAllTreeNode")
    public ModuleTreeNode findAllTreeNode(@RequestParam String modelType) {
        return this.moduleService.findAllTreeNode(modelType);
    }

    @GetMapping("/findModule")
    public Module findModule(@RequestParam Long moduleId) {
        return this.moduleService.findModule(moduleId);
    }

    @GetMapping("/findModuleForEdit")
    public ModuleEditDto findModuleForEdit(@RequestParam String moduleId) {
        ModuleEditDto moduleEditDto = new ModuleEditDto();
        moduleEditDto.setModule(this.moduleService.findModuleWithForeignName(moduleId));

        this.prepareModuleEditDto(moduleEditDto);

        return moduleEditDto;
    }

    //创建新的模块
    @GetMapping("/createModule")
    public ModuleEditDto createModule() {
        ModuleEditDto moduleEditDto = new ModuleEditDto();
        moduleEditDto.setModule(new Module());

        this.prepareModuleEditDto(moduleEditDto);
        return moduleEditDto;
    }

    private void prepareModuleEditDto(ModuleEditDto moduleEditDto) {
        moduleEditDto.setModuleTypeCodeTables(this.codeTableService.findCodeTablesByCodeType("ModuleType"));
        moduleEditDto.setIsInUseCodeTables(this.codeTableService.findCodeTablesByCodeType("UseStatus"));
        moduleEditDto.setParentModules(this.moduleService.findAllParentModules());
    }

    @PostMapping("/saveModule")
    public void saveModule(@RequestBody Module module) throws Exception {
        this.moduleService.saveModule(module);
    }

    @PostMapping("/updateModule")
    public Module updateModule(@RequestBody Module module) {
        return this.moduleService.updateModule(module);
    }

    @GetMapping("/deleteModule")
    public boolean deleteModule(@RequestParam String moduleId) {
        return moduleService.deleteModule(moduleId);
    }

}

