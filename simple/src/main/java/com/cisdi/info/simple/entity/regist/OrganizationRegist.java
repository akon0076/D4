package com.cisdi.info.simple.entity.regist;

import com.cisdi.info.simple.entity.base.*;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;
import com.cisdi.info.simple.entity.organization.Employee;

@DEntity(label="单位注册",comment="",moduleLabel="注册管理")
@Entity(name="simple_organization_regist")
public class OrganizationRegist extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="营业执照号",comment="营业执照号")
	@Column(name="business_license_code",length=200,nullable=false,unique=true)
	private String businessLicenseCode;

	@DColumn(index=4,label="注册资金(万元)",comment="注册资金(万元)")
	@Column(name="amount",length=255,nullable=true,unique=false)
	private Double amount;

	@DColumn(index=5,label="单位地址",comment="单位地址")
	@Column(name="address",length=200,nullable=true,unique=false)
	private String address;

	@DColumn(index=6,label="法人",comment="法人")
	@Column(name="legal_person",length=200,nullable=true,unique=false)
	private String legalPerson;

	@DColumn(index=7,label="行业属性",comment="行业属性")
	@Column(name="industry_attributes",length=300,nullable=true,unique=false)
	private String industryAttributes;

	@DColumn(index=8,label="公司座机",comment="公司座机")
	@Column(name="landline",length=200,nullable=true,unique=false)
	private String landline;

	@DColumn(index=9,label="主营业务",comment="主营业务")
	@Column(name="main_business",length=500,nullable=true,unique=false)
	private String mainBusiness;

	@DColumn(index=10,label="审核状态",comment="审核状态:通过、未通过")
	@Column(name="audit_state",length=100,nullable=true,unique=false)
	private String auditState;

	@DColumn(index=11,label="审核人员",foreignEntity="Employee",comment="审核人员")
	@Column(name="audit_emloyee_id",length=255,nullable=true,unique=false)
	private Long auditEmloyeeId;

	@Transient
	private Employee auditEmloyee;

	@Transient
	@DColumn(index=11,label="审核人员",foreignEntity="Employee",comment="审核人员")
	private String auditEmloyeeName;

	@DColumn(index=12,label="审核时间",comment="审核时间")
	@Column(name="audit_date",length=255,nullable=true,unique=false)
	private Date auditDate;

	@DColumn(index=13,label="管理员名称",comment="管理员名称")
	@Column(name="admin_name",length=200,nullable=true,unique=false)
	private String adminName;

	@DColumn(index=13,label="审核意见",comment="审核意见")
	@Column(name="audit_remark",length=200,nullable=true,unique=false)
	private String auditRemark;

	@DColumn(index=14,label="管理员手机",comment="管理员手机")
	@Column(name="admin_link_tel",length=200,nullable=true,unique=false)
	private String adminLinkTel;

	@DColumn(index=15,label="管理员邮箱",comment="管理员邮箱")
	@Column(name="admin_email",length=200,nullable=true,unique=false)
	private String adminEmail;

	@DColumn(index=16,label="密码",comment="密码")
	@Column(name="admin_password",length=200,nullable=true,unique=false)
	private String adminPassword;

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getBusinessLicenseCode() {
		return this.businessLicenseCode;
	}

	public void setBusinessLicenseCode(String businessLicenseCode) {
		this.businessLicenseCode = businessLicenseCode;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLegalPerson() {
		return this.legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getIndustryAttributes() {
		return this.industryAttributes;
	}

	public void setIndustryAttributes(String industryAttributes) {
		this.industryAttributes = industryAttributes;
	}

	public String getLandline() {
		return this.landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMainBusiness() {
		return this.mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public Long getAuditEmloyeeId() {
		return this.auditEmloyeeId;
	}

	public void setAuditEmloyeeId(Long auditEmloyeeId) {
		this.auditEmloyeeId = auditEmloyeeId;
	}

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminLinkTel() {
		return this.adminLinkTel;
	}

	public void setAdminLinkTel(String adminLinkTel) {
		this.adminLinkTel = adminLinkTel;
	}

	public String getAdminEmail() {
		return this.adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return this.adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public Employee getAuditEmloyee() {
		return this.auditEmloyee;
	}

	public void setAuditEmloyee(Employee auditEmloyee) {
		if(auditEmloyee == null){
			return;
		}
		this.auditEmloyeeId = auditEmloyee.getEId();
		this.auditEmloyee = auditEmloyee;
	}

	public String getAuditEmloyeeName() {
		return this.auditEmloyeeName;
	}

	public void setAuditEmloyeeName(String auditEmloyeeName) {
		this.auditEmloyeeName = auditEmloyeeName;
	}



}
