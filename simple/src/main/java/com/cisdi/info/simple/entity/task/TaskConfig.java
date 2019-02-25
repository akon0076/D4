package com.cisdi.info.simple.entity.task;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;

@DEntity(label="定时任务",comment="",moduleLabel="定时任务配置管理")
@Entity(name="simple_task_config")
public class TaskConfig extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="任务名称",comment="任务编码")
	@Column(name="task_name",length=255,nullable=true,unique=false)
	private String taskName;

	@DColumn(index=4,label="任务类型",comment="表达式")
	@Column(name="task_type",length=255,nullable=true,unique=false)
	private String taskType;

	@DColumn(index=5,label="创建时间",comment="创建时间")
	@Column(name="create_time",length=255,nullable=true,unique=false)
	private Date createTime;

	@DColumn(index=6,label="是否需要参数",comment="是否需要参数")
	@Column(name="is_need_variable",length=255,nullable=true,unique=false)
	private Integer isNeedVariable;


	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsNeedVariable() {
		return this.isNeedVariable;
	}

	public void setIsNeedVariable(Integer isNeedVariable) {
		this.isNeedVariable = isNeedVariable;
	}


	
}