package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.dao.permission.RoleAndPermissionDao;
import com.cisdi.info.simple.dao.permission.RoleDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.entity.permission.RoleAndPermission;
import com.cisdi.info.simple.service.permission.PermissionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.cisdi.info.simple.entity.permission.Role;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceBean extends BaseService implements RoleService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleAndPermissionDao roleAndPermissionDao;

    public PageResultDTO findRoles(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize());
        List<Role> roleDTOS = this.roleDao.findRoles(pageDTO);
        Long totalCount = this.roleDao.findRoleTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(roleDTOS);

        return pageResultDTO;
    }

    public List<Role> findAllRoles() {
        return this.roleDao.findAllRoles();
    }

    public List<Role> findAllRolesWithIdName() {
        return this.roleDao.findAllRolesWithIdName();
    }

    public Role findRole(Long roleId) {
        return this.roleDao.findRole(roleId);
    }

    //所有外键的Name都以加载
    public Role findRoleWithForeignName(Long roleId) {
        return this.roleDao.findRoleWithForeignName(roleId);
    }

    public Long saveRole(Role role) {
        this.setSavePulicColumns(role);
        return this.roleDao.saveRole(role);
    }

    public Role updateRole(Role role) {
        this.setUpdatePulicColumns(role);
        return this.roleDao.updateRole(role);
    }

    public void deleteRole(Long roleId) {
        this.roleDao.deleteRole(roleId);
    }

    @Override
    public Role createSuperRole() {
        Role superRole = this.roleDao.findRoleByCode(SuperCode);
        if (superRole == null) {
            superRole = new Role();
            superRole.setName("超级角色");
            superRole.setCode(SuperCode);
            superRole.setRemark("超级角色拥有所有权限，为了保证安全，可以删除");
            this.roleDao.saveRole(superRole);
        }
        this.roleAndPermissionDao.deleteRoleAndPermissionsByRoleId(superRole.getEId());

        List<Permission> allPermissions = this.permissionService.getAllPermissions();

        for (Permission permission : allPermissions) {
            RoleAndPermission roleAndPermission = new RoleAndPermission(superRole.getEId(), permission.getCode());
            roleAndPermission.setRoleId(superRole.getEId());
            roleAndPermission.setModuleCode(permission.getModuleCode());
            this.roleAndPermissionDao.saveRoleAndPermission(roleAndPermission);
        }
        return superRole;
    }
}
