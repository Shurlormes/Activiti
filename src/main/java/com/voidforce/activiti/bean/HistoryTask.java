package com.voidforce.activiti.bean;

import java.util.Date;

public class HistoryTask {
	private String id;

	private String name;

	private String actProcInstId;

	private String actReProcdefId;

	private Date startTime;

	private Date endTime;

	private String deleteReason;

	private String comment;

	private Integer status;

	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActProcInstId() {
		return actProcInstId;
	}

	public void setActProcInstId(String actProcInstId) {
		this.actProcInstId = actProcInstId;
	}

	public String getActReProcdefId() {
		return actReProcdefId;
	}

	public void setActReProcdefId(String actReProcdefId) {
		this.actReProcdefId = actReProcdefId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
