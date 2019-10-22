package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;

/**
 * 后缀 Suffix entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SuffixInfo extends BaseModel {

	// Fields

	private String suffix;
	private Integer status;

	// Constructors

	/** default constructor */
	public SuffixInfo() {
	}

	/** full constructor */
	public SuffixInfo(String suffix, Integer status) {
		this.suffix = suffix;
		this.status = status;
	}


	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}