package com.cisdi.info.simple.dao.systemConfig;

import com.cisdi.info.simple.dto.base.PageDTO;
import com.cisdi.info.simple.entity.systemConfig.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
@Component(value = "systemConfigDao")
public interface SystemConfigDao {

    public List<SystemConfig> findSystemConfigs(PageDTO pageDTO);

    public List<SystemConfig> findAllSystemConfigs();

    public List<SystemConfig> findAllSystemConfigsWithIdName();

    public List<SystemConfig> findSystemConfigsWithIdNameByName(@Param("systemConfigName") String systemConfigName);

    public SystemConfig findSystemConfigsWithIdNameById(@Param(" systemConfigId") Long systemConfigId);

    public Long findSystemConfigTotalCount(PageDTO pageDTO);

    public SystemConfig findSystemConfig(@Param("systemConfigId") Long systemConfigId);

    //所有外键的Name都以加载
    public SystemConfig findSystemConfigWithForeignName(@Param("systemConfigId") Long systemConfigId);

    public Integer saveSystemConfig(SystemConfig systemConfig);

    public Integer updateSystemConfig(SystemConfig systemConfig);

    public Integer deleteSystemConfig(@Param("systemConfigId") Long systemConfigId);

    public List<SystemConfig> findSystemConfigsByCondition(@Param("sql")String  sql);
}
