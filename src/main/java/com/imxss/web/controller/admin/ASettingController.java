package com.imxss.web.controller.admin;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.SettingInfo;
import com.imxss.web.service.SettingService;

@RequestMapping("/admin/setting")
@Controller
public class ASettingController extends BaseController{

	@Resource
	SettingService settingService;
	
	
	@RequestMapping("/settingManage")
	@Power("settingManage")
	public String settingManage(){
		return "admin/setting/setting";
	}
	
	@RequestMapping("/settingSave")
	@Power("settingManage")
	@ResponseBody
	public Object settingSave(){
		SettingInfo setting=getBeanAll(SettingInfo.class);
		Long code=settingService.writeSetting(setting);
		if(code<1){
			return new MsgEntity(-1,"操作失败");
		}
		return new MsgEntity(0,"操作成功");
	}
}
