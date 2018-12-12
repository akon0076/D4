package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.entity.base.*;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;
import com.cisdi.info.simple.entity.organization.Organization;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@DEntity(label="权限点",comment="",moduleLabel="授权")
@Entity(name="simple_permission")
public class Permission extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	@DColumn(index=3,label="编码",comment="编码")
	@Column(length=200,nullable=false,unique=false)
	private String code;

	@Expose
	@SerializedName("name1")
	@DColumn(index=4,label="名称",comment="名称")
	@Column(length=200,nullable=false,unique=false)
	private String name;

	@Expose
	@DColumn(index=5,label="全名",comment="名称加上模块名称")
	@Column(length=200,nullable=false,unique=false)
	private String fullName;

	@Expose
	@DColumn(index=6,label="模块编码",comment="模块编码")
	@Column(length=100,nullable=false,unique=false)
	private String moduleCode;

	//权限点需要的访问的URLs
	@Expose
	@Transient
	private List<String> urls;

	public Permission() {
	}

	public Permission(String code, String name,String fullName, String moduleCode,List<String> urls) {
		this.code = code;
		this.name = name;
		this.fullName = fullName;
		this.moduleCode = moduleCode;
		this.urls = urls;
	}

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

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}


	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
}