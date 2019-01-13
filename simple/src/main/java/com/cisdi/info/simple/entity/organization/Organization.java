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

	@DColumn(index=8,label="营业执照号",comment="营业执照号")
	@Column(name="business_license_code",length=200,nullable=false,unique=true)
	private String businessLicenseCode;

	@DColumn(index=9,label="注册资金(万元)",comment="注册资金(万元)")
	@Column(name="amount",length=255,nullable=true,unique=false)
	private Double amount;

	@DColumn(index=10,label="法人",comment="法人")
	@Column(name="legal_person",length=200,nullable=true,unique=false)
	private String legalPerson;

	@DColumn(index=11,label="行业属性",comment="行业属性")
	@Column(name="industry_attributes",length=300,nullable=true,unique=false)
	private String industryAttributes;

	@DColumn(index=12,label="公司座机",comment="公司座机")
	@Column(name="landline",length=200,nullable=true,unique=false)
	private String landline;

	@DColumn(index=13,label="主营业务",comment="主营业务")
	@Column(name="main_business",length=500,nullable=true,unique=false)
	private String mainBusiness;


	@DColumn(index=9,label="传真",comment="传真")
	@Column(name="fax",length=15,nullable=true,unique=false)
	private String fax;

	@DColumn(index=12,label="投资金额",comment="投资金额")
	@Column(name="investment_money",length=255,nullable=true,unique=false)
	private Double investmentMoney;

	@DColumn(index=14,label="入驻时间",comment="入驻时间")
	@Column(name="admission_time",length=255,nullable=true,unique=false)
	private Date admissionTime;

	@DColumn(index=15,label="营业期限自",comment="营业期限自")
	@Column(name="business_start_date",length=255,nullable=true,unique=false)
	private Date businessStartDate;

	@DColumn(index=16,label="营业期限至",comment="营业期限至")
	@Column(name="business_end_date",length=255,nullable=true,unique=false)
	private Date businessEndDate;

	@DColumn(index=17,label="统一社会信用代码",comment="统一社会信用代码")
	@Column(name="unified_code",length=23,nullable=true,unique=false)
	private String unifiedCode;

	@DColumn(index=18,label="类型",codeTable="Type",comment="类型")
	@Column(name="type",length=255,nullable=true,unique=false)
	private String type;

	@DColumn(index=19,label="登记机关",comment="登记机关")
	@Column(name="register_office",length=100,nullable=true,unique=false)
	private String registerOffice;

	@DColumn(index=20,label="登记状态",codeTable="RegisterStatus",comment="登记状态")
	@Column(name="register_status",length=255,nullable=true,unique=false)
	private String registerStatus;

	@DColumn(index=21,label="核准日期",comment="核准日期")
	@Column(name="check_date",length=255,nullable=true,unique=false)
	private Date checkDate;

	@DColumn(index=22,label="住所",comment="住所")
	@Column(name="residence",length=100,nullable=true,unique=false)
	private String residence;

	@DColumn(index=23,label="成立日期",comment="成立日期")
	@Column(name="establish_date",length=255,nullable=true,unique=false)
	private Date establishDate;

	@DColumn(index=24,label="审核状态",comment="审核状态")
	@Column(name="audit_status",length=255,nullable=true,unique=false)
	private String auditStatus;

	@DColumn(index=25,label="功能类型",comment="功能类型")
	@Column(name="function_type",length=255,nullable=true,unique=false)
	private String functionType;

	@DColumn(index=26,label="入驻面积",comment="入驻面积(平方米)")
	@Column(name="admission_area",length=255,nullable=true,unique=false)
	private Double admissionArea;

	@DColumn(index=27,label="入驻楼宇",comment="入驻楼宇")
	@Column(name="admission_building",length=255,nullable=true,unique=false)
	private String admissionBuilding;

	@DColumn(index=28,label="企业规模",comment="企业规模")
	@Column(name="scale",length=255,nullable=true,unique=false)
	private String scale;

	@DColumn(index=29,label="所属行业",codeTable="Industry",comment="所属行业")
	@Column(name="industry",length=255,nullable=true,unique=false)
	private String industry;

	@DColumn(index=30,label="经营模式",codeTable="BusinessPattern",comment="经营模式")
	@Column(name="business_pattern",length=255,nullable=true,unique=false)
	private String businessPattern;

	@DColumn(index=31,label="从业人员",comment="从业人员(人)")
	@Column(name="work_person_num",length=255,nullable=true,unique=false)
	private Integer workPersonNum;

	@DColumn(index=32,label="研发人员",comment="研发人员(人)")
	@Column(name="research_person_num",length=255,nullable=true,unique=false)
	private Integer researchPersonNum;

	@Transient
	private String adminNamePhone;

	@DColumn(index=14,label="管理员名称",comment="管理员名称")
	@Column(name="admin_name",length=200,nullable=true,unique=false)
	private String adminName;

	@DColumn(index=15,label="管理员手机",comment="管理员手机")
	@Column(name="admin_link_tel",length=200,nullable=true,unique=false)
	private String adminLinkTel;

	@DColumn(index=16,label="管理员邮箱",comment="管理员邮箱")
	@Column(name="admin_email",length=200,nullable=true,unique=false)
	private String adminEmail;

	public String getBusinessLicenseCode() {
		return businessLicenseCode;
	}

	public void setBusinessLicenseCode(String businessLicenseCode) {
		this.businessLicenseCode = businessLicenseCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getIndustryAttributes() {
		return industryAttributes;
	}

	public void setIndustryAttributes(String industryAttributes) {
		this.industryAttributes = industryAttributes;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminLinkTel() {
		return adminLinkTel;
	}

	public void setAdminLinkTel(String adminLinkTel) {
		this.adminLinkTel = adminLinkTel;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Double getInvestmentMoney() {
		return investmentMoney;
	}

	public void setInvestmentMoney(Double investmentMoney) {
		this.investmentMoney = investmentMoney;
	}

	public Date getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(Date admissionTime) {
		this.admissionTime = admissionTime;
	}

	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	public String getUnifiedCode() {
		return unifiedCode;
	}

	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegisterOffice() {
		return registerOffice;
	}

	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public Date getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public Double getAdmissionArea() {
		return admissionArea;
	}

	public void setAdmissionArea(Double admissionArea) {
		this.admissionArea = admissionArea;
	}

	public String getAdmissionBuilding() {
		return admissionBuilding;
	}

	public void setAdmissionBuilding(String admissionBuilding) {
		this.admissionBuilding = admissionBuilding;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getBusinessPattern() {
		return businessPattern;
	}

	public void setBusinessPattern(String businessPattern) {
		this.businessPattern = businessPattern;
	}

	public Integer getWorkPersonNum() {
		return workPersonNum;
	}

	public void setWorkPersonNum(Integer workPersonNum) {
		this.workPersonNum = workPersonNum;
	}

	public Integer getResearchPersonNum() {
		return researchPersonNum;
	}

	public void setResearchPersonNum(Integer researchPersonNum) {
		this.researchPersonNum = researchPersonNum;
	}

	public String getAdminNamePhone() {
		return adminNamePhone;
	}

	public void setAdminNamePhone(String adminNamePhone) {
		this.adminNamePhone = adminNamePhone;
	}
}
