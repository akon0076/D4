

package com.cisdi.info.simple.controller.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.PermissionEditDto;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.permission.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * module
 * {
 * "simple/permission/Permission": {
 * "code": "simple/permission/Permission",
 * "name1": "权限点",
 * "url": "/simple/permission/Permission",
 * "route": "/simple/permission/Permission",
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
 * "code": "simple_permission_Permission_Add",
 * "name1": "新增",
 * "fullName": "simple.授权.权限点.新增",
 * "moduleCode": "simple/permission/Permission",
 * urls:[
 * "/simple/permission/Permission/createPermission",
 * "/simple/permission/Permission/savePermission"
 * ]
 * },
 * {
 * "code": "simple_permission_Permission_Edit",
 * "name1": "编辑",
 * "fullName": "simple.授权.权限点.编辑",
 * "moduleCode": "simple/permission/Permission",
 * urls:[
 * "/simple/permission/Permission/findPermissionForEdit",
 * "/simple/permission/Permission/updatePermission"
 * ]
 * },
 * {
 * "code": "simple_permission_Permission_Delete",
 * "name1": "删除",
 * "fullName": "simple.授权.权限点.删除",
 * "moduleCode": "simple/permission/Permission",
 * urls:[
 * "/simple/permission/Permission/deletePermission"
 * ]
 * },
 * {
 * "code": "simple_permission_Permission_View",
 * "name1": "查看",
 * "fullName": "simple.授权.权限点.查看",
 * "moduleCode": "simple/permission/Permission",
 * urls:[
 * "/simple/permission/Permission/findPermissions",
 * "/simple/permission/Permission/findPermissionForView"
 * ]
 * }
 * ]
 * }
 * }
 */

@RestController
@RequestMapping("/simple/permission/Permission")
@CrossOrigin(allowCredentials = "true")
public class PermissionController {
    private static Logger logger = LogManager.getLogger();


    @Autowired
    private PermissionService permissionService;


    /**
     * 查询所有的权限
     *
     * @return
     */
    @GetMapping("/findAllPermissions")
    public List<Permission> findAllPermissions() {
        return this.permissionService.findAllPermissions();
    }

    @PostMapping("/findPermissions")
    public PageResultDTO findPermissions(@RequestBody PageDTO pageDTO) {
        return this.permissionService.findPermissions(pageDTO);
    }

    @GetMapping("/findPermission")
    public Permission findPermission(@RequestParam String code) {
        return this.permissionService.findPermission(code);
    }

    @GetMapping("/findPermissionForView")
    public Permission findPermissionForView(@RequestParam String code) {
        return this.permissionService.findPermissionWithForeignName(code);
    }

    @GetMapping("/findPermissionForEdit")
    public PermissionEditDto findPermissionForEdit(@RequestParam String code) {
        PermissionEditDto permissionEditDto = new PermissionEditDto();
        permissionEditDto.setPermission(this.permissionService.findPermissionWithForeignName(code));
        this.preparePermissionEditDto(permissionEditDto);

        return permissionEditDto;
    }

    //创建新的权限点
    @GetMapping("/createPermission")
    public PermissionEditDto createPermission() {
        PermissionEditDto permissionEditDto = new PermissionEditDto();
        permissionEditDto.setPermission(new Permission());

        this.preparePermissionEditDto(permissionEditDto);
        return permissionEditDto;
    }

    private void preparePermissionEditDto(PermissionEditDto permissionEditDto) {
    }

    @PostMapping("/savePermission")
    public Permission savePermission(@RequestBody Permission permission) {
        return this.permissionService.savePermission(permission);
    }

    @PostMapping("/updatePermission")
    public Permission updatePermission(@RequestBody Permission permission) {
        return this.permissionService.updatePermission(permission);
    }

    @GetMapping("/deletePermission")
    public void deletePermission(@RequestParam String code) {
        this.permissionService.deletePermission(code);
    }

    @GetMapping("/findPermissionsWithIdNameById")
    public Permission findPermissionsWithIdNameById(@RequestParam Long permissionId) {
        return null;//this.permissionService.findPermissionsWithIdNameById(permissionId);
    }

    @GetMapping("/findPermissionsWithIdNameByName")
    public List<Permission> findPermissionsWithIdNameByName(String permissionName) {
        return null;//this.permissionService.findPermissionsWithIdNameByName(permissionName);
    }
}

