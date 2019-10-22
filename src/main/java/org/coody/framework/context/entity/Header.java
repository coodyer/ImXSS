package org.coody.framework.context.entity;

import org.coody.framework.constant.ParaCheckFinal;
import org.coody.framework.context.annotation.ParamCheck;
import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class Header extends BaseModel{
	//命令
	@ParamCheck
	private String cmd;
	//动作
	@ParamCheck
	private String action;
	//0代表安卓，1代表IOS
	@ParamCheck
	private Integer clientType;
	//系统类型
	@ParamCheck
	private String osVersion;
	//客户端别名,如:iphone 8s
	@ParamCheck
	private String clientAlias;
	//手机唯一标识
	@ParamCheck
	private String imei;
	//版本号
	@ParamCheck(format=ParaCheckFinal.NUMBER)
	private Integer clientVersion;
	//用户ID
	@ParamCheck(allowNull=true,format=ParaCheckFinal.NUMBER)
	private Long userId;
	//登录授权key
	private String token;
	//IP地址
	@ParamCheck
	private String ipAddress;
	//渠道
	@ParamCheck
	private String channelId;
	//IM驱动号
	private String imDeviceToken;
	//域名地址
	private String domain;
	


	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getImDeviceToken() {
		return imDeviceToken;
	}

	public void setImDeviceToken(String imDeviceToken) {
		this.imDeviceToken = imDeviceToken;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getClientAlias() {
		return clientAlias;
	}

	public void setClientAlias(String clientAlias) {
		this.clientAlias = clientAlias;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public Integer getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(Integer clientVersion) {
		this.clientVersion = clientVersion;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
