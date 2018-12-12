package com.cisdi.info.simple.dto.organization;

import com.cisdi.info.simple.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;


/**
 * 单位
 * @author 许洲
 */

public class OrganizationDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long EId;
	private String code;
	private String name;
    private String linkTel;
	private Long parentId;
	private String parentName;

	public Long getEId() {
		return EId;
	}

	public void setEId(Long EId) {
		this.EId = EId;
	}

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

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
