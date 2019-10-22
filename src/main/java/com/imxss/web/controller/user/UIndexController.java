package com.imxss.web.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imxss.web.domain.EmailInfo;
import com.imxss.web.service.EmailService;
import com.imxss.web.service.SettingService;

@Controller
@RequestMapping("/")
public class UIndexController extends BaseController {

	@Resource
	EmailService emailService;

	@Resource
	SettingService settingService;

	@RequestMapping("/index")
	public String index(HttpServletResponse res) {
		return "index";
	}

	@RequestMapping("/login")
	public String login(HttpServletResponse res) {
		if(RequestUtil.isUserLogin(request)){
			return "redirect:user/index."+getSessionPara("defSuffix");
		}
		return "login";
	}

	@RequestMapping("/reg")
	public String reg(HttpServletResponse res) {
		// 加载发信邮箱列表
		List<EmailInfo> emails = emailService.loadEmails();
		emails=PropertUtil.doSeq(emails, "email");
		setAttribute("emails", emails);
		return "reg";
	}
	
	@RequestMapping("/resetPwd")
	public String resetPwd() {
		return "reset_pwd";
	}
}
