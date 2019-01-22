package com.cisdi.info.simple.dao.verification;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by 向鑫 on 2019/1/22
 */
@Mapper
@Component(value ="validateLogonDao")
public interface ValidateLogonDao {
    //根据Ip地址查出当前的次数
    public Integer getCounts(String ipAddress);
    //新增一条记录
    public Integer addRecord(Map map);
    //更新一条记录
    public Integer  updateCountsByIp(Map  map);
}
