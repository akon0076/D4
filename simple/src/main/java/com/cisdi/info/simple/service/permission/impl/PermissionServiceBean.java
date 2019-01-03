package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.dao.permission.PermissionDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.PermissionService;
import com.cisdi.info.simple.util.ModuleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class PermissionServiceBean extends BaseService implements PermissionService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据分页参数查询权限点
     *
     * @param pageDTO 分页参数
     * @return
     */
    @Override
    public PageResultDTO findPermissions(PageDTO pageDTO) {
        // 读取json文件中的模块
        Collection<Module> moduleCollection = ModuleManager.getAllModules();
        Iterator iterator = moduleCollection.iterator();

        List<Module> moduleList = new ArrayList<>();
        while (iterator.hasNext()) {
            moduleList.add((Module) iterator.next());
        }

        List<Permission> permissions = new ArrayList<>();
        for (Module module : moduleList) {
            List<Permission> modulePermissions = module.getPermissions();
            for (int i = 0; i < modulePermissions.size(); i++) {
                Permission permission = new Permission();
                permission.setFullName(modulePermissions.get(i).getFullName());
                permission.setCode(modulePermissions.get(i).getCode());
                permission.setModuleCode(modulePermissions.get(i).getModuleCode());
                permission.setUrls(modulePermissions.get(i).getUrls());
                String name = module.getName() + module.getPermissions().get(i).getName();
                permission.setName(name);
                permissions.add(permission);
            }
        }
        int startIndex = (pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize();
        int pageSize = pageDTO.getPageSize();
        long totalCount = permissions.size();
        List<Permission> permissionList = permissions.subList(startIndex, startIndex + pageSize);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setDatas(permissionList);
        pageResultDTO.setTotalCount(totalCount);

        return pageResultDTO;
    }

    @Override
    public List<Permission> findAllPermissions() {
        return this.permissionDao.findAllPermissions();
    }

    @Override
    public List<Permission> findAllPermissionsWithIdName() {
        return this.permissionDao.findAllPermissionsWithIdName();
    }

    @Override
    public Permission findPermission(Long permissionId) {
        return this.permissionDao.findPermission(permissionId);
    }

    //所有外键的Name都以加载
    @Override
    public Permission findPermissionWithForeignName(Long permissionId) {
        return this.permissionDao.findPermissionWithForeignName(permissionId);
    }

    @Override
    public Permission savePermission(Permission permission) {

        this.setSavePulicColumns(permission);

        Permission permission1 = this.permissionDao.savePermission(permission);

        ModuleManager.addPermission(permission);

        return permission1;
    }

    @Override
    public Permission updatePermission(Permission permission) {

        this.setUpdatePulicColumns(permission);

        Permission permission1 = this.permissionDao.updatePermission(permission);

        ModuleManager.updatePermission(permission);

        return permission1;
    }

    @Override
    public void deletePermission(String permissionCode) {
        // 这里写删除权限点的
        if (ModuleManager.removePermission(permissionCode)) {
            logger.debug("删除权限点:" + permissionCode + "成功！");
        } else {
            logger.debug("删除权限点:" + permissionCode + "失败！");
        }
    }

    @Override
    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<Permission>();
        for (Module module : ModuleManager.getAllModules()) {
            permissions.addAll(module.getPermissions());
        }
        return permissions;
    }

    /**
     * 保存到数据库
     *
     * @param permissions 从module.json中获取的权限
     */
    @Override
    public void savePermissionsToDataBase(List<Permission> permissions) {
        Integer count = 0;
        for (Permission permission : permissions) {
            this.permissionDao.savePermission(permission);
            count++;
        }
        logger.info("权限条数:" + count);
    }

    /**
     * 更新module.json的文件
     *
     * @param permissions 从module.json中获取的权限
     */
    @Override
    public void updatePermissionsToDataBase(List<Permission> permissions) {
        Integer count = 0;
        for (Permission permission : permissions) {
            this.permissionDao.updatePermission(permission);
            count++;
        }
        logger.info("权限条数:" + count);
    }

    /**
     * 获取权限点总数
     *
     * @return 权限点数量
     */
    @Override
    public Integer permissionCount() {
        return this.permissionDao.permissionCount();
    }

}
