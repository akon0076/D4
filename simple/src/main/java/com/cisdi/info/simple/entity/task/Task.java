package com.cisdi.info.simple.entity.task;

import com.cisdi.info.simple.entity.base.BaseEntity;
import javax.persistence.*;
import java.io.Serializable;
import com.cisdi.info.simple.annotation.DColumn;
import com.cisdi.info.simple.annotation.DEntity;

import java.util.Date;

@DEntity(label="定时任务",comment="",moduleLabel="定时任务管理")
@Entity(name="simple_task")
public class Task extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="任务编码",comment="任务编码")
	@Column(name="task_id",length=255,nullable=true,unique=false)
	private String taskId;

	@DColumn(index=4,label="表达式",comment="表达式")
	@Column(name="task_expression",length=255,nullable=true,unique=false)
	private String taskExpression;

	@DColumn(index=5,label="发布人",comment="发布人")
	@Column(name="task_publisher",length=255,nullable=true,unique=false)
	private String taskPublisher;

	@DColumn(index=6,label="创建时间",comment="创建时间")
	@Column(name="task_create_time",length=255,nullable=true,unique=false)
	private Date taskCreateTime;

	@DColumn(index=7,label="完成时间",comment="完成时间")
	@Column(name="task_complete_time",length=255,nullable=true,unique=false)
	private Date taskCompleteTime;

	@DColumn(index=8,label="任务状态",comment="任务状态")
	@Column(name="task_status",length=255,nullable=true,unique=false)
	private String taskStatus;

	@DColumn(index=9,label="执行任务类",comment="执行任务类")
	@Column(name="task_class",length=255,nullable=true,unique=false)
	private String taskClass;

	@DColumn(index=10,label="执行任务变量",comment="执行任务变量")
	@Column(name="task_variable",length=255,nullable=true,unique=false)
	private String taskVariable;

	@DColumn(index=11,label="任务配置ID",comment="任务配置ID")
	@Column(name="task_config_id",length=255,nullable=true,unique=false)
	private Integer taskConfigId;

	@DColumn(index=12,label="任务组",comment="任务组")
	@Column(name="task_group",length=255,nullable=true,unique=false)
	private String taskGroup;

	@DColumn(index=13,label="任务名称",comment="任务名称")
	@Column(name="task_name",length=255,nullable=true,unique=false)
	private String taskName;


	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskExpression() {
		return this.taskExpression;
	}

	public void setTaskExpression(String taskExpression) {
		this.taskExpression = taskExpression;
	}

	public String getTaskPublisher() {
		return this.taskPublisher;
	}

	public void setTaskPublisher(String taskPublisher) {
		this.taskPublisher = taskPublisher;
	}

	public Date getTaskCreateTime() {
		return this.taskCreateTime;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public Date getTaskCompleteTime() {
		return this.taskCompleteTime;
	}

	public void setTaskCompleteTime(Date taskCompleteTime) {
		this.taskCompleteTime = taskCompleteTime;
	}

	public String getTaskStatus() {
		return this.taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskClass() {
		return this.taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getTaskVariable() {
		return this.taskVariable;
	}

	public void setTaskVariable(String taskVariable) {
		this.taskVariable = taskVariable;
	}

	public Integer getTaskConfigId() {
		return this.taskConfigId;
	}

	public void setTaskConfigId(Integer taskConfigId) {
		this.taskConfigId = taskConfigId;
	}

	public String getTaskGroup() {
		return this.taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}



}