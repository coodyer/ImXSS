package com.imxss.web.schema;

import com.imxss.web.domain.LetterInfo;

@SuppressWarnings("serial")
public class LetterSchema extends LetterInfo{

	private String projectName;
	
	private String ipInfo;
	
	private String moduleName;
	
	private String userEmail;
	
	

	
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getIpInfo() {
		return ipInfo;
	}

	public void setIpInfo(String ipInfo) {
		this.ipInfo = ipInfo;
	}
	
	
}
