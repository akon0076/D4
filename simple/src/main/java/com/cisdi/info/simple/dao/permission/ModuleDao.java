package com.cisdi.info.simple.dao.permission;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.permission.Module;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component(value = "moduleDao")
public interface ModuleDao {

     List<Module> findModules(PageDTO pageDTO);

     List<Module> findAllModules();

     List<Module> findAllModulesWithIdName();

     Long findModuleTotalCount(PageDTO pageDTO);

     Module findModule(Long moduleId);

    //所有外键的Name都以加载
     Module findModuleWithForeignName(Long moduleId);

     Module saveModule(Module module);

     Module updateModule(Module module);

     void deleteModule(Long moduleId);


    /**
     * 获取数据库中模块的总条数
     * @return 数据库中模块数量
     */
    Integer moduleAccount();

    /**
     * 根据条件获取数据库中所有的模块
     * @param map 查询条件
     * @return 模块
     */
    List<Module> findModulesByKey(HashMap map);

    /**
     * 根据条件获取模块总数
     * @param map 搜素条件
     * @return 模块总数
     */
    Long moduleAccountByKey(HashMap map);

}
