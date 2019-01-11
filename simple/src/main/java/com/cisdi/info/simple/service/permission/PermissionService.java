package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.PermissionEditDto;
import com.cisdi.info.simple.entity.permission.Permission;

import java.util.List;

public interface PermissionService {

    public PageResultDTO findPermissions(PageDTO pageDTO);

    public List<Permission> findAllPermissions();

    public List<Permission> findAllPermissionsWithIdName();

    public Permission findPermission(String code);

    public Permission findPermissionWithForeignName(String code);

    public Permission savePermission(Permission permission);

    public Permission updatePermission(PermissionEditDto permissionEditDto);

    public void deletePermission(String permissionCode);

    public List<Permission> getAllPermissions();

    /**
     * 权限点总数
     * @return 获取权限点总数
     */
    Integer permissionCount();
}
