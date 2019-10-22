package com.imxss.web.schema;

import com.imxss.web.domain.ProjectInfo;

@SuppressWarnings("serial")
public class ProjectSchema extends ProjectInfo {

	private String moduleName;

	private Integer letterNum;
	
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

	public Integer getLetterNum() {
		return letterNum;
	}

	public void setLetterNum(Integer letterNum) {
		this.letterNum = letterNum;
	}


}
