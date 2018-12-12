package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.permission.PermissionListDto;
import com.cisdi.info.simple.entity.permission.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component(value = "permissionDao")
public interface PermissionDao {

    public List<Permission> findPermissions(PageDTO pageDTO);

    public List<Permission> findAllPermissions();

    public List<Permission> findAllPermissionsWithIdName();

    public Long findPermissionTotalCount(PageDTO pageDTO);

    public Permission findPermission(Long permissionId);

    //所有外键的Name都以加载
    public Permission findPermissionWithForeignName(Long permissionId);

    public Permission savePermission(Permission permission);

    public Permission updatePermission(Permission permission);

    public void deletePermission(Long permissionId);

    /**
     * 获取权限点总数
     * @return 权限点数量
     */
    Integer permissionCount();

}
