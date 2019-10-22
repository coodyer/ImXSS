<%@page import="com.alibaba.fastjson.JSON"%>
<%@page import="org.coody.framework.context.entity.MsgEntity"%>
<%@page import="org.coody.framework.util.RequestUtil"%>
<%@page import="com.imxss.web.install.InstallHandle"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	if(InstallHandle.isInstall()){
		return;
	}
	response.setContentType("text/json");
	InstallHandle.InstallConfig installConfig=RequestUtil.getBeanAll(request, "", InstallHandle.InstallConfig.class);
	MsgEntity msg=InstallHandle.install(installConfig);
	out.print(JSON.toJSONString(msg));
 %>