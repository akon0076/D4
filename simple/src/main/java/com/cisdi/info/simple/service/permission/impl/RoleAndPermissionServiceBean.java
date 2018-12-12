package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.dao.permission.RoleAndPermissionDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.permission.RoleAndPermissionDto;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.RoleAndPermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleAndPermissionServiceBean extends BaseService implements RoleAndPermissionService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private RoleAndPermissionDao roleAndPermissionDao;

    public PageResultDTO findRoleAndPermissions(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<RoleAndPermission> roleAndPermissionDTOS = this.roleAndPermissionDao.findRoleAndPermissions(pageDTO);
        Long totalCount = this.roleAndPermissionDao.findRoleAndPermissionTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(roleAndPermissionDTOS);

        return pageResultDTO;
    }

    public List<RoleAndPermission> findAllRoleAndPermissions() {
        return this.roleAndPermissionDao.findAllRoleAndPermissions();
    }

    public List<RoleAndPermission> findAllRoleAndPermissionsWithIdName() {
        return this.roleAndPermissionDao.findAllRoleAndPermissionsWithIdName();
    }

    public RoleAndPermission findRoleAndPermission(Long roleAndPermissionId) {
        return this.roleAndPermissionDao.findRoleAndPermission(roleAndPermissionId);
    }

    //所有外键的Name都以加载
    public RoleAndPermission findRoleAndPermissionWithForeignName(Long roleAndPermissionId) {
        return this.roleAndPermissionDao.findRoleAndPermissionWithForeignName(roleAndPermissionId);
    }

    public RoleAndPermission saveRoleAndPermission(RoleAndPermission roleAndPermission) {
        this.setSavePulicColumns(roleAndPermission);
        return this.roleAndPermissionDao.saveRoleAndPermission(roleAndPermission);
    }

    /**
     * 更新角色对应的权限点
     * @param roleAndPermissionDto
     * @return
     */
    public void updateRoleAndPermission(RoleAndPermissionDto roleAndPermissionDto) {
        //删除原有的角色权限点对应关系
        this.roleAndPermissionDao.deleteRoleAndPermissionsByRoleId(roleAndPermissionDto.getRoleId());
        RoleAndPermission roleAndPermission = new RoleAndPermission();
        String[] permissionsCode = roleAndPermissionDto.getPermissionsCode();
        this.setSavePulicColumns(roleAndPermission);
        roleAndPermission.setRoleId(roleAndPermissionDto.getRoleId());
        for (String  permissionCode : permissionsCode) {
            roleAndPermission.setPermissionCode(permissionCode);
            //加入新的角色权限点对应关系
            this.roleAndPermissionDao.saveRoleAndPermission(roleAndPermission);
        }
    }

    public void deleteRoleAndPermission(Long roleAndPermissionId) {
        this.roleAndPermissionDao.deleteRoleAndPermission(roleAndPermissionId);
    }

    @Override
    public List<RoleAndPermission> findRoleAndPermissionByRoleId(Long roleId) {
        return this.roleAndPermissionDao.findRoleAndPermissionByRoleId(roleId);
    }
}
