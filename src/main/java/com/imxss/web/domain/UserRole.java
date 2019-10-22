package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;

/**
 * MemberRole entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class UserRole extends BaseModel{

	// Fields

	private Integer id;
	private String name;
	private String menus;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(String name, String menus) {
		this.name = name;
		this.menus = menus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenus() {
		return this.menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

}