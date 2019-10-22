package com.imxss.web.domain;

import org.coody.framework.context.base.BaseModel;
import org.coody.framework.util.StringUtil;

@SuppressWarnings("serial")
public class AddressInfo extends BaseModel {

	private String ip;

	private String country;

	private String area;

	private String region;

	private String city;

	private String isp;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

	public AddressInfo() {

	}

	public String toString() {
		if(StringUtil.isNullOrEmpty(region)&&StringUtil.isNullOrEmpty(city)){
			return country;
		}
		String data = region + " " + city + " " + isp;
		return data;
	}

}