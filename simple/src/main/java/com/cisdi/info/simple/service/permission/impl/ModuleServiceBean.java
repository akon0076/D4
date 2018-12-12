package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.DDDException;
import com.cisdi.info.simple.dao.permission.ModuleDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.dto.permission.ModuleListDto;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;
import com.cisdi.info.simple.entity.permission.Permission;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.permission.PermissionService;
import com.cisdi.info.simple.util.ModuleManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ModuleServiceBean extends BaseService implements ModuleService {

    private static Logger logger = LogManager.getLogger();

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private PermissionService permissionService;


    @Override
    public PageResultDTO findModules(PageDTO pageDTO) {
        pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
        List<Module> roleDTOS = this.moduleDao.findModules(pageDTO);
        Long totalCount = this.moduleDao.findModuleTotalCount(pageDTO);

        PageResultDTO pageResultDTO = new PageResultDTO();
        pageResultDTO.setTotalCount(totalCount);
        pageResultDTO.setDatas(roleDTOS);
        return pageResultDTO;
    }

    @Override
    public List<Module> findAllModules() {
        return this.moduleDao.findAllModules();
    }

    @Override
    public List<Module> findAllModulesWithIdName() {
        return this.moduleDao.findAllModulesWithIdName();
    }

    @Override
    public Module findModule(Long moduleId) {
        return this.moduleDao.findModule(moduleId);
    }


    //所有外键的Name都以加载
    @Override
    public Module findModuleWithForeignName(Long moduleId) {
        return this.moduleDao.findModuleWithForeignName(moduleId);
    }

    /**
     * 保存模块
     *
     * @param module
     * @return
     */
    @Override
    public Module saveModule(Module module) {

        this.setSavePulicColumns(module);

        ModuleManager.addModule(module);

        Module module1 = this.moduleDao.saveModule(module);

        return module1;
    }

    /**
     * 更新模块
     *
     * @param module
     * @return
     */
    @Override
    public Module updateModule(Module module) {

        // 更新模块对应的权限的名称   ----> start

        // 手动填充一些数据进入（因为直接更新会导致一些数据丢失）
        if (module.getName().split("_").length > 1) {
            module.setName(module.getName());
        } else {
            module.setName(module.getName().split("_")[1]);
        }

        Module findModule = ModuleManager.findModuleByCode(module.getCode());

        List<Permission> moduleList = findModule.getPermissions();

        for (Permission permission : moduleList) {
            String[] name = permission.getName().split("_");
            String[] fullName = permission.getFullName().split("\\.");
            permission.setName(name[0] + "_" + module.getName() + "_" + name[name.length - 1]);
            permission.setFullName(fullName[0] + "." + fullName[1] + "." + module.getName() + "." + fullName[3]);
        }

        // 更新模块对应的权限的名称   ----> end
        this.setUpdatePulicColumns(module);
        this.permissionService.updatePermissionsToDataBase(moduleList);
        Module module1 = this.moduleDao.updateModule(module);

        // 修改文件中的数据
        if (module.getName().split("_").length > 1) {
            module.setName(module.getName().split("_")[1]);
        } else {
            module.setName(module.getName().split("_")[0]);
        }
        ModuleManager.updateModule(module);
        return module1;
    }

    /**
     * 删除模块
     *
     * @param moduleId
     */
    @Override
    public void deleteModule(Long moduleId) {
        Module module = findModule(moduleId);
        if (module != null) {
            this.moduleDao.deleteModule(moduleId);
            String code = module.getCode();
            if (code != null) {
                if (ModuleManager.removeModule(code)) {
                    logger.debug("删除模块:" + code + "成功！");
                } else {
                    logger.debug("删除模块:" + code + "失败！");
                }
            } else {

                logger.error("该模块ID:" + moduleId + "找不到对应模块编码！");

                throw new DDDException("该模块ID:" + moduleId + "找不到对应模块编码！");
            }
        } else {
            logger.error("找不到模块ID为" + moduleId + "的模块！");

            throw new DDDException("找不到模块ID为" + moduleId + "的模块！");
        }

    }

    @Override
    public ModuleTreeNode constructNewTree(Long operatorId, String modelType) {
//        PageDTO pageDTO = new PageDTO();
//        pageDTO.setColumnName(String.valueOf(operatorId));
//        pageDTO.setContent(modelType);
        List<String> permissionCodes = this.operatorDao.findPermissions(operatorId);

        List<ModuleTreeNode> subSystemModules = new ArrayList<ModuleTreeNode>();
        Map<String, ModuleTreeNode> moduleTreeNodes = new HashMap<String, ModuleTreeNode>();
        if ("电脑模块".equals(modelType)) {
            for (String permissionCode : permissionCodes) {
//                if(permissionCode.equals("pm_spaceRenting_basicManagement_BuildingInformation_Add")){
//                   System.out.println();
//                   System.out.println("DDDD");
//                }
                String[] permissionCodeParts = StringUtils.split(permissionCode, "_");
                if (permissionCodeParts != null && permissionCodeParts.length < 3) {
                    continue;
                }
                String subSystemName = permissionCodeParts[0];
                if ("mobile".equals(subSystemName)) {
                    continue;
                }
                String moduleName = permissionCodeParts[0] + "/" + permissionCodeParts[1];
                String entityName = permissionCodeParts[0] + "/" + permissionCodeParts[1] + "/" + permissionCodeParts[2];
                if(permissionCodeParts.length>=5){
                    entityName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2]+"/"+permissionCodeParts[3];
                }
                if (moduleTreeNodes.containsKey(entityName)) continue;

                ModuleTreeNode subSystemModuleTreeNode = moduleTreeNodes.get(subSystemName);
                if (subSystemModuleTreeNode == null) {
                    Module subSystemModule = findMoudle(subSystemName);
                    // x_x 命名之后存在的数据相同 -start
                    String name = subSystemModule.getName();
                    if (name.split("_").length > 1) {
                        subSystemModule.setName(name.split("_")[1]);
                    }
                    // x_x 命名之后存在的数据相同 -end

                    if ("否".equals(subSystemModule.getIsInUse())) continue;
                    subSystemModuleTreeNode = this.convertModule2TreeNode(subSystemModule, 1);
                    subSystemModules.add(subSystemModuleTreeNode);
                    moduleTreeNodes.put(subSystemName, subSystemModuleTreeNode);
                }

                ModuleTreeNode moduleModuleTreeNode = moduleTreeNodes.get(moduleName);
                ModuleTreeNode moduleModuleTreeNode1 = moduleTreeNodes.get(moduleName);
                if (moduleModuleTreeNode == null) {
                    Module moduleModule = findMoudle(moduleName);
                    // x_x 命名之后存在的数据相同 -start
                    String name = moduleModule.getName();
                    if (name.split("_").length > 1) {
                        moduleModule.setName(name.split("_")[1]);
                    }
                    // x_x 命名之后存在的数据相同 -end
                    if ("否".equals(moduleModule.getIsInUse())) continue;
                    moduleModuleTreeNode = this.convertModule2TreeNode(moduleModule, 2);
                    subSystemModuleTreeNode.addNode(moduleModuleTreeNode);
                    moduleTreeNodes.put(moduleName, moduleModuleTreeNode);
                }
                if(permissionCodeParts.length>=5){
                    moduleName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2];
                    entityName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2]+"/"+permissionCodeParts[3];
                    moduleModuleTreeNode1 = moduleTreeNodes.get(moduleName);
                    if (moduleModuleTreeNode1 == null) {
                        Module moduleModule = findMoudle(moduleName);
                        if ("否".equals(moduleModule.getIsInUse())) continue;
                        moduleModuleTreeNode1 = this.convertModule2TreeNode(moduleModule, 3);
                        moduleModuleTreeNode.addNode(moduleModuleTreeNode1);
                        moduleTreeNodes.put(moduleName, moduleModuleTreeNode1);
                    }
                }
                Module entityModule = findMoudle(entityName);
                // x_x 命名之后存在的数据相同 -start
                String name = entityModule.getName();
                if (name.split("_").length > 1) {
                    entityModule.setName(name.split("_")[1]);

                }
                // x_x 命名之后存在的数据相同 -end

                if ("否".equals(entityModule.getIsInUse())) continue;
                ModuleTreeNode entityModuleTreeNode = this.convertModule2TreeNode(entityModule, 3);
                if(permissionCodeParts.length>=5){
                    entityModuleTreeNode = this.convertModule2TreeNode(entityModule, 4);
                    moduleModuleTreeNode1.addNode(entityModuleTreeNode);
                    moduleTreeNodes.put(entityName, entityModuleTreeNode);
                }else {
                    moduleModuleTreeNode.addNode(entityModuleTreeNode);
                    moduleTreeNodes.put(entityName, entityModuleTreeNode);
                }
            }


            ModuleTreeNode rootModuleTreeNode = new ModuleTreeNode();
            rootModuleTreeNode.setId("root");
            rootModuleTreeNode.setName("root");
            rootModuleTreeNode.setText("根节点");
            rootModuleTreeNode.getNodes().addAll(subSystemModules);
            sort(rootModuleTreeNode);
            return rootModuleTreeNode;
        } else if ("移动模块".equals(modelType)) {
            for (String permissionCode : permissionCodes) {
                String[] permissionCodeParts = StringUtils.split(permissionCode, "_");
                String subSystemName = permissionCodeParts[0];
                if (!"mobile".equals(subSystemName)) {
                    continue;
                }
                String moduleName = permissionCodeParts[0] + "/" + permissionCodeParts[1];

                ModuleTreeNode subSystemModuleTreeNode = moduleTreeNodes.get(subSystemName);
                if (subSystemModuleTreeNode == null) {
                    Module subSystemModule = findMoudle(subSystemName);
                    // x_x 命名之后存在的数据相同 -start
                    String name = subSystemModule.getName();
                    if (name.split("_").length > 1) {
                        subSystemModule.setName(name.split("_")[1]);
                    }
                    // x_x 命名之后存在的数据相同 -end

                    if ("否".equals(subSystemModule.getIsInUse())) continue;
                    subSystemModuleTreeNode = this.convertModule2TreeNode(subSystemModule, 1);
                    subSystemModules.add(subSystemModuleTreeNode);
                    moduleTreeNodes.put(subSystemName, subSystemModuleTreeNode);
                }

                ModuleTreeNode moduleModuleTreeNode = moduleTreeNodes.get(moduleName);
                if (moduleModuleTreeNode == null) {
                    Module moduleModule = findMoudle(moduleName);
                    // x_x 命名之后存在的数据相同 -start
                    String name = moduleModule.getName();
                    if (name.split("_").length > 1) {
                        moduleModule.setName(name.split("_")[1]);
                    }
                    // x_x 命名之后存在的数据相同 -end
                    if ("否".equals(moduleModule.getIsInUse())) continue;
                    moduleModuleTreeNode = this.convertModule2TreeNode(moduleModule, 2);
                    subSystemModuleTreeNode.addNode(moduleModuleTreeNode);
                    moduleTreeNodes.put(moduleName, moduleModuleTreeNode);
                }
            }


            ModuleTreeNode rootModuleTreeNode = new ModuleTreeNode();
            rootModuleTreeNode.setId("root");
            rootModuleTreeNode.setName("root");
            rootModuleTreeNode.setText("根节点");
            rootModuleTreeNode.getNodes().addAll(subSystemModules);
            sort(rootModuleTreeNode);
            return rootModuleTreeNode;
        }
        return null;
    }

    /**
     * 初始化模块进入数据库
     *
     * @param modules
     */
    @Override
    public void saveModelsToDataBase(List<Module> modules) {
        Integer count = 0;
        for (Module module1 : modules) {
            Module newModule = new Module();

            newModule.setParentName(module1.getParentName());
            newModule.setParent(module1.getParent());
            newModule.setPermissions(module1.getPermissions());
            newModule.setUrl(module1.getUrl());
            newModule.setRouteParamsObj(module1.getRouteParamsObj());
            newModule.setParentCode(module1.getParentCode());
            newModule.setParentId(module1.getParentId());
            newModule.setModuleType(module1.getModuleType());
            newModule.setIconClass(module1.getIconClass());
            newModule.setDisplayIndex(module1.getDisplayIndex());
            newModule.setRoute(module1.getRoute());
            newModule.setCode(module1.getCode());
            newModule.setChildren(module1.getChildren());
            newModule.setIsInUse(module1.getIsInUse());

            if(module1.getParentCode() != null) {
                if (!module1.getParentName().equals("")) {
                    String name = module1.getParentName() + "_" + module1.getName();
                    newModule.setName(name);
                } else {
                    newModule.setName(module1.getName());
                }
                this.moduleDao.saveModule(newModule);
            }else {
                logger.error("模块：" + module1.getCode() + "，生成出错，父级模块默认不为null，应该是''，请检查代码生成是否正确");
                throw new DDDException("模块：" + module1.getCode()+ "，生成出错，父级模块默认不为null，应该是''，请检查代码生成是否正确");
            }
            count++;
        }
        logger.debug("模块条数:" + count);
    }

    /**
     * 第二次启动针对模块改变刷新数据库
     *
     * @param modules
     */
    @Override
    public void changeModelsToDataBase(List<Module> modules) {

        Integer count = 0;
        for (Module module1 : modules) {
            Module newModule = new Module();

            newModule.setParentName(module1.getParentName());
            newModule.setParent(module1.getParent());
            newModule.setPermissions(module1.getPermissions());
            newModule.setUrl(module1.getUrl());
            newModule.setRouteParamsObj(module1.getRouteParamsObj());
            newModule.setParentCode(module1.getParentCode());
            newModule.setParentId(module1.getParentId());
            newModule.setModuleType(module1.getModuleType());
            newModule.setIconClass(module1.getIconClass());
            newModule.setDisplayIndex(module1.getDisplayIndex());
            newModule.setRoute(module1.getRoute());
            newModule.setCode(module1.getCode());
            newModule.setChildren(module1.getChildren());
            newModule.setIsInUse(module1.getIsInUse());

            if( module1.getParentName() != null) {
                if (!module1.getParentName().equals("")) {
                    String name = module1.getParentName() + "_" + module1.getName();
                    newModule.setName(name);
                } else {
                    newModule.setName(module1.getName());
                }
                this.moduleDao.updateModule(newModule);
            }else {
                logger.error("模块：" + module1.getCode() + "，生成出错，parentCode默认不为null，应该是''，请检查代码生成是否正确");
                throw new DDDException("模块：" + module1.getCode()+ "，生成出错，parentCode默认不为null，应该是''，请检查代码生成是否正确");
            }
            count++;
        }
        logger.debug("模块条数:" + count);
    }

    /**
     * 获取数据库中模块的总条数
     *
     * @return 总条数
     */
    @Override
    public Integer moduleAccount() {
        return this.moduleDao.moduleAccount();
    }

    public ModuleTreeNode wisdomCateringConstructNewTree(Long operatorId, String modelType) {
        List<String> permissionCodes = this.operatorDao.findPermissions(operatorId);

        List<ModuleTreeNode> subSystemModules = new ArrayList<ModuleTreeNode>();

        Map<String, ModuleTreeNode> moduleTreeNodes = new HashMap<String, ModuleTreeNode>();
        for (String permissionCode : permissionCodes) {
            String[] permissionCodeParts = StringUtils.split(permissionCode, "_");
                String subSystemName = permissionCodeParts[0];
                String moduleName = permissionCodeParts[0] + "/" + permissionCodeParts[1];
                String entityName = permissionCodeParts[0] + "/" + permissionCodeParts[1] + "/" + permissionCodeParts[2];
                if(permissionCodeParts.length>=5){
                    entityName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2]+"/"+permissionCodeParts[3];
                }
            if (moduleTreeNodes.containsKey(entityName)) continue;

            ModuleTreeNode subSystemModuleTreeNode = moduleTreeNodes.get(subSystemName);
            if (subSystemModuleTreeNode == null) {
                Module subSystemModule = findMoudle(subSystemName);
                if ("否".equals(subSystemModule.getIsInUse())) continue;
                subSystemModuleTreeNode = this.convertModule2TreeNode(subSystemModule, 1);
                subSystemModules.add(subSystemModuleTreeNode);
                moduleTreeNodes.put(subSystemName, subSystemModuleTreeNode);
            }

            ModuleTreeNode moduleModuleTreeNode = moduleTreeNodes.get(moduleName);
            if (moduleModuleTreeNode == null) {
                Module moduleModule = findMoudle(moduleName);
                if ("否".equals(moduleModule.getIsInUse())) continue;
                moduleModuleTreeNode = this.convertModule2TreeNode(moduleModule, 2);
                subSystemModuleTreeNode.addNode(moduleModuleTreeNode);
                moduleTreeNodes.put(moduleName, moduleModuleTreeNode);
            }
            if(permissionCodeParts.length>=5){
                moduleName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2];
                entityName=permissionCodeParts[0] + "/" + permissionCodeParts[1]+"/"+permissionCodeParts[2]+"/"+permissionCodeParts[3];
                   moduleModuleTreeNode = moduleTreeNodes.get(moduleName);
                if (moduleModuleTreeNode == null) {
                    Module moduleModule = findMoudle(moduleName);
                    if ("否".equals(moduleModule.getIsInUse())) continue;
                    moduleModuleTreeNode = this.convertModule2TreeNode(moduleModule, 3);
                    subSystemModuleTreeNode.addNode(moduleModuleTreeNode);
                    moduleTreeNodes.put(moduleName, moduleModuleTreeNode);
                }
            }

            Module entityModule = findMoudle(entityName);
            if ("否".equals(entityModule.getIsInUse())) continue;
            if(permissionCodeParts.length>=5){}
            ModuleTreeNode entityModuleTreeNode = this.convertModule2TreeNode(entityModule, 3);
            if(permissionCodeParts.length>=5){
             entityModuleTreeNode = this.convertModule2TreeNode(entityModule, 4);
            }
            entityModuleTreeNode.setRouteData("/wisdomCateringMain" + entityModuleTreeNode.getRouteData());
            moduleModuleTreeNode.addNode(entityModuleTreeNode);
            moduleTreeNodes.put(entityName, entityModuleTreeNode);
        }
        for (ModuleTreeNode moduleTreeNode : moduleTreeNodes.values()) {
            if (moduleTreeNode.getNodes().size() == 0) continue;
            ;
            Collections.sort(moduleTreeNode.getNodes(), new Comparator<ModuleTreeNode>() {
                @Override
                public int compare(ModuleTreeNode o1, ModuleTreeNode o2) {
                    int i = (int) (o1.getDisplayIndex() - o2.getDisplayIndex());
                    return i;
                }
            });
        }
        ModuleTreeNode rootModuleTreeNode = new ModuleTreeNode();
        rootModuleTreeNode.setId("root");
        rootModuleTreeNode.setName("root");
        rootModuleTreeNode.setText("根节点");
        rootModuleTreeNode.getNodes().addAll(subSystemModules);
        sort(rootModuleTreeNode);
        return rootModuleTreeNode;
    }

    private Module findMoudle(String code) {
        Module module = ModuleManager.findModule(code);
        if (module == null) {
            String error = "没找到模块：" + code;
            logger.error(error);
            throw new DDDException(error);
        }
        return module;
    }

    private ModuleTreeNode convertModule2TreeNode(Module module, Integer level) {

        ModuleTreeNode treeNode = new ModuleTreeNode();
        treeNode.setText(module.getName());
        treeNode.setId(module.getCode());
        treeNode.setRouteData(module.getRoute());
        treeNode.setLocation(module.getUrl());
        treeNode.setRouteParamsObj(module.getRouteParamsObj());
        treeNode.setDisplayIndex(module.getDisplayIndex());

        treeNode.setLevel(level.toString());
        treeNode.setIsExpanded(false);
        treeNode.setIsSelected(false);

        String iconClass = module.getIconClass();
        if (iconClass != null && (!("".equals(iconClass)))) {
            treeNode.setIcon(iconClass);
        } else {
            //
        }
        return treeNode;
    }

    private void sort(ModuleTreeNode node){
        if(node.getNodes().size()==0){
            return;
        }
        node.getNodes().sort(Comparator.comparing(ModuleTreeNode::getDisplayIndex));
        for(int i=0;i<node.getNodes().size();i++){
            sort(node.getNodes().get(i));
        }
    }
}
