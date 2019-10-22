package com.imxss.web.schema;

import java.util.List;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class MenuSchema extends BaseModel{

	private Integer id;
	private Integer upId;
	private String title;
	private String url;
	private Integer type;
	private Integer seq;
	private String code;
	private String remark;
	private List<MenuSchema> childMenus;
	private String groupName;
	
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUpId() {
		return upId;
	}
	public void setUpId(Integer upId) {
		this.upId = upId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<MenuSchema> getChildMenus() {
		return childMenus;
	}
	public void setChildMenus(List<MenuSchema> childMenus) {
		this.childMenus = childMenus;
	}
	
	
	
	
}
