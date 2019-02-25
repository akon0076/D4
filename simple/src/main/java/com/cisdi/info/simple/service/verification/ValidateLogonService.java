package com.cisdi.info.simple.service.verification;

/**
 * Created by 向鑫 on 2019/1/22
 */
public interface ValidateLogonService {
    public Integer getCounts(String ipAddress);
    public Integer addRecord(String ipAddress,Integer operatorId,Integer count);
    public Integer  updateCountsByIp(String ipAddress,Integer operatorId,Integer count);
}
