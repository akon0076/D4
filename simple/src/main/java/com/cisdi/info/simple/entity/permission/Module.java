package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	private String code;

	@SerializedName("name1")
	@Expose
	private String name;

	@Expose
	private String url;

	@Expose
	private String route;


	@Expose
	private String iconClass;

	@Expose
	private Long displayIndex;

	private Long parentId;

	@Transient
	private Module parent;

	@Expose
	private String parentCode;

	@Expose
	@Transient
	private String parentName;

	@Expose
	private String moduleType;

	@Expose
	private String isInUse;

	@Expose
	private String routeParamsObj;

	@Transient
	private List<Module> children;

	@Expose
	@Transient
	private List<Permission> permissions = new ArrayList<Permission>();

	public Module() {
	}

	public Module(String code, String name, String url, String route, String iconClass, Long displayIndex, String moduleType, String isInUse, String routeParamsObj)
	{
		this(code, name, url, route, iconClass, displayIndex, "", "", moduleType, isInUse, routeParamsObj);
	}

	public Module(String code, String name, String url, String route, String iconClass, Long displayIndex, String moduleType, String isInUse, String routeParamsObj, Module parent)
	{
		this(code, name, url, route, iconClass, displayIndex, parent.getCode(), parent.getName(), moduleType, isInUse, routeParamsObj);
	}

	public Module(String code, String name, String url, String route, String iconClass, Long displayIndex, String parentCode, String parentName, String moduleType, String isInUse, String routeParamsObj) {
		this.code = code;
		this.name = name;
		this.url = url;
		this.route = route;
		this.iconClass = iconClass;
		this.displayIndex = displayIndex;
		this.parentCode = parentCode;
		this.parentName = parentName;
		this.moduleType = moduleType;
		this.isInUse = isInUse;
		this.routeParamsObj = routeParamsObj;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getIconClass() {
		return this.iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public Long getDisplayIndex() {
		return this.displayIndex;
	}

	public void setDisplayIndex(Long displayIndex) {
		this.displayIndex = displayIndex;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getModuleType() {
		return this.moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

	public String getIsInUse() {
		return this.isInUse;
	}

	public void setIsInUse(String isInUse) {
		this.isInUse = isInUse;
	}

	public String getRouteParamsObj() {
		return this.routeParamsObj;
	}

	public void setRouteParamsObj(String routeParamsObj) {
		this.routeParamsObj = routeParamsObj;
	}

	public List<Module> getChildren() {
		return this.children;
	}

	public void setChildren(List<Module> children) {
		this.children = children;
	}

	public Module getParent() {
		return this.parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}