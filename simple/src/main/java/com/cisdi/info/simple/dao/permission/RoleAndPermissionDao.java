package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "roleAndPermissionDao")
public interface RoleAndPermissionDao {

    public List<RoleAndPermission> findRoleAndPermissions(PageDTO pageDTO);

    public List<RoleAndPermission> findAllRoleAndPermissions();

    public List<RoleAndPermission> findAllRoleAndPermissionsWithIdName();

    public Long findRoleAndPermissionTotalCount(PageDTO pageDTO);

    public RoleAndPermission findRoleAndPermission(Long roleAndPermissionId);

    //所有外键的Name都以加载
    public RoleAndPermission findRoleAndPermissionWithForeignName(Long roleAndPermissionId);

    public RoleAndPermission saveRoleAndPermission(RoleAndPermission roleAndPermission);

    public RoleAndPermission updateRoleAndPermission(RoleAndPermission roleAndPermission);

    public void deleteRoleAndPermission(Long roleAndPermissionId);

    public int deleteRoleAndPermissionsByRoleId(Long roleId);

    public List<RoleAndPermission> findRoleAndPermissionByRoleId(Long roleId);

}
