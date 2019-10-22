package org.coody.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.HttpEntity;

public class HttpHandle {

	
	public final static String POST="POST";
	public final static String GET="GET";
	public final static String HEAD="HEAD";
	public final static String PUT="PUT";
	public final static String CONNECT="CONNECT";
	public final static String OPTIONS="OPTIONS";
	public final static String DELETE="DELETE";
	public final static String PATCH="PATCH";
	public final static String PROPFIND="PROPFIND";
	public final static String PROPPATCH="PROPPATCH";
	public final static String MKCOL="MKCOL";
	public final static String COPY="COPY";
	public final static String MOVE="MOVE";
	public final static String LOCK="LOCK";
	public final static String UNLOCK ="UNLOCK";
	public final static String TRACE="TRACE";
	
	public final static String HTTP_GENERAL="HTTP_GENERAL";
	
	public final static String HTTP_JSON="HTTP_JSON";
	
	public HttpConfig config=new HttpConfig();
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(HttpHandle.class);

	
	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}
	public static class HttpConfig{
		
		private boolean allowRedirects=true;
		
		private String cookie="";
		
		private String encode="UTF-8";
		
		private int timeOut=15;
		
		private String httpModule=HTTP_GENERAL;
		
		private Map<String, String> headerMap=new HashMap<String, String>();
		
		
		public void setEncode(String encode) {
			this.encode = encode;
		}


		public void setTimeOut(int timeOut) {
			this.timeOut = timeOut;
		}


		public void setCookie(String cookie) {
			this.cookie = cookie;
		}


		public void setHeaderMap(Map<String, String> headerMap) {
			this.headerMap = headerMap;
		}

		//设置Header头部
		public void setRequestProperty(String fieldName,String value){
			headerMap.put(fieldName, value);
		}
		//是否开启Gzip
		public void setGzip(boolean isGzip){
			if(isGzip){
				headerMap.put("Accept-Encoding", "gzip, deflate, sdch");
				return;
			}
			headerMap.put("Accept-Encoding", "*");
		}
		//是否保持连接
		public void setKeepAlive(boolean keepAlive){
			if(keepAlive){
				headerMap.put("Connection", "keep-alive");
				return;
			}
			headerMap.put("Connection", "close");
		}
		
		//是否允许重定向
		public void allowRedirects(boolean allowRedirects){
			this.allowRedirects=allowRedirects;
		}
	}
	
	private HttpURLConnection createConnectionGeneral(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.addRequestProperty("Referer", getDomain(url));
			conn.addRequestProperty(
					"Accept",
					"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.addRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			conn.addRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			return conn;
		} catch (Exception e) {
			return null;
		}
	}
	
	private HttpURLConnection createConnectionJson(String url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			conn.addRequestProperty("Referer", getDomain(url));
			conn.addRequestProperty(
					"Accept",
					"image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
			conn.addRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			conn.addRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			return conn;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		}
	}
	
	
	


	
	//获取默认来源地址
	public static String getDomain(String urlStr){
		try {
			URI uri=new URI(urlStr);
			String result=uri.getScheme()+"://"+uri.getHost();
			if(uri.getPort()>0&&uri.getPort()!=80){
				result+=("/"+uri.getPort());
			}
			if(!result.endsWith("/")){
				result+="/";
			}
			return result;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		}
		
	}
	//合并Cookie
	private static String mergeCookie(String oldCookie, String newCookie) {
		if (newCookie == null) {
			return oldCookie;
		}
		Map<String, String> cookieMap = new HashMap<String, String>();
		String[] cookTmp = null;
		String[] cookieTab = null;
		StringBuilder valueTmp = new StringBuilder();
		String[] cookies = { oldCookie, newCookie };
		for (String currCookie : cookies) {
			if (StringUtil.isNullOrEmpty(currCookie)) {
				continue;
			}
			cookieTab = currCookie.split(";");
			for (String cook : cookieTab) {
				cookTmp = cook.split("=");
				if (cookTmp.length < 2) {
					continue;
				}
				valueTmp = new StringBuilder();
				for (int i = 1; i < cookTmp.length; i++) {
					valueTmp.append(cookTmp[i]);
					if (i < cookTmp.length - 1) {
						valueTmp.append("=");
					}
				}
				if (StringUtil.findNull(cookTmp[0], valueTmp) > -1) {
					continue;
				}
				cookieMap.put(cookTmp[0], valueTmp.toString());
			}
		}
		valueTmp = new StringBuilder();
		for (String key : cookieMap.keySet()) {
			valueTmp.append(key).append("=").append(cookieMap.get(key));
			valueTmp.append(";");
		}
		return valueTmp.toString();
	}
	
	private HttpURLConnection getConnection(String url) {
		if(config.httpModule.equals(HTTP_GENERAL)){
			return createConnectionGeneral(url);
		}
		if(config.httpModule.equals(HTTP_JSON)){
			return createConnectionJson(url);
		}
		return null;
	}
	
	public HttpEntity Get(String url){
			return Conn(url, GET, null);
	}
	
	public HttpEntity Post(String url,String data){
		return Conn(url, POST, data);
	}
	
	public HttpEntity Conn(String url, String method,
			String postData){
		if(url.contains(" ")){
			url=url.replace(" ", "%20");
		}
		HttpURLConnection conn = getConnection(url);
		if (conn == null) {
			return null;
		}
		if (!StringUtil.isNullOrEmpty(config.headerMap)) {
			for (String key : config.headerMap.keySet()) {
				logger.debug("设置Head:"+key+"="+config.headerMap.get(key));
				conn.setRequestProperty(key, config.headerMap.get(key));
				key = null;
			}
		}
		if(!config.allowRedirects){
			logger.debug("禁止重定向");
			conn.setInstanceFollowRedirects(false);
		}
		if (!StringUtil.isNullOrEmpty(config.cookie)) {
			logger.debug("设置Cookie:"+config.cookie);
			conn.setRequestProperty("Cookie", config.cookie);
		}
		try {
			conn.setRequestMethod(method);
			if (method.equalsIgnoreCase(POST)||method.equalsIgnoreCase(PUT)) {
				logger.debug("发送内容:"+postData);
				conn.setDoOutput(true);
				byte [] postByte=postData.getBytes(config.encode);
				conn.setRequestProperty("Content-Length", String.valueOf(postByte.length));
				conn.getOutputStream().write(postByte);
				conn.connect();
				conn.getOutputStream().flush();
				conn.getOutputStream().close();
			}
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		conn.setConnectTimeout(config.timeOut*1000);
		InputStream ins = null;
		HttpEntity hEntity = new HttpEntity();
		String key = "";
		StringBuilder cookie = new StringBuilder();
		try {
			Integer status=conn.getResponseCode();
			logger.debug("响应码:"+status);
			if (status !=HttpURLConnection.HTTP_OK) {
				ins=conn.getErrorStream();
			}else{
				ins=conn.getInputStream();
			}
			hEntity.setCode(conn.getResponseCode());
			Map<String,String> headMap=new HashMap<String, String>();
			for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
				headMap.put(key, conn.getHeaderField(key));
				if (key.equalsIgnoreCase("set-cookie")) {
					try {
						cookie.append(conn.getHeaderField(i).replace("/", ""));
					} catch (Exception e) {
					}
				}
			}
			config.cookie = mergeCookie(config.cookie, cookie.toString());
			byte[] b = toByte(ins);
			if((headMap.get("Content-Encoding")!=null && headMap.get("Content-Encoding").contains("gzip"))||(conn.getRequestProperty("Accept-Encoding")!=null&&conn.getRequestProperty("Accept-Encoding").contains("gzip"))){
				b = GZIPUtils.uncompress(b);
			}
			hEntity.setEncode(config.encode);
			hEntity.setBye(b);
			hEntity.setCookie(config.cookie);
			hEntity.setHeadMap(headMap);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ins.close();
			} catch (Exception e2) {
			}
		}
		return hEntity;
	}
	
	
	private byte[] toByte(InputStream ins) {
		if(ins==null){
			return null;
		}
		ByteArrayOutputStream swapStream = null;
		try {
			swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[1024];
			int rc = 0;
			while ((rc = ins.read(buff, 0, 1024)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			return swapStream.toByteArray();
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		} finally {
			try {
				swapStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}
