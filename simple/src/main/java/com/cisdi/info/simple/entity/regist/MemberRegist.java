package com.cisdi.info.simple.entity.regist;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;


@DEntity(label="商家注册审核",comment="",moduleLabel="商家注册审核")
@Entity(name="simple_member_regist")
public class MemberRegist extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="唯一键",comment="唯一键")
	@Column(name="only_code",length=255,nullable=true,unique=false)
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

	@DColumn(index=10,label="审核状态",comment="审核状态")
	@Column(name="audit_state",length=255,nullable=true,unique=false)
	private String auditState;


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

	public String getAuditState() {
		return this.auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}



}