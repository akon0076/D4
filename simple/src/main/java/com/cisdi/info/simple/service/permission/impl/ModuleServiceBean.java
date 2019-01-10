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
import com.cisdi.info.simple.util.Config;
import com.cisdi.info.simple.util.D4Util;
import com.cisdi.info.simple.util.ModuleManager;
import com.sun.org.apache.bcel.internal.generic.DUP;
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

       if(pageDTO.getColumnName()==null||"".equals(pageDTO.getColumnName())||pageDTO.getContent()==null||"".equals(pageDTO.getContent())){
           pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
           List list = new ArrayList(ModuleManager.findAllModules());
           List  returnList=   list.subList(pageDTO.getStartIndex(),pageDTO.getStartIndex()+pageDTO.getPageSize()>list.size()?list.size():pageDTO.getStartIndex()+pageDTO.getPageSize());
           PageResultDTO pageResultDTO = new PageResultDTO();
           pageResultDTO.setTotalCount((long) list.size());
           pageResultDTO.setDatas(returnList);
           return pageResultDTO;
       }
       else if(pageDTO.getSql().contains("AND")){
           String methodName = D4Util.getAttributerGetterName(pageDTO.getColumnName());
           List<Module> list = new ArrayList(ModuleManager.findAllModules());
           List<Module> returnedList = new ArrayList<>();
           String [] anditems=pageDTO.getContent().split("\\s+and\\s");
          for(Module module :list){
              String  result= D4Util.invokeMethodByString(module,methodName);
              boolean mark=true;
              for(int i=0;i<anditems.length;i++) {
                   if(!result.contains(anditems[i])){
                     mark=false;
                     break;
                  }
              }
              if (mark) {
                  returnedList.add(module);
              }
         }
           pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
           PageResultDTO pageResultDTO = new PageResultDTO();
           pageResultDTO.setTotalCount((long) returnedList.size());
           pageResultDTO.setDatas(returnedList.subList(pageDTO.getStartIndex(),pageDTO.getStartIndex()+pageDTO.getPageSize()>returnedList.size()?returnedList.size():pageDTO.getStartIndex()+pageDTO.getPageSize()));
           return pageResultDTO;
       }else{
           String methodName = D4Util.getAttributerGetterName(pageDTO.getColumnName());
           List<Module> list = new ArrayList(ModuleManager.findAllModules());
           List<Module> returnedList = new ArrayList<>();
           String [] items=pageDTO.getContent().split("\\s+");//or
           for(Module module :list){
               String  result= D4Util.invokeMethodByString(module,methodName);
               boolean mark=false;
               for(int i=0;i<items.length;i++) {
                   if(result.contains(items[i])){
                       mark=true;
                       break;
                   }
               }
               if (mark) {
                   returnedList.add(module);
               }
           }
           pageDTO.setStartIndex((pageDTO.getCurrentPage()-1)*pageDTO.getPageSize());
           PageResultDTO pageResultDTO = new PageResultDTO();
           pageResultDTO.setTotalCount((long) returnedList.size());
           pageResultDTO.setDatas(returnedList.subList(pageDTO.getStartIndex(),pageDTO.getStartIndex()+pageDTO.getPageSize()>returnedList.size()?returnedList.size():pageDTO.getStartIndex()+pageDTO.getPageSize()));
           return pageResultDTO;
       }
    }


    @Override
    public List<Module> findAllModules() {
        return this.moduleDao.findAllModules();
    }

    @Override
    public List<Module> findAllModulesWithIdName() {
            return new ArrayList<>(ModuleManager.findAllModules());
    }

    @Override
    public Module findModule(Long moduleId) {
        return this.moduleDao.findModule(moduleId);
    }


    //所有外键的Name都以加载
    @Override
    public Module findModuleWithForeignName(String moduleCode) {
        Module result=findMoudle(moduleCode);
        result.setHideAttribute(result.getCode());
        return result;
    }

    /**
     * 保存模块
     *
     * @param module
     * @return
     */
    @Override
    public void saveModule(Module module)  {
       ModuleManager.addModule(module);
    }

    /**
     * 更新模块
     *
     * @param module
     * @return
     */
    @Override
    public synchronized Module updateModule(Module module) {

        if(module.getParentCode()!=null&&!"".equals(module.getParentCode())&&!module.getCode().contains(module.getParentCode()+"/")){
            module.setCode(module.getParentCode()+"/"+module.getCode());
        }
        //找到要修改的Module
        Module primaryModule = ModuleManager.findModule(module.getHideAttribute());
        if (primaryModule == null) {
            throw new DDDException("该模板已经被删除,或者已经被更改");
        }
        for(int i=0;i<primaryModule.getPermissions().size();i++) {
             primaryModule.getPermissions().get(i).setModuleCode(module.getCode());
        }
        module.setPermissions(primaryModule.getPermissions());
       if(ModuleManager.getModulesForEdit().remove(module.getHideAttribute())==null) {
           throw new DDDException("该模板已经被删除,或者已经被更改");
       }

        ModuleManager.getModulesForEdit().put(module.getHideAttribute(), module);

        //遍历每个模块,找到当前要修改的子模块进行更改
        for(Module childModule :ModuleManager.getAllModules()){
            if (childModule.getParentCode()!=null&&!"".equals(childModule.getParentCode())&&childModule.getParentCode().equals(module.getHideAttribute())) {
                childModule.setParentCode(module.getCode());
                childModule.setParentName(module.getName());
            }
        }
        //保存文件
        ModuleManager.saveModules(Config.moduleFile);
         return null;
    }

    /**
     * 删除模块
     *
     * @param moduleCode
     */
    @Override
    public void deleteModule(String moduleCode) {
        Module module = ModuleManager.findModule(moduleCode);
        if (module != null) {
            String code = module.getCode();
            if (code != null) {
                if (ModuleManager.removeModule(code)) {
                    logger.debug("删除模块:" + code + "成功！");
                } else {
                    logger.debug("删除模块:" + code + "失败！");
                }
            } else {
                logger.error("该模块编码:" + moduleCode + "找不到对应模块编码！");
                throw new DDDException("该模块编码:" + moduleCode + "找不到对应模块编码！");
            }
        } else {
            logger.error("找不到模块编码为" + moduleCode + "的模块！");

            throw new DDDException("找不到模块编码为" + moduleCode + "的模块！");
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
