package com.cisdi.info.simple.entity.qingTui;

import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;
import com.cisdi.info.simple.entity.base.BaseEntity;
import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.permission.Operator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@DEntity(label="人员与应用",comment="",moduleLabel="人员与应用")
@Entity(name="simple_emp_open_id")
public class EmpOpenId extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="人员",foreignEntity="Employee",comment="人员")
	@Column(name="employee_id",length=255,nullable=true,unique=false)
	private Long employeeId;

	@Transient
	private Employee employee;

	@Transient
	@DColumn(index=3,label="人员",foreignEntity="Employee",comment="人员")
	private String employeeName;

	@DColumn(index=4,label="openId",comment="openId")
	@Column(name="open_id",length=200,nullable=true,unique=false)
	private String openId;

	@DColumn(index=5,label="uId",comment="uId")
	@Column(name="u_id",length=200,nullable=true,unique=false)
	private String uId;

	@DColumn(index=6,label="操作员",foreignEntity="Operator",comment="操作员")
	@Column(name="operator_id",length=255,nullable=true,unique=false)
	private Long operatorId;

	@Transient
	private Operator operator;

	@Transient
	@DColumn(index=6,label="操作员",foreignEntity="Operator",comment="操作员")
	private String operatorName;

	@DColumn(index=7,label="平台类型",comment="平台类型：轻推、微信")
	@Column(name="type",length=200,nullable=true,unique=false)
	private String type;

	@DColumn(index=8,label="用户类型",comment="用户类型")
	@Column(name="user_type",length=200,nullable=true,unique=false)
	private String userType;

	@DColumn(index=9,label="商家",foreignEntity="Member",comment="商家")
	@Column(name="member_id",length=255,nullable=true,unique=false)
	private Long memberId;

	@Transient
	private Member member;

	@Transient
	@DColumn(index=9,label="商家",foreignEntity="Member",comment="商家")
	private String memberName;


	public Long getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUId() {
		return this.uId;
	}

	public void setUId(String uId) {
		this.uId = uId;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		if(employee == null){
			return;
		}
		this.employeeId = employee.getEId();
		this.employee = employee;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		if(member == null){
			return;
		}
		this.memberId = member.getEId();
		this.member = member;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	
}