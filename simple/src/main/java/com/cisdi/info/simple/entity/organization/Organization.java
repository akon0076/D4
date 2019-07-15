package com.cisdi.info.simple.entity.organization;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@DEntity(label="单位",comment="",moduleLabel="组织机构")
@Entity(name="simple_organization")
public class Organization extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="编码",comment="编码")
	@Column(name="code",length=200,nullable=false,unique=true)
	private String code;

	@DColumn(index=4,label="联系电话",comment="联系电话")
	@Column(name="link_tel",length=100,nullable=true,unique=false)
	private String linkTel;

	@DColumn(index=5,label="地址",comment="地址")
	@Column(name="address",length=200,nullable=true,unique=false)
	private String address;

	@DColumn(index=6,label="上级单位",foreignEntity="Organization",comment="上级单位")
	@Column(name="parent_id",length=250,nullable=true,unique=false)
	private Long parentId;

	@Transient
	private Organization parent;

	@Transient
	@DColumn(index=6,label="上级单位",foreignEntity="Organization",comment="上级单位")
	private String parentName;

	@DColumn(index=7,label="显示顺序",comment="显示顺序")
	@Column(name="display_order",length=250,nullable=true,unique=false)
	private Integer displayOrder;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLinkTel() {
		return this.linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Organization getParent() {
		return this.parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
