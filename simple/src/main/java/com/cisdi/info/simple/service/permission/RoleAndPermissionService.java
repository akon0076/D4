package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.RoleAndPermissionDto;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;

import java.util.List;

public interface RoleAndPermissionService {

    public PageResultDTO findRoleAndPermissions(PageDTO pageDTO);

    public List<RoleAndPermission> findAllRoleAndPermissions();

    public List<RoleAndPermission> findAllRoleAndPermissionsWithIdName();

    public RoleAndPermission findRoleAndPermission(Long roleAndPermissionId);

    //所有外键的Name都以加载
    public RoleAndPermission findRoleAndPermissionWithForeignName(Long roleAndPermissionId);

    public RoleAndPermission saveRoleAndPermission(RoleAndPermission roleAndPermission);

    public void updateRoleAndPermission(RoleAndPermissionDto roleAndPermissionDto);

    public void deleteRoleAndPermission(Long roleAndPermissionId);

    public List<RoleAndPermission> findRoleAndPermissionByRoleId(Long roleId);
}
