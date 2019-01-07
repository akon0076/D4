package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "operatorAndRoleDao")
public interface OperatorAndRoleDao {

    public List<OperatorAndRole> findOperatorAndRoles(PageDTO pageDTO);

    public List<OperatorAndRole> findAllOperatorAndRoles();

    public List<OperatorAndRole> findAllOperatorAndRolesWithIdName();

    public Long findOperatorAndRoleTotalCount(PageDTO pageDTO);

    public OperatorAndRole findOperatorAndRole(Long operatorAndRoleId);

    //所有外键的Name都以加载
    public OperatorAndRole findOperatorAndRoleWithForeignName(Long operatorAndRoleId);

    public OperatorAndRole saveOperatorAndRole(OperatorAndRole operatorAndRole);

    public OperatorAndRole updateOperatorAndRole(OperatorAndRole operatorAndRole);

    public void deleteOperatorAndRole(Long operatorAndRoleId);

    public OperatorAndRole findOperatorAndRoleByOperatorAndRole(@Param("operatorCode") String operatorCode, @Param("roleCode") String roleCode);

    public List<Long> findRoleIdsByOperator(@Param("operatorId") Long operatorId);

    public int deleteOperatorAndRoleByOperatorId(Long operatorId);

    public int deleteOperatorAndRoleByOperatorIdAndRoleId(@Param("operatorId") Long operatorId, @Param("roleId") Long roleId);

    public List<OperatorAndRole> findOperatorAndRoleByOperatorId(Long operatorId);

    public List<OperatorAndRole> findOperatorAndRoleByOperatorIdAndOrganizationId(@Param("operatorId") Long operatorId, @Param("organizationId") Long organizationId);

}
