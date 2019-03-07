package com.cisdi.info.simple.entity.log;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.permission.Operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@DEntity(label="日志",comment="",moduleLabel="日志")
@Entity(name="simple_log")
public class Log extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="操作员",foreignEntity="Operator",comment="操作员")
	@Column(name="operator_id",length=255,nullable=false,unique=false)
	private Long operatorId;

	@Transient
	private Operator operator;

	@Transient
	@DColumn(index=3,label="操作员",foreignEntity="Operator",comment="操作员")
	private String operatorName;

	@DColumn(index=4,label="IP地址",comment="IP地址")
	@Column(name="ip_address",length=255,nullable=true,unique=false)
	private String ipAddress;

	@DColumn(index=5,label="日志类型",comment="日志类型")
	@Column(name="log_type",length=255,nullable=true,unique=false)
	private String logType;

	@DColumn(index=6,label="操作实体",comment="操作实体")
	@Column(name="entity",length=255,nullable=true,unique=false)
	private String entity;

	@DColumn(index=7,label="操作类型",comment="操作类型")
	@Column(name="operation_type",length=255,nullable=true,unique=false)
	private String operationType;

	@DColumn(index=8,label="操作时间",comment="操作时间")
	@Column(name="log_date",length=255,nullable=true,unique=false)
	private Date logDate;

	@DColumn(index=9,label="操作内容",comment="操作内容")
	@Column(name="operation_content",length=255,nullable=true,unique=false)
	private String operationContent;

	@DColumn(index=10,label="url",comment="url")
	@Column(name="url",length=255,nullable=true,unique=false)
	private String url;

	@DColumn(index=11,label="模块",comment="模块")
	@Column(name="module",length=255,nullable=true,unique=false)
	private String module;


	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getOperationContent() {
		return this.operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
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