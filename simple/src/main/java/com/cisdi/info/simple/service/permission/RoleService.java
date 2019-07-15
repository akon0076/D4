package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Role;

import java.util.List;

public interface RoleService {

    public PageResultDTO findRoles(PageDTO pageDTO);

    public List<Role> findAllRoles();

    public List<Role> findAllRolesWithIdName();

    public Role findRole(Long roleId);

    //所有外键的Name都以加载
    public Role findRoleWithForeignName(Long roleId);

    public Long saveRole(Role role);

    public Role updateRole(Role role);

    public void deleteRole(Long roleId);

    public static String SuperCode = "000";

    /**
     * 创建创建超级角色，其用户编码为000，超级角色拥有所有权限
     */
    public Role createSuperRole();
}
