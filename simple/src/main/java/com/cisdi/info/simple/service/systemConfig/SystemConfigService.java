package com.cisdi.info.simple.service.systemConfig;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.dto.base.PageResultDTO;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.systemConfig.SystemConfig;

import java.io.InputStream;
import java.util.List;

public interface SystemConfigService {

    public PageResultDTO findSystemConfigs(PageDTO pageDTO);

    public List<SystemConfig> findAllSystemConfigs();

    public List<SystemConfig> findSystemConfigsWithIdNameByName(String systemConfigName);

    public List<SystemConfig> findAllSystemConfigsWithIdName();

    public SystemConfig findSystemConfigsWithIdNameById(Long systemConfigId);

    public SystemConfig findSystemConfig(Long systemConfigId);

    //所有外键的Name都以加载
    public SystemConfig findSystemConfigWithForeignName(Long systemConfigId);

    public SystemConfig saveSystemConfig(SystemConfig systemConfig);

    public SystemConfig updateSystemConfig(SystemConfig systemConfig);

    public void deleteSystemConfig(Long systemConfigId);


    public String getStringValueByKey(String key);

    public  List<Attachment> getFilePathByKey(String key);
}
