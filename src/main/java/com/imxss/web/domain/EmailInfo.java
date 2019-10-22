/**
 * 
 */
package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;
import org.springframework.stereotype.Service;

/**
 * @author coody
 * @date 2017年7月11日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
@SuppressWarnings("serial")
@Service
public class EmailInfo extends BaseModel{

	private Integer id;
	private String smtp;
	private String email;
	private String password;
	private Integer sendNum;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSmtp() {
		return smtp;
	}
	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSendNum() {
		return sendNum;
	}
	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}
	
}
