package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;

import java.util.List;

public interface ModuleService {

    public PageResultDTO findModules(PageDTO pageDTO);

    public List<Module> findAllModules();

    public ModuleTreeNode findAllTreeNode(String modelType);

    public List<Module> findAllModulesWithIdName();

    public Module findModule(Long moduleId);

    //所有外键的Name都以加载
    public Module findModuleWithForeignName(String moduleCode);

    public void saveModule(Module module) throws Exception;

    public Module updateModule(Module module);

    public void deleteModule(String moduleCode);

    public ModuleTreeNode constructNewTree(Long operatorId, String modelType);

    /**
     * 初始化模块进入数据库
     *
     * @param modules
     */
    void saveModelsToDataBase(List<Module> modules);

    /**
     * 更新模块
     *
     * @param modules
     */
    void changeModelsToDataBase(List<Module> modules);

    Integer moduleAccount();

    public ModuleTreeNode wisdomCateringConstructNewTree(Long operatorId, String modelType);
}
