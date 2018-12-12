package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@DEntity(label="角色",comment="",moduleLabel="授权")
@Entity(name="simple_role")
public class Role extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="编码",comment="编码")
	@Column(name="code",length=200,nullable=false,unique=true)
	private String code;

	@DColumn(index=4,label="名称",comment="名称")
	@Column(name="name",length=200,nullable=false,unique=true)
	private String name;


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
}