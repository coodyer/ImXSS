package com.imxss.web.service;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.SettingInfo;

@Service
public class SettingService {
	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 加载网站设置
	 * @return
	 */
	@CacheWrite(key=CacheFinal.SETTING_INFO,time=72000)
	public SettingInfo loadSiteSetting(){
		return jdbcHandle.findBeanFirst(SettingInfo.class);
	}
	
	@CacheWipe(key=CacheFinal.SETTING_INFO)
	public Long writeSetting(SettingInfo setting){
		setting.setId(1);
		return jdbcHandle.saveOrUpdateAuto(setting);
	}
	
}
