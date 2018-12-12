package com.cisdi.info.simple.entity.permission;

import com.cisdi.info.simple.entity.base.*;

import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;
import com.cisdi.info.simple.entity.organization.Organization;

import javax.persistence.Column;
import javax.persistence.Entity;

@DEntity(label="操作员",comment="",moduleLabel="授权")
@Entity(name="simple_operator")
public class Operator extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="编码",comment="编码")
	@Column(name="code",length=200,nullable=false,unique=true)
	private String code;

	@DColumn(index=4,label="密码",comment="密码")
	@Column(name="pass_word",length=200,nullable=false,unique=false)
	private String passWord;

	@DColumn(index=5,label="状态",codeTable="OperatorStatus",comment="状态")
	@Column(name="status",length=250,nullable=true,unique=false)
	private String status;

	@DColumn(index=6,label="类型",codeTable="OperatorType",comment="与此操作员相应人员类型")
	@Column(name="type",length=250,nullable=true,unique=false)
	private String type;

	@DColumn(index=7,label="人员ID",comment="与操作员相应的人员ID，此字段和type直接相关")
	@Column(name="person_id",length=250,nullable=true,unique=false)
	private Long personId;

	@DColumn(index=9,label="邮箱",comment="邮箱")
	@Column(name="email",length=100,nullable=true,unique=false)
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassWord() {
		return this.passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getPersonId() {
		return this.personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}


	
}