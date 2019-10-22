package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class EmailSendCensus extends BaseModel{

	private Integer userId;
	
	private Integer num;
	
	private String day;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
	
}
