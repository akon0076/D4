package com.cisdi.info.simple.service.verification.impl;

import com.cisdi.info.simple.dao.verification.ValidateLogonDao;
import com.cisdi.info.simple.entity.verification.ValidateLogon;
import com.cisdi.info.simple.service.verification.ValidateLogonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 向鑫 on 2019/1/22
 */
@Service
@Transactional
public class ValidateLogonServiceBean implements ValidateLogonService {
    @Autowired
    private   ValidateLogonDao validateLogonDao;
    @Override
    public Integer getCounts(String ipAddress) {
        return this.validateLogonDao.getCounts(ipAddress);
    }

    @Override
    public Integer addRecord(String ipAddress, Integer operatorId, Integer count) {
        Map map = new HashMap();
        map.put("ipAddress",ipAddress);
        map.put("operatorId",operatorId);
        map.put("count",count);
        return this.validateLogonDao.addRecord(map);
    }

    @Override
    public Integer updateCountsByIp(String ipAddress,Integer operatorId,Integer count) {
        Map map = new HashMap();
        map.put("ipAddress",ipAddress);
        map.put("operatorId",operatorId);
        map.put("count",count);
        return this.validateLogonDao.updateCountsByIp(map);
    }
}
