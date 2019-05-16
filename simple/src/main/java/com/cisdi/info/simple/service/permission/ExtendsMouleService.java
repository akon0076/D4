package com.cisdi.info.simple.service.permission;

import com.cisdi.info.simple.entity.permission.Module;

import java.util.List;

/**
 * @author: chengbg
 * @date: 2019/5/16
 **/
public interface ExtendsMouleService {

    /**
     * 加载扩展模块
     * @return
     */
    public List<Module> loadExtendMoule();
}
