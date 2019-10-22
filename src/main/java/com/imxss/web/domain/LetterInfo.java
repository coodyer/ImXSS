package com.imxss.web.domain;

import java.util.Date;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class LetterInfo extends BaseModel{

	
	private Integer id;
	private Integer projectId;
	private String refUrl;
	private Date updateTime;
	private String unionId;
	private String ip;
	private Integer userId;
	private Integer isReaded;
	private Integer moduleId;

	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(Integer isReaded) {
		this.isReaded = isReaded;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getRefUrl() {
		return refUrl;
	}
	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	
}
