package com.cisdi.info.simple.entity.systemConfig;

import com.cisdi.info.simple.entity.base.BaseEntity;

import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;


import javax.persistence.Column;
import javax.persistence.Entity;


@DEntity(label="系统参数",comment="",moduleLabel="系统参数")
@Entity(name="simple_system_config")
public class SystemConfig extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="键",comment="键")
	@Column(name="system_config_key",length=255,nullable=false,unique=true)
	private String systemConfigKey;

	@DColumn(index=4,label="值",comment="值")
	@Column(name="system_config_value",length=255,nullable=false,unique=false)
	private String systemConfigValue;

	@DColumn(index=5,label="描述",comment="描述")
	@Column(name="system_config_description",length=255,nullable=true,unique=false)
	private String systemConfigDescription;


	public String getSystemConfigKey() {
		return this.systemConfigKey;
	}

	public void setSystemConfigKey(String systemConfigKey) {
		this.systemConfigKey = systemConfigKey;
	}

	public String getSystemConfigValue() {
		return this.systemConfigValue;
	}

	public void setSystemConfigValue(String systemConfigValue) {
		this.systemConfigValue = systemConfigValue;
	}

	public String getSystemConfigDescription() {
		return this.systemConfigDescription;
	}

	public void setSystemConfigDescription(String systemConfigDescription) {
		this.systemConfigDescription = systemConfigDescription;
	}
}