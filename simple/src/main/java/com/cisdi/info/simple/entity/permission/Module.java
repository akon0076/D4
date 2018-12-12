package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.entity.base.*;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.ArrayList;
import java.util.Date;
import com.cisdi.info.simple.entity.organization.Organization;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@DEntity(label="模块",comment="",moduleLabel="授权")
@Entity(name="simple_module")
public class Module extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	@DColumn(index = 3, label = "编码", comment = "编码")
	@Column(length = 200, nullable = false, unique = true)
	private String code;

	@SerializedName("name1")
	@Expose
	@DColumn(index = 4, label = "名称", comment = "名称")
	@Column(length = 200, nullable = true, unique = false)
	private String name;

	@Expose
	@DColumn(index = 5, label = "URL地址", comment = "地址")
	@Column(length = 500, nullable = true, unique = false)
	private String url;

	@Expose
	@DColumn(index = 6, label = "路由", comment = "路由")
	@Column(length = 200, nullable = true, unique = false)
	private String route;


	@Expose
	@DColumn(index = 7, label = "图标", comment = "图标")
	@Column(length = 200, nullable = true, unique = false)

	private String iconClass;

	@Expose
	@DColumn(index = 8, label = "显示顺序", comment = "显示顺序")
	@Column(length = 250, nullable = true, unique = false)
	private Long displayIndex;

	@DColumn(index = 9, label = "上级模块", foreignEntity = "Module", comment = "上级模块")
	@Column(length = 250, nullable = true, unique = false)
	private Long parentId;

	@Transient
	private Module parent;

	@Expose
	@DColumn(index = 10, label = "上级模块编码", comment = "上级模块编码")
	@Column(length = 500, nullable = true, unique = false)
	private String parentCode;

	@Expose
	@Transient
	@DColumn(index = 9, label = "上级模块", foreignEntity = "Module", comment = "上级模块")
	private String parentName;

	@Expose
	@DColumn(index = 10, label = "模块类型", codeTable = "模块类型", comment = "模块类型")
	@Column(length = 250, nullable = true, unique = false)
	private String moduleType;

	@Expose
	@DColumn(index = 11, label = "在用", codeTable = "逻辑", comment = "在用")
	@Column(length = 250, nullable = true, unique = false)
	private String isInUse;

	@Expose
	@DColumn(index = 12, label = "路由参数对象", comment = "路由参数对象")
	@Column(length = 200, nullable = true, unique = false)
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