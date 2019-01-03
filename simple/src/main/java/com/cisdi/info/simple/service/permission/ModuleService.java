package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;

import java.util.List;

public interface ModuleService {

    public PageResultDTO findModules(PageDTO pageDTO);

    public List<Module> findAllModules();

    public List<Module> findAllModulesWithIdName();

    public Module findModule(Long moduleId);

    //所有外键的Name都以加载
    public Module findModuleWithForeignName(Long moduleId);

    public void deleteModule(Long moduleId);

    public ModuleTreeNode constructNewTree(Long operatorId, String modelType);

    Integer moduleAccount();

    public ModuleTreeNode wisdomCateringConstructNewTree(Long operatorId, String modelType);
}
