package com.cisdi.info.simple.service.permission.impl;

import com.cisdi.info.simple.entity.permission.Module;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.permission.ExtendsMouleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chengbg
 * @date: 2019/5/16
 **/
@Service
@Transactional
public class ExtendsMouleServiceBean extends BaseService implements ExtendsMouleService {

    /**
     * 加载扩展模块
     * @return
     */
    @Override
    public List<Module> loadExtendMoule() {
        return null;
    }
}
