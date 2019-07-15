package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.dao.permission.OperatorAndRoleDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.OperatorAndRoleDto;
import com.cisdi.info.simple.entity.permission.OperatorAndRole;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.OperatorAndRoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OperatorAndRoleServiceBean extends BaseService implements OperatorAndRoleService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private OperatorAndRoleDao operatorAndRoleDao;

    @Override
    public PageResultDTO findOperatorAndRoles(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<OperatorAndRole> operatorAndRoleDTOS = this.operatorAndRoleDao.findOperatorAndRoles(pageDTO);
        Long totalCount = this.operatorAndRoleDao.findOperatorAndRoleTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(operatorAndRoleDTOS);

        return pageResultDTO;
    }

    @Override
    public List<OperatorAndRole> findOperatorAndRoleByOperatorIdAndOrganizationId(Long operatorId, Long organizationId) {
        return this.operatorAndRoleDao.findOperatorAndRoleByOperatorIdAndOrganizationId(operatorId, organizationId);
    }

    @Override
    public List<OperatorAndRole> findAllOperatorAndRoles() {
        return this.operatorAndRoleDao.findAllOperatorAndRoles();
    }

    @Override
    public List<OperatorAndRole> findAllOperatorAndRolesWithIdName() {
        return this.operatorAndRoleDao.findAllOperatorAndRolesWithIdName();
    }

    @Override
    public OperatorAndRole findOperatorAndRole(Long operatorAndRoleId) {
        return this.operatorAndRoleDao.findOperatorAndRole(operatorAndRoleId);
    }

    //所有外键的Name都以加载
    @Override
    public OperatorAndRole findOperatorAndRoleWithForeignName(Long operatorAndRoleId) {
        return this.operatorAndRoleDao.findOperatorAndRoleWithForeignName(operatorAndRoleId);
    }

    @Override
    public OperatorAndRole saveOperatorAndRole(OperatorAndRole operatorAndRole) {
        this.setSavePulicColumns(operatorAndRole);
        return this.operatorAndRoleDao.saveOperatorAndRole(operatorAndRole);
    }

    /**
     * 根据操作员ID更新对应的角色信息
     *
     * @param operatorAndRoleDto
     */
    @Override
    public void updateOperatorAndRole(OperatorAndRoleDto operatorAndRoleDto) {
        Long operatorId = operatorAndRoleDto.getOperatorId();
        Long organizationId = operatorAndRoleDto.getOrganizationId();
        Long[] removeRoles = operatorAndRoleDto.getRemoveRoles();
        for (Long roleId : removeRoles) {
            //删除原有的操作员角色对应关系
            this.operatorAndRoleDao.deleteOperatorAndRoleByOperatorIdAndRoleId(operatorId, roleId, organizationId);
        }
        OperatorAndRole operatorAndRole = new OperatorAndRole();
        this.setSavePulicColumns(operatorAndRole);
        operatorAndRole.setOperatorId(operatorId);
        Long[] addRoles = operatorAndRoleDto.getAddRoles();
        for (Long roleId : addRoles) {
            operatorAndRole.setRoleId(roleId);
            operatorAndRole.setOrganizationId(organizationId);
            //加入新的操作员角色对应关系
            this.operatorAndRoleDao.saveOperatorAndRole(operatorAndRole);
        }
    }

    @Override
    public void deleteOperatorAndRole(Long operatorAndRoleId) {
        this.operatorAndRoleDao.deleteOperatorAndRole(operatorAndRoleId);
    }
}
