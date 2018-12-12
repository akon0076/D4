package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dao.permission.PermissionDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.PermissionListDto;
import com.cisdi.info.simple.entity.permission.Permission;

import java.util.List;

public interface PermissionService {

    public PageResultDTO findPermissions(PageDTO pageDTO);

    public List<Permission> findAllPermissions();

    public List<Permission> findAllPermissionsWithIdName();

    public Permission findPermission(Long permissionId);

    //所有外键的Name都以加载
    public Permission findPermissionWithForeignName(Long permissionId);

    public Permission savePermission(Permission permission);

    public Permission updatePermission(Permission permission);

    public void deletePermission(Long permissionId);

    public List<Permission> getAllPermissions();

    /**
     * 初始化数据到权限列表
     * @param permissions  从module.json中获取的权限
     */
    void savePermissionsToDataBase(List<Permission> permissions);

    /**
     * 更新权限数据到列表
     * @param permissions 从module.json中获取的权限
     */
    void updatePermissionsToDataBase(List<Permission> permissions);

    /**
     * 权限点总数
     * @return 获取权限点总数
     */
    Integer permissionCount();
}
