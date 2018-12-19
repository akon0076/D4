package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.OperatorAndRoleDto;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;

import java.util.List;

public interface OperatorAndRoleService {

    public PageResultDTO findOperatorAndRoles(PageDTO pageDTO);

    public List<OperatorAndRole> findAllOperatorAndRoles();

    public List<OperatorAndRole> findAllOperatorAndRolesWithIdName();

    public OperatorAndRole findOperatorAndRole(Long operatorAndRoleId);

    //所有外键的Name都以加载
    public OperatorAndRole findOperatorAndRoleWithForeignName(Long operatorAndRoleId);

    public OperatorAndRole saveOperatorAndRole(OperatorAndRole operatorAndRole);

    public void updateOperatorAndRole(OperatorAndRoleDto operatorAndRoleDto);

    public void deleteOperatorAndRole(Long operatorAndRoleId);

    public List<OperatorAndRole> findOperatorAndRoleByOperatorIdAndOrganizationId(Long operatorId, Long organizationId);
}
