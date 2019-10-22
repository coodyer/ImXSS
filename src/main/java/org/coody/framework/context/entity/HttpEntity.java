package org.coody.framework.context.entity;

import java.util.HashMap;
import java.util.Map;

import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.util.PrintException;

public class HttpEntity {
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(HttpEntity.class);
	
	
	private String html;
	private byte[] bye;
	private String cookie;
	private Integer code=-1;
	private Map<String,String> headMap;
	
	public Map<String, String> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;	
	}

	private String encode="UTF-8";
	
	public String getHtml() {
		try {
			if(html!=null){
				return html;
			}
		if(bye==null){
			return null;
		}
			String str= new String(bye, encode);
			html=str;
			return str;
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		return null;
	}
	

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public byte[] getBye() {
		return bye;
	}

	public void setBye(byte[] bye) {
		this.bye = bye;
	}

	public Map<String, String> getCookieMap() {
		if (cookie == null) {
			return null;
		}
		Map<String, String> cookieMap = new HashMap<String, String>();
		String[] cookies = cookie.split(";");
		for (String cook : cookies) {
			String[] tmps = cook.split("=");
			if (tmps.length >= 2) {
				String cookieValue = "";
				for (int i = 1; i < tmps.length; i++) {
					cookieValue += tmps[i];
					if (i < tmps.length-1) {
						cookieValue += "=";
					}
				}
				cookieMap.put(tmps[0].trim(), cookieValue.trim());
			}
		}
		return cookieMap;
	}
}
