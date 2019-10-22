package com.imxss.web.domain;

import java.util.Date;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class EmailQueue extends BaseModel {
	
	private Integer id;
	private String unionId;
	private String title;
	private String context;
	private String sendEmail;
	private String targeEmail;
	private Integer status=0;
	private Date createTime=new Date();
	private Long millisecond=System.currentTimeMillis();
	private Date updateTime=new Date();
	
	
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public Long getMillisecond() {
		return millisecond;
	}

	public void setMillisecond(Long millisecond) {
		this.millisecond = millisecond;
	}

	public String getTargeEmail() {
		return targeEmail;
	}

	public void setTargeEmail(String targeEmail) {
		this.targeEmail = targeEmail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}