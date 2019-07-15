package org.ddd.sms.strategy;

import org.ddd.sms.service.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: MsgService
 * Description: 信息传输服务
 * Author: 袁泽锋
 * Date: 2019/5/2 9:23
 * Version: 1.0
 **/
@Component("msgService")
public class MsgService implements Serializable {

    private final Map<String, BaseMsg> SMSServiceMap = new ConcurrentHashMap<>();

    @Autowired
    public MsgService(Map<String, BaseMsg> SMSServiceMap) {
        SMSServiceMap.forEach(this.SMSServiceMap::put);
    }

    public Map sendMsg(String serviceType, String contactTarget, Map<String, String> msgs){
        return this.SMSServiceMap.get(serviceType).sendMsg(contactTarget, msgs);
    }

}
