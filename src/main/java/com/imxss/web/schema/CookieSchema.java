package com.imxss.web.schema;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.coody.framework.context.base.BaseModel;
import org.coody.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class CookieSchema extends BaseModel{
	
	public CookieSchema(String domain,String cookieLine) {
		if(!cookieLine.contains("=")) {
			return;
		}
		cookieLine=cookieLine.trim();
		String cookieName=cookieLine.substring(0,cookieLine.indexOf("="));
		String cookieValue=cookieLine.substring(cookieLine.indexOf("=")+1);
		this.name=cookieName;
		this.value=cookieValue;
		this.domain=domain;
	}

	private String domain;
	
	private Boolean hostOnly=false;
	
	private Boolean httpOnly=false;
	
	private String name;
	
	private String path="/";
	
	private String sameSite="no_restriction";
	
	private Boolean secure=false;
	
	private Boolean session=true;
	
	private String storeId="0";
	
	private String value;
	
	private Integer id;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Boolean getHostOnly() {
		return hostOnly;
	}

	public void setHostOnly(Boolean hostOnly) {
		this.hostOnly = hostOnly;
	}

	public Boolean getHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(Boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSameSite() {
		return sameSite;
	}

	public void setSameSite(String sameSite) {
		this.sameSite = sameSite;
	}

	public Boolean getSecure() {
		return secure;
	}

	public void setSecure(Boolean secure) {
		this.secure = secure;
	}

	public Boolean getSession() {
		return session;
	}

	public void setSession(Boolean session) {
		this.session = session;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public static String buildCookies(String url,String cookie) throws MalformedURLException {
		if(StringUtil.isNullOrEmpty(cookie)) {
			return null;
		}
		URL uri=new URL(url);
		List<CookieSchema> schemas=new ArrayList<>();
		String[] lines=cookie.split(";");
		for(String line:lines) {
			CookieSchema schema=new CookieSchema(uri.getHost(), line);
			schema.setId(schemas.size()+1);
			schemas.add(schema);
		}
		return JSON.toJSONString(schemas);
	}
}
