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

    public PageResultDTO findOperatorAndRoles(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<OperatorAndRole> operatorAndRoleDTOS = this.operatorAndRoleDao.findOperatorAndRoles(pageDTO);
        Long totalCount = this.operatorAndRoleDao.findOperatorAndRoleTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(operatorAndRoleDTOS);

        return pageResultDTO;
    }

    public List<OperatorAndRole> findOperatorAndRoleByOperatorId(Long operatorId) {
        return this.operatorAndRoleDao.findOperatorAndRoleByOperatorId(operatorId);
    }

    public List<OperatorAndRole> findAllOperatorAndRoles() {
        return this.operatorAndRoleDao.findAllOperatorAndRoles();
    }

    public List<OperatorAndRole> findAllOperatorAndRolesWithIdName() {
        return this.operatorAndRoleDao.findAllOperatorAndRolesWithIdName();
    }

    public OperatorAndRole findOperatorAndRole(Long operatorAndRoleId) {
        return this.operatorAndRoleDao.findOperatorAndRole(operatorAndRoleId);
    }

    //所有外键的Name都以加载
    public OperatorAndRole findOperatorAndRoleWithForeignName(Long operatorAndRoleId) {
        return this.operatorAndRoleDao.findOperatorAndRoleWithForeignName(operatorAndRoleId);
    }

    public OperatorAndRole saveOperatorAndRole(OperatorAndRole operatorAndRole) {
        this.setSavePulicColumns(operatorAndRole);
        return this.operatorAndRoleDao.saveOperatorAndRole(operatorAndRole);
    }

    /**
     * 根据操作员ID更新对应的角色信息
     * @param operatorAndRoleDto
     */
    public void updateOperatorAndRole(OperatorAndRoleDto operatorAndRoleDto) {
        //删除原有的操作员角色对应关系
        this.operatorAndRoleDao.deleteOperatorAndRoleByOperatorId(operatorAndRoleDto.getOperatorId());
        OperatorAndRole operatorAndRole = new OperatorAndRole();
        Long[] rolesId = operatorAndRoleDto.getRoleId();
        this.setSavePulicColumns(operatorAndRole);
        operatorAndRole.setOperatorId(operatorAndRoleDto.getOperatorId());
        for (Long roleId : rolesId) {
            operatorAndRole.setRoleId(roleId);
            //加入新的操作员角色对应关系
            this.operatorAndRoleDao.saveOperatorAndRole(operatorAndRole);
        }
    }

    public void deleteOperatorAndRole(Long operatorAndRoleId) {
        this.operatorAndRoleDao.deleteOperatorAndRole(operatorAndRoleId);
    }
}
