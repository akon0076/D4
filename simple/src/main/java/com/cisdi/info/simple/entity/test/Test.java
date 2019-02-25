package com.cisdi.info.simple.entity.test;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;

@DEntity(label="测试管理",comment="",moduleLabel="测试管理")
@Entity(name="simple_test")
public class Test extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="测试名称",comment="测试名称")
	@Column(name="test_name",length=255,nullable=false,unique=false)
	private String testName;

	@DColumn(index=4,label="测试id",comment="测试id")
	@Column(name="test_id",length=255,nullable=false,unique=false)
	private Long testID;

	@DColumn(index=5,label="测试日期",comment="测试日期")
	@Column(name="test_date",length=255,nullable=true,unique=false)
	private Date testDate;


	public String getTestName() {
		return this.testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Long getTestID() {
		return this.testID;
	}

	public void setTestID(Long testID) {
		this.testID = testID;
	}

	public Date getTestDate() {
		return this.testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}


	
}