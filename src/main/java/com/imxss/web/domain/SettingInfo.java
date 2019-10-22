package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;

/**
 * 设置 Setting entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class SettingInfo extends BaseModel {

	// Fields

	private Integer id;
	private String siteName;
	private String keywords;
	private String description;
	private String copyright;
	private Integer openReg;
	private Integer needInvite;
	
	
	
	public Integer getOpenReg() {
		return openReg;
	}
	public void setOpenReg(Integer openReg) {
		this.openReg = openReg;
	}
	public Integer getNeedInvite() {
		return needInvite;
	}
	public void setNeedInvite(Integer needInvite) {
		this.needInvite = needInvite;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public SettingInfo(Integer id, String siteName, String keywords,
			String description, String copyright) {
		super();
		this.id = id;
		this.siteName = siteName;
		this.keywords = keywords;
		this.description = description;
		this.copyright = copyright;
	}
	public SettingInfo(){
	}

}