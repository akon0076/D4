package com.cisdi.info.simple.entity.clue;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;


@DEntity(label="基础信息",comment="",moduleLabel="线索管理")
@Entity(name="simple_basic_message")
public class BasicMessage extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="线索唯一标识",comment="线索唯一标识")
	@Column(name="clue_key",length=255,nullable=false,unique=false)
	private String clueKey;

	@DColumn(index=4,label="线索编号",comment="线索编号")
	@Column(name="clue_id",length=255,nullable=false,unique=false)
	private String clueId;

	@DColumn(index=5,label="线索级别",comment="线索级别")
	@Column(name="clue_level",length=255,nullable=true,unique=false)
	private String clueLevel;

	@DColumn(index=6,label="线索来源",comment="线索来源")
	@Column(name="clue_source",length=255,nullable=true,unique=false)
	private String clueSource;

	@DColumn(index=7,label="活动名称",comment="活动名称")
	@Column(name="activity_name",length=255,nullable=true,unique=false)
	private String activityName;

	@DColumn(index=8,label="所属分局",comment="所属分局")
	@Column(name="subord_branch",length=255,nullable=true,unique=false)
	private String subordBranch;

	@DColumn(index=9,label="线索专员",comment="线索专员")
	@Column(name="clue_attache",length=255,nullable=true,unique=false)
	private String clueAttache;

	@DColumn(index=10,label="分管领导",comment="分管领导")
	@Column(name="charge_leaders",length=255,nullable=true,unique=false)
	private String chargeLeaders;

	@DColumn(index=11,label="对接要求",comment="对接要求")
	@Column(name="dock_require",length=255,nullable=true,unique=false)
	private String dockRequire;

	@DColumn(index=12,label="备注",comment="备注")
	@Column(name="comment",length=255,nullable=true,unique=false)
	private String comment;

	@DColumn(index=13,label="状态",comment="状态")
	@Column(name="state",length=255,nullable=true,unique=false)
	private String state;


	public String getClueKey() {
		return this.clueKey;
	}

	public void setClueKey(String clueKey) {
		this.clueKey = clueKey;
	}

	public String getClueId() {
		return this.clueId;
	}

	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	public String getClueLevel() {
		return this.clueLevel;
	}

	public void setClueLevel(String clueLevel) {
		this.clueLevel = clueLevel;
	}

	public String getClueSource() {
		return this.clueSource;
	}

	public void setClueSource(String clueSource) {
		this.clueSource = clueSource;
	}

	public String getActivityName() {
		return this.activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getSubordBranch() {
		return this.subordBranch;
	}

	public void setSubordBranch(String subordBranch) {
		this.subordBranch = subordBranch;
	}

	public String getClueAttache() {
		return this.clueAttache;
	}

	public void setClueAttache(String clueAttache) {
		this.clueAttache = clueAttache;
	}

	public String getChargeLeaders() {
		return this.chargeLeaders;
	}

	public void setChargeLeaders(String chargeLeaders) {
		this.chargeLeaders = chargeLeaders;
	}

	public String getDockRequire() {
		return this.dockRequire;
	}

	public void setDockRequire(String dockRequire) {
		this.dockRequire = dockRequire;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}


	
}