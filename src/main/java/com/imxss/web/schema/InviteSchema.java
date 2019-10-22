package com.imxss.web.schema;

import com.imxss.web.domain.InviteInfo;

@SuppressWarnings("serial")
public class InviteSchema extends InviteInfo{

	private String regUserEmail;

	public String getRegUserEmail() {
		return regUserEmail;
	}

	public void setRegUserEmail(String regUserEmail) {
		this.regUserEmail = regUserEmail;
	}
	
	
}
