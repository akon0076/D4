package com.cisdi.info.simple.entity.system;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chengbg
 * @date: 2019/5/27
 **/
public class CodeTable implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	private String uuid;

	@Expose
	private String codeTypeId;

	@Expose
	private String code;

	@Expose
	private String name;

	@Expose
	@NotBlank
	private String codeType;

	@Expose
	private String parentUUID;

	@Expose
	private int displayIndex = 1;

	@Expose
	private Long orgId;

	@Expose
	private String orgName;

	@Expose
	private String label;

	@Expose
	private String value;

	@Expose
	private boolean isPublic = true;

	@Expose
	private List<CodeTable> children = new ArrayList<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean aPublic) {
		isPublic = aPublic;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public List<CodeTable> getChildren() {
		return children;
	}


	public boolean addChildren(CodeTable codeTable) {
		return this.children.add(codeTable);
	}

	public void setChildren(List<CodeTable> children) {
		this.children = children;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(String parentUUID) {
		this.parentUUID = parentUUID;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCodeTypeId() {
		return codeTypeId;
	}

	public void setCodeTypeId(String codeTypeId) {
		this.codeTypeId = codeTypeId;
	}
}
