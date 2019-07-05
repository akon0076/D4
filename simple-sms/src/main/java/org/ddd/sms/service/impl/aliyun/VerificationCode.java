package org.ddd.sms.service.impl.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.ddd.sms.service.BaseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: VerificationCode
 * Description: 阿里云短信服务
 * Author: 袁泽锋
 * Date: 2019/5/2 9:03
 * Version: 1.0
 **/
@Component("verificationCode")
public class VerificationCode implements BaseMsg {

    private final AliyunConfig aliyunConfig;

    @Autowired
    public VerificationCode(AliyunConfig aliyunConfig) {
        this.aliyunConfig = aliyunConfig;
    }

    /**
     * Description: aliyunSMS
     * Date: 2019/5/22 13:45
     *
     * @param contactTarget 联系人
     * @param msgs          信息
     * @return int
     **/
    @Override
    public Map sendMsg(String contactTarget, Map<String, String> msgs) {
        Map<String, String> result = new HashMap<>();
        if (contactTarget == null || "".equals(contactTarget)) {
            /* 联系人为空 */
            result.put("Message","CONTACT_IS_EMPTY");
        } else if (msgs != null && !msgs.isEmpty()) {
                /* 发送验证码 */
                DefaultProfile profile = DefaultProfile.getProfile(aliyunConfig.getRegionId(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
                IAcsClient client = new DefaultAcsClient(profile);

                CommonRequest request = new CommonRequest();
                //request.setProtocol(ProtocolType.HTTPS);
                request.setSysMethod(MethodType.POST);
                request.setSysDomain("dysmsapi.aliyuncs.com");
                request.setSysVersion("2017-05-25");
                request.setSysAction("SendSms");
                request.putQueryParameter("RegionId", aliyunConfig.getRegionId());
                request.putQueryParameter("PhoneNumbers", contactTarget);
                request.putQueryParameter("SignName", aliyunConfig.getSignName());
                request.putQueryParameter("TemplateCode", aliyunConfig.getTemplateCode().getPin());
                request.putQueryParameter("TemplateParam", JSON.toJSONString(msgs));
                try {
                    CommonResponse response = client.getCommonResponse(request);
                    JSONObject jsonObject = JSONObject.parseObject(response.getData());
                    if ("OK".equals(jsonObject.getString("Code"))) {
                        result.put("Message", "OK");
                    } else {
                        /* 发送失败 */
                        result.put("Message", jsonObject.getString("Code"));
                    }
                } catch (ClientException e) {
                    /* 发送出错 */
                    e.printStackTrace();
                    result.put("Message", e.toString());
                }

        } else {
            /* 信息为空 */
            result.put("Message", "INFORMATION_IS_EMPTY");
        }
        return result;
    }
}

