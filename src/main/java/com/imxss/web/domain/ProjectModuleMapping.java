package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class ProjectModuleMapping extends BaseModel{

	private Integer projectId;
	private Integer moduleId;
	private String mapping;
	private Integer userId;
	private String id;
	/**
	 * 1根据来源地址 2根据IP地址
	 */
	private Integer type;
	
	
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getMapping() {
		return mapping;
	}
	public void setMapping(String mapping) {
		this.mapping = mapping;
	}
	

}
