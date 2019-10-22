package com.imxss.web.service;

import java.net.InetAddress;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.HttpEntity;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.coody.framework.core.thread.IpSearchThreadHandle;
import org.coody.framework.util.HttpUtil;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.SpringContextHelper;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.AddressInfo;

@Service
public class IpService {
	
	@Resource
	JdbcHandle jdbcHandle;
	
	private static final BaseLogger logger = BaseLogger.getLoggerPro(IpService.class);

	@CacheWrite(key=CacheFinal.SHELL_IP_CACHE ,time=60*60*24*30,fields="url")
	public  String getIp(String url){
		try {
			URI uri = new URI(url);
			String domain=uri.getHost();
			InetAddress address = InetAddress.getByName(domain);
			return address.getHostAddress().toString();
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
		return null;
	}
	
	@CacheWrite(key=CacheFinal.IP_INFO,time=7200000,fields="ip")
	public AddressInfo loadIpInfo(String ip){
		try {
			AddressInfo info=jdbcHandle.findBeanFirst(AddressInfo.class,"ip",ip);
			if(info!=null&&!StringUtil.isNullOrEmpty(info.getCountry())){
				return info;
			}
			IpSearchThreadHandle.ipThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					IpService ipService=SpringContextHelper.getBean(IpService.class);
					ipService.loadAddress(ip);
				}
			});
			return info;
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return null;
		}
	}
	@CacheWipe(key=CacheFinal.IP_INFO,fields="ip")
	public void  loadAddress(String ip){
		try {
			AddressInfo info=new AddressInfo();
			info.setIp(ip);
			jdbcHandle.insert(info);
			String url="http://ip.taobao.com/service/getIpInfo.php?ip={0}";
			url=MessageFormat.format(url, ip);
			HttpEntity entity=HttpUtil.Get(url);
			Map<String, Object> jsonMap = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			String dataJson=jsonMap.get("data").toString();
			info=JSON.parseObject(dataJson, AddressInfo.class);
			jdbcHandle.saveOrUpdateAuto(info);
		} catch (Exception e) {
			PrintException.printException(logger, e);
			return;
		}
		
	}
}
