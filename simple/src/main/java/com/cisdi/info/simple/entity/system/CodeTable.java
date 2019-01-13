package com.cisdi.info.simple.entity.system;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@DEntity(label="码表",comment="",moduleLabel="系统管理")
@Entity(name="simple_code_table")
public class CodeTable extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Expose
	@SerializedName("name1")
	@DColumn(index=3,label="名称",comment="名称")
	@Column(name="name",length=200,nullable=true,unique=false)
	private String name;

	@Expose
	@DColumn(index=4,label="码表全名",comment="为码表类型名称加所有父级名称,加本级名称")
	@Column(name="fullname",length=200,nullable=false,unique=false)
	private String fullname;

	@Expose
	@DColumn(index=5,label="码表类型",comment="码表种类")
	@Column(name="code_type",length=100,nullable=false,unique=false)
	private String codeType;

	@Expose
	@DColumn(index=6,label="父级",comment="父级")
	@Column(name="parent_fullname",length=100,nullable=true,unique=false)
	private String parentFullname;

	@Expose
	@DColumn(index=7,label="父级",foreignEntity="CodeTable",comment="父级")
	@Column(name="parent_id",length=250,nullable=true,unique=false)
	private Long parentId;

	@Transient
	private CodeTable parent;

	@Expose
	@Transient
	@DColumn(index=7,label="父级",foreignEntity="CodeTable",comment="父级")
	private String parentName;

	@Expose
	@DColumn(index=8,label="显示顺序",comment="显示顺序")
	@Column(name="display_order",length=250,nullable=true,unique=false)
	private Integer displayOrder;

	@Expose
	@Transient
	private List<CodeTable> children;

	@Expose
	@DColumn(index=10,label="有子码表",comment="是否有孩子节点")
	@Column(name="has_children",length=250,nullable=true,unique=false)
	private Boolean hasChildren;

    public CodeTable() {
    }

    public CodeTable(String codeType, String name) {
        this.name = name;
        this.fullname= StringUtils.join(new String[]{codeType,name},".");
        this.codeType = codeType;
        this.displayOrder = 0;
    }
    public CodeTable( String name,CodeTable parent) {
        this.name = name;
        this.codeType = parent.codeType;
        this.fullname= StringUtils.join(new String[]{parent.getFullname(),name},".");
        this.displayOrder = 0;
        this.parent = parent;
        parent.children.add(this);
    }
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCodeType() {
		return this.codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getParentFullname() {
		return this.parentFullname;
	}

	public void setParentFullname(String parentFullname) {
		this.parentFullname = parentFullname;
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

	public List<CodeTable> getChildren() {
		return this.children;
	}

	public void setChildren(List<CodeTable> children) {
		this.children = children;
	}

	public Boolean getHasChildren() {
		return this.hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public CodeTable getParent() {
		return this.parent;
	}

	public void setParent(CodeTable parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CodeTable codeTable = (CodeTable) o;
		return Objects.equals(fullname, codeTable.fullname);
	}

	@Override
	public int hashCode() {

		return Objects.hash(fullname);
	}
	
}