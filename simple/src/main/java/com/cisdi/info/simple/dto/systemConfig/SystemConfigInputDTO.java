package com.cisdi.info.simple.dto.systemConfig;


/**
 * Created by 向鑫 on 2018/11/30
 */
public class SystemConfigInputDTO {
    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public Long  eid;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSystemConfigKey() {
        return systemConfigKey;
    }

    public void setSystemConfigKey(String systemConfigKey) {
        this.systemConfigKey = systemConfigKey;
    }

    public String getSystemConfigValue() {
        return systemConfigValue;
    }

    public void setSystemConfigValue(String systemConfigValue) {
        this.systemConfigValue = systemConfigValue;
    }

    public String getSystemConfigDescription() {
        return systemConfigDescription;
    }

    public void setSystemConfigDescription(String systemConfigDescription) {
        this.systemConfigDescription = systemConfigDescription;
    }

    private String name;
    private String remark;
    private String systemConfigKey;

    private String systemConfigValue;

    private String systemConfigDescription;

    public Long[] getDeleteIds() {
        return deleteIds;
    }

    public void setDeleteIds(Long[] deleteIds) {
        this.deleteIds = deleteIds;
    }

    private Long deleteIds[];

}
