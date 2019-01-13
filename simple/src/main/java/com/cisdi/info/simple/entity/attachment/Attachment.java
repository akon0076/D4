package com.cisdi.info.simple.entity.attachment;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;

import com.cisdi.info.simple.entity.organization.Employee;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="附件",comment="",moduleLabel="附件")
@Entity(name="simple_attachment")
public class Attachment extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="附件的真实名称",comment="附件的真实名称")
	@Column(name="attachment_real_name",length=255,nullable=false,unique=false)
	private String attachmentRealName;

	@DColumn(index=4,label="保存文件名",comment="保存文件名")
	@Column(name="attachment_logical_name",length=255,nullable=false,unique=false)
	private String attachmentLogicalName;

	@DColumn(index=5,label="上传时间",comment="上传时间")
	@Column(name="upload_time",length=255,nullable=true,unique=false)
	private Date uploadTime;

	@DColumn(index=6,label="上传人",foreignEntity="Employee",comment="上传人")
	@Column(name="upload_employee_id",length=255,nullable=true,unique=false)
	private Long uploadEmployeeId;

	@Transient
	private Employee uploadEmployee;

	@Transient
	@DColumn(index=6,label="上传人",foreignEntity="Employee",comment="上传人")
	private String uploadEmployeeName;

	@DColumn(index=7,label="附件地址",comment="附件地址")
	@Column(name="attachment_addr",length=255,nullable=false,unique=true)
	private String attachmentAddr;

	@DColumn(index=8,label="关联表单Id",comment="关联表单Id")
	@Column(name="associate_form_id",length=255,nullable=false,unique=false)
	private String associateFormId;

	@DColumn(index=9,label="关联表单名称",comment="关联表单名称")
	@Column(name="associate_form_name",length=255,nullable=false,unique=false)
	private String associateFormName;

	@DColumn(index=10,label="附件大小",comment="附件大小")
	@Column(name="associate_size",length=255,nullable=false,unique=false)
	private Long associateSize;

	@DColumn(index=11,label="是否有效",comment="是否有效")
	@Column(name="is_effective",length=255,nullable=true,unique=false)
	private Boolean isEffective;


	public String getAttachmentRealName() {
		return this.attachmentRealName;
	}

	public void setAttachmentRealName(String attachmentRealName) {
		this.attachmentRealName = attachmentRealName;
	}

	public String getAttachmentLogicalName() {
		return this.attachmentLogicalName;
	}

	public void setAttachmentLogicalName(String attachmentLogicalName) {
		this.attachmentLogicalName = attachmentLogicalName;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Long getUploadEmployeeId() {
		return this.uploadEmployeeId;
	}

	public void setUploadEmployeeId(Long uploadEmployeeId) {
		this.uploadEmployeeId = uploadEmployeeId;
	}

	public String getAttachmentAddr() {
		return this.attachmentAddr;
	}

	public void setAttachmentAddr(String attachmentAddr) {
		this.attachmentAddr = attachmentAddr;
	}

	public String getAssociateFormId() {
		return this.associateFormId;
	}

	public void setAssociateFormId(String associateFormId) {
		this.associateFormId = associateFormId;
	}

	public String getAssociateFormName() {
		return this.associateFormName;
	}

	public void setAssociateFormName(String associateFormName) {
		this.associateFormName = associateFormName;
	}

	public Long getAssociateSize() {
		return this.associateSize;
	}

	public void setAssociateSize(Long associateSize) {
		this.associateSize = associateSize;
	}

	public Boolean getIsEffective() {
		return this.isEffective;
	}

	public void setIsEffective(Boolean isEffective) {
		this.isEffective = isEffective;
	}

	public Employee getUploadEmployee() {
		return this.uploadEmployee;
	}

	public void setUploadEmployee(Employee uploadEmployee) {
		this.uploadEmployee = uploadEmployee;
	}

	public String getUploadEmployeeName() {
		return this.uploadEmployeeName;
	}

	public void setUploadEmployeeName(String uploadEmployeeName) {
		this.uploadEmployeeName = uploadEmployeeName;
	}

}