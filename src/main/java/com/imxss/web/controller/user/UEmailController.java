package com.imxss.web.controller.user;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imxss.web.service.EmailService;

@Controller
@RequestMapping("/user/email")
public class UEmailController extends BaseController{

	
	@Resource
	EmailService emailService;
	
	@RequestMapping("/emailCenter")
	@Power("emailCenter")
	public String emailCenter(){
		Pager pager=getBeanAll(Pager.class);
		pager=emailService.loadEmails(pager,getPara("keyWorld"));
		setAttribute("dataPager", pager);
		keepParas();
		
		return "user/email/email_list";
	}
	
}
