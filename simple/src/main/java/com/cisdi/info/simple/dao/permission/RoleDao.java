package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.permission.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "roleDao")
public interface RoleDao {

public List<Role> findRoles(PageDTO pageDTO);

public List<Role> findAllRoles();

public List<Role> findAllRolesWithIdName();

public Long findRoleTotalCount(PageDTO pageDTO);

public Role findRole(Long roleId);

//所有外键的Name都以加载
public Role findRoleWithForeignName(Long roleId);

public Role saveRole(Role role);

public Role updateRole(Role role);

public void deleteRole(Long roleId);

public Role  findRoleByCode(String code);

public Role findRoleByName(String name);

}
