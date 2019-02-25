package com.cisdi.info.simple.entity.verification;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import com.cisdi.info.simple.entity.permission.Operator;

@DEntity(label="登录验证",comment="",moduleLabel="登录验证")
@Entity(name="simple_validate_logon")
public class ValidateLogon extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="ip地址",comment="ip地址")
	@Column(name="ip_address",length=20,nullable=false,unique=true)
	private String ipAddress;

	@DColumn(index=4,label="次数",comment="登录次数")
	@Column(name="count",length=255,nullable=true,unique=false)
	private Integer count;

	@DColumn(index=5,label="操作员",foreignEntity="Operator",comment="当前IP地址登录成功的操作员")
	@Column(name="operator_id",length=255,nullable=true,unique=false)
	private Long operatorId;

	@Transient
	private Operator operator;

	@Transient
	@DColumn(index=5,label="操作员",foreignEntity="Operator",comment="当前IP地址登录成功的操作员")
	private String operatorName;


	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		if(operator == null){
			return;
		}
		this.operatorId = operator.getEId();
		this.operator = operator;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	
}