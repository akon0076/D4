package com.cisdi.info.simple.entity.member;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="商户",comment="",moduleLabel="智慧餐饮")
/*@Entity(name="ps_member")*/
public class Member extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="唯一键",comment="唯一键")
	@Column(name="only_code",length=255,nullable=false,unique=true)
	private String onlyCode;

	@DColumn(index=4,label="密码",comment="密码")
	@Column(name="password",length=255,nullable=false,unique=false)
	private String password;

	@DColumn(index=5,label="编码",comment="编码")
	@Column(name="member_code",length=255,nullable=false,unique=true)
	private String memberCode;

	@DColumn(index=6,label="名称",comment="名称")
	@Column(name="member_name",length=255,nullable=false,unique=false)
	private String memberName;

	@DColumn(index=7,label="联系人",comment="联系人")
	@Column(name="person",length=255,nullable=false,unique=false)
	private String person;

	@DColumn(index=8,label="联系电话",comment="联系电话")
	@Column(name="phone",length=255,nullable=false,unique=false)
	private String phone;

	@DColumn(index=9,label="地址",comment="地址")
	@Column(name="address",length=255,nullable=false,unique=false)
	private String address;

	@DColumn(index=10,label="餐饮店简介",comment="餐饮店简介")
	@Column(name="introduce",length=255,nullable=true,unique=false)
	private String introduce;

	@Transient
	@DColumn(index=11,label="标志",comment="标志")
	private String logo;

	@DColumn(index=12,label="配送费",comment="配送费")
	@Column(name="dispatch_price",length=255,nullable=true,unique=false)
	private Double dispatchPrice;

	@DColumn(index=13,label="餐盒费",comment="餐盒费")
	@Column(name="packet_price",length=255,nullable=true,unique=false)
	private Double packetPrice;

	@DColumn(index=14,label="主营",comment="主营")
	@Column(name="the_main",length=255,nullable=true,unique=false)
	private String theMain;

	@DColumn(index=15,label="热销",comment="热销")
	@Column(name="hot_sell",length=255,nullable=true,unique=false)
	private String hotSell;

	@DColumn(index=16,label="开业时间",comment="开业时间")
	@Column(name="opening_time",length=255,nullable=true,unique=false)
	private Date openingTime;

	@DColumn(index=17,label="商家评分",comment="商家评分")
	@Column(name="score",length=255,nullable=true,unique=false)
	private Double score;

	@DColumn(index=18,label="人均消费",comment="人均消费")
	@Column(name="per_cost",length=255,nullable=true,unique=false)
	private Double perCost;

	@DColumn(index=19,label="设施",comment="设施")
	@Column(name="facilities",length=255,nullable=true,unique=false)
	private String facilities;

	@DColumn(index=20,label="区域",comment="区域")
	@Column(name="area",length=255,nullable=true,unique=false)
	private String area;

	@DColumn(index=21,label="坐标经度",comment="坐标经度")
	@Column(name="longitude",length=255,nullable=true,unique=false)
	private Double longitude;

	@DColumn(index=22,label="坐标纬度",comment="坐标纬度")
	@Column(name="latitude",length=255,nullable=true,unique=false)
	private Double latitude;

	@DColumn(index=23,label="商户appid",comment="商户appid")
	@Column(name="alipay_appid",length=255,nullable=true,unique=false)
	private String alipayAPPID;

	@DColumn(index=24,label="支付宝公钥",comment="支付宝公钥")
	@Column(name="alipay_public_key",length=255,nullable=true,unique=false)
	private String alipayPublicKey;

	@DColumn(index=25,label="商户私钥",comment="商户私钥")
	@Column(name="rsa_private_key",length=255,nullable=true,unique=false)
	private String rsaPrivateKey;

	@DColumn(index=26,label="提交数据时关联",comment="提交数据时关联")
	@Column(name="organization_id",length=255,nullable=true,unique=false)
	private Long organizationId;

	@DColumn(index=27,label="配送时间",comment="配送时间")
	@Column(name="dispatch_time",length=20,nullable=true,unique=false)
	private String dispatchTime;

	@DColumn(index=28,label="配送服务",comment="如：提供高品质送餐服务")
	@Column(name="dispatch_service",length=255,nullable=true,unique=false)
	private String dispatchService;

	@DColumn(index=28,label="类型",comment="商家类型")
	@Column(name="member_type",length=255,nullable=true,unique=false)
	private String memberType;

	@Transient
	@DColumn(index=29,label="图片ID",comment="图片ID")
	private Long logoId;

	public Long getLogoId() {
		return logoId;
	}

	public void setLogoId(Long logoId) {
		this.logoId = logoId;
	}

	public String getOnlyCode() {
		return this.onlyCode;
	}

	public void setOnlyCode(String onlyCode) {
		this.onlyCode = onlyCode;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberCode() {
		return this.memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPerson() {
		return this.person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Double getDispatchPrice() {
		return this.dispatchPrice;
	}

	public void setDispatchPrice(Double dispatchPrice) {
		this.dispatchPrice = dispatchPrice;
	}

	public Double getPacketPrice() {
		return this.packetPrice;
	}

	public void setPacketPrice(Double packetPrice) {
		this.packetPrice = packetPrice;
	}

	public String getTheMain() {
		return this.theMain;
	}

	public void setTheMain(String theMain) {
		this.theMain = theMain;
	}

	public String getHotSell() {
		return this.hotSell;
	}

	public void setHotSell(String hotSell) {
		this.hotSell = hotSell;
	}

	public Date getOpeningTime() {
		return this.openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getPerCost() {
		return this.perCost;
	}

	public void setPerCost(Double perCost) {
		this.perCost = perCost;
	}

	public String getFacilities() {
		return this.facilities;
	}

	public void setFacilities(String facilities) {
		this.facilities = facilities;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAlipayAPPID() {
		return this.alipayAPPID;
	}

	public void setAlipayAPPID(String alipayAPPID) {
		this.alipayAPPID = alipayAPPID;
	}

	public String getAlipayPublicKey() {
		return this.alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getRsaPrivateKey() {
		return this.rsaPrivateKey;
	}

	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	public Long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getDispatchTime() {
		return dispatchTime;
	}

	public void setDispatchTime(String dispatchTime) {
		this.dispatchTime = dispatchTime;
	}

	public String getDispatchService() {
		return dispatchService;
	}

	public void setDispatchService(String dispatchService) {
		this.dispatchService = dispatchService;
	}


}