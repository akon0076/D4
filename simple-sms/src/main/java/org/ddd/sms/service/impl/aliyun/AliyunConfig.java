package org.ddd.sms.service.impl.aliyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: AliyunConfig
 * Description: 阿里云sms配置
 * Author: 袁泽锋
 * Date: 2019/7/5 15:29
 * Version: 1.0
 **/
@Component
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String product;
    private String domain;
    private String regionId;
    private String signName;
    private TemplateCode templateCode;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public TemplateCode getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(TemplateCode templateCode) {
        this.templateCode = templateCode;
    }

    @Override
    public String toString() {
        return "AliyunConfig{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", product='" + product + '\'' +
                ", domain='" + domain + '\'' +
                ", regionId='" + regionId + '\'' +
                ", signName='" + signName + '\'' +
                ", templateCode=" + templateCode +
                '}';
    }

    /**
     * 模板配置类
     */
    public static class TemplateCode {
        private String pin;

        String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        @Override
        public String toString() {
            return "TemplateCode{" +
                    "pin='" + pin + '\'' +
                    '}';
        }
    }

}
