package org.ddd.sms.service;

import java.util.Map;

/***
 * Author: 袁泽锋
 * Description: 基础信息传输接口
 * Date: 2019/5/2 17:32
 **/
public interface BaseMsg {

    /**
     * Description: 发送信息
     * Date: 2019/5/22 13:45
     * @param contactTarget 联系人
     * @param msgs 信息
     * @return int
     **/
    Map sendMsg(String contactTarget, Map<String, String> msgs);

}
