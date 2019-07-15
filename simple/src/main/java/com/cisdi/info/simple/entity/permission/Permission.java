package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.entity.base.BaseEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

public class Permission extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	private String code;

	@Expose
	@SerializedName("name1")
	private String name;

	@Expose
	private String fullName;

	private String moduleName;

	@Expose
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

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}