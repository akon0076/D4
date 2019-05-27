package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;

import java.util.List;

public interface ModuleService {

    /**
     * 获取全部模块
     * @return
     */
    public List<Module> findAllModules();

    /**
     * 找到全部叶节点模块
     * @return
     */
    public List<Module> findAllLeafModules();

    /**
     * 找到全部非叶节点
     * @return
     */
    public List<Module> findAllParentModules();

    public ModuleTreeNode findAllTreeNode(String modelType);

    public List<Module> findAllModulesWithIdName();

    public Module findModule(Long moduleId);

    //所有外键的Name都以加载
    public Module findModuleWithForeignName(String moduleCode);

    public void saveModule(Module module) throws Exception;

    public Module updateModule(Module module);

    public boolean deleteModule(String moduleCode);

    public ModuleTreeNode constructNewTree(Long operatorId, String modelType);

    public Module findModuleForDisplay(String moduleCode);

    /**
     * 根据serviceName加载扩展模块
     * @param serviceName
     * @return
     */
    public List<Module> loadExtendMoule(String serviceName);

    /**
     * 获取全部模块树
     * @param pageDTO
     * @return
     */
    public List<Module> findModuleTree(PageDTO pageDTO);
}
