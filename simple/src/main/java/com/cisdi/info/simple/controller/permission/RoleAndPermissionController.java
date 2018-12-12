

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.RoleAndPermissionDto;
import com.cisdi.info.simple.dto.permission.RoleAndPermissionEditDto;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;
import com.cisdi.info.simple.service.permission.RoleAndPermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * module
 * {
 * "simple/permission/RoleAndPermission": {
 * "code": "simple/permission/RoleAndPermission",
 * "name1": "角色权限点",
 * "url": "/simple/permission/RoleAndPermission",
 * "route": "/simple/permission/RoleAndPermission",
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
 * "code": "simple_permission_RoleAndPermission_Add",
 * "name1": "新增",
 * "fullName": "simple.授权.角色权限点.新增",
 * "moduleCode": "simple/permission/RoleAndPermission",
 * urls:[
 * "/simple/permission/RoleAndPermission/createRoleAndPermission",
 * "/simple/permission/RoleAndPermission/saveRoleAndPermission"
 * ]
 * },
 * {
 * "code": "simple_permission_RoleAndPermission_Edit",
 * "name1": "编辑",
 * "fullName": "simple.授权.角色权限点.编辑",
 * "moduleCode": "simple/permission/RoleAndPermission",
 * urls:[
 * "/simple/permission/RoleAndPermission/findRoleAndPermissionForEdit",
 * "/simple/permission/RoleAndPermission/updateRoleAndPermission"
 * ]
 * },
 * {
 * "code": "simple_permission_RoleAndPermission_Delete",
 * "name1": "删除",
 * "fullName": "simple.授权.角色权限点.删除",
 * "moduleCode": "simple/permission/RoleAndPermission",
 * urls:[
 * "/simple/permission/RoleAndPermission/deleteRoleAndPermission"
 * ]
 * },
 * {
 * "code": "simple_permission_RoleAndPermission_View",
 * "name1": "查看",
 * "fullName": "simple.授权.角色权限点.查看",
 * "moduleCode": "simple/permission/RoleAndPermission",
 * urls:[
 * "/simple/permission/RoleAndPermission/findRoleAndPermissions",
 * "/simple/permission/RoleAndPermission/findRoleAndPermissionForView"
 * ]
 * }
 * ]
 * }
 * }
 */

@RestController
@RequestMapping("/simple/permission/RoleAndPermission")
@CrossOrigin(allowCredentials = "true")
public class RoleAndPermissionController {
    private static Logger logger = LogManager.getLogger();


    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    @PostMapping("/findRoleAndPermissions")
    public PageResultDTO findRoleAndPermissions(@RequestBody PageDTO pageDTO) {
        return this.roleAndPermissionService.findRoleAndPermissions(pageDTO);
    }

    @GetMapping("/findRoleAndPermissionByRoleId")
    public List<RoleAndPermission> findRoleAndPermissionByRoleId(@RequestParam Long roleId) {
        return this.roleAndPermissionService.findRoleAndPermissionByRoleId(roleId);
    }

    @GetMapping("/findRoleAndPermission")
    public RoleAndPermission findRoleAndPermission(@RequestParam Long roleAndPermissionId) {
        return this.roleAndPermissionService.findRoleAndPermission(roleAndPermissionId);
    }

    @GetMapping("/findRoleAndPermissionForView")
    public RoleAndPermission findRoleAndPermissionForView(@RequestParam Long roleAndPermissionId) {
        return this.roleAndPermissionService.findRoleAndPermissionWithForeignName(roleAndPermissionId);
    }

    @GetMapping("/findRoleAndPermissionForEdit")
    public RoleAndPermissionEditDto findRoleAndPermissionForEdit(@RequestParam Long roleAndPermissionId) {
        RoleAndPermissionEditDto roleAndPermissionEditDto = new RoleAndPermissionEditDto();
        roleAndPermissionEditDto.setRoleAndPermission(this.roleAndPermissionService.findRoleAndPermissionWithForeignName(roleAndPermissionId));

        this.prepareRoleAndPermissionEditDto(roleAndPermissionEditDto);

        return roleAndPermissionEditDto;
    }

    //创建新的角色权限点
    @GetMapping("/createRoleAndPermission")
    public RoleAndPermissionEditDto createRoleAndPermission() {
        RoleAndPermissionEditDto roleAndPermissionEditDto = new RoleAndPermissionEditDto();
        roleAndPermissionEditDto.setRoleAndPermission(new RoleAndPermission());

        this.prepareRoleAndPermissionEditDto(roleAndPermissionEditDto);
        return roleAndPermissionEditDto;
    }

    private void prepareRoleAndPermissionEditDto(RoleAndPermissionEditDto roleAndPermissionEditDto) {
    }

    @PostMapping("/saveRoleAndPermission")
    public RoleAndPermission saveRoleAndPermission(@RequestBody RoleAndPermission roleAndPermission) {
        return this.roleAndPermissionService.saveRoleAndPermission(roleAndPermission);
    }

    @PostMapping("/updateRoleAndPermission")
    public void updateRoleAndPermission(@RequestBody RoleAndPermissionDto roleAndPermissionDto) {
        this.roleAndPermissionService.updateRoleAndPermission(roleAndPermissionDto);
    }

    @GetMapping("/deleteRoleAndPermission")
    public void deleteRoleAndPermission(@RequestParam Long roleAndPermissionId) {
        this.roleAndPermissionService.deleteRoleAndPermission(roleAndPermissionId);
    }

    @GetMapping("/findRoleAndPermissionsWithIdNameById")
    public RoleAndPermission findRoleAndPermissionsWithIdNameById(@RequestParam Long roleAndPermissionId) {
        return null;//this.roleAndPermissionService.findRoleAndPermissionsWithIdNameById(roleAndPermissionId);
    }

    @GetMapping("/findRoleAndPermissionsWithIdNameByName")
    public List<RoleAndPermission> findRoleAndPermissionsWithIdNameByName(String roleAndPermissionName) {
        return null;//this.roleAndPermissionService.findRoleAndPermissionsWithIdNameByName(roleAndPermissionName);
    }
}

