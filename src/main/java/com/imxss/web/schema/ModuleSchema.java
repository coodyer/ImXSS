package com.imxss.web.schema;

import com.imxss.web.domain.ModuleInfo;

@SuppressWarnings("serial")
public class ModuleSchema extends ModuleInfo{

	private String userEmail;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
