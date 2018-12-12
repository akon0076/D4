package com.cisdi.info.simple.util;

import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.permission.PermissionService;
import com.cisdi.info.simple.util.ModuleManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
public class StartLoad implements CommandLineRunner {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PermissionService permissionService;


    @Override
    public void run(String... args) throws Exception {

        // 初始化模块数据 ---> start

        // 读取json文件中的模块
        Collection<Module> moduleCollection = ModuleManager.getAllModules();
        //  获取数据库的模块
        Integer account =  this.moduleService.moduleAccount();
        // 更新数据
        initModules(account, moduleCollection);
        // 初始化模块数据 ---> end

        // 初始化权限数据  ---> start
        Integer count = this.permissionService.permissionCount();

        initPermissions(moduleCollection,count);
        // 初始化权限数据  ---> end
    }


    /**
     * 首次运行初始化数据方法
     * @param account 从数据库查询而来的数据
     * @param modules 从module.json中读取而来的
     */
    private void initModules(Integer account,Collection<Module> modules){

        // 初始化数据   --->   start
        List<Module> moduleList1 = new ArrayList<>();
        Iterator iterator = modules.iterator();
        while (iterator.hasNext()){
            moduleList1.add((Module) iterator.next());
        }
        // 初始化数据   --->   end
        if(account != 0){
            this.moduleService.changeModelsToDataBase(moduleList1);
            logger.debug("初始化成功！");
        }else {
            this.moduleService.saveModelsToDataBase(moduleList1);
            logger.debug("初始化成功！");
        }

    }

    /**
     * 初始化权限
     * @param moduleCollection   从文件读取出来的权限
     * @param count 从数据库读取的权限总数
     */
    private void initPermissions(Collection<Module> moduleCollection, Integer count){

        List<Permission> permissions = new ArrayList<>();

        List<Module> moduleList1 = new ArrayList<>();

        Iterator iterator = moduleCollection.iterator();

        while (iterator.hasNext()){
            moduleList1.add((Module) iterator.next());
        }

        for (Module module: moduleList1){

            List<Permission> modulePermissions = module.getPermissions();
            // 处理模块名称
            for (int i = 0; i < modulePermissions.size(); i++) {
                Permission permission = new Permission();

                permission.setFullName(modulePermissions.get(i).getFullName());

                permission.setCode(modulePermissions.get(i).getCode());

                permission.setModuleCode(modulePermissions.get(i).getModuleCode());

                permission.setUrls(modulePermissions.get(i).getUrls());

                String  name = module.getName() + "_" + module.getPermissions().get(i).getName();

                permission.setName(name);

                permissions.add(permission);
            }
        }

        if(count != 0){
            permissionService.updatePermissionsToDataBase(permissions);
            logger.debug("更新成功！");
        }else {
            permissionService.savePermissionsToDataBase(permissions);
            logger.debug("初始化成功！");
        }
    }

}
