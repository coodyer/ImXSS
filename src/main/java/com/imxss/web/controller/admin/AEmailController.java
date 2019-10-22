package com.imxss.web.controller.admin;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.EmailSenderUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.EmailInfo;
import com.imxss.web.service.EmailService;

@RequestMapping("/admin/email")
@Controller
public class AEmailController extends BaseController{

	

	@Resource
	EmailService emailService;
	
	@RequestMapping("/emailManage")
	@Power("emailManage")
	public String emailManage(){
		Pager pager=getBeanAll(Pager.class);
		pager=emailService.loadEmails(pager,getPara("keyWorld"));
		setAttribute("dataPager", pager);
		keepParas();
		return "admin/email/email_list";
	}
	@RequestMapping("/emailEdit")
	@Power("emailManage")
	public String emailEdit(){
		Integer id=getParaInteger("id");
		EmailInfo emailInfo=emailService.loadEmailInfo(id);
		setAttribute("email", emailInfo);
		keepParas();
		return "admin/email/email_edit";
	}
	
	@RequestMapping("/emailSave")
	@Power("emailManage")
	@ResponseBody
	public Object emailSave(){
		EmailInfo emailInfo=getBeanAll(EmailInfo.class);
		if(StringUtil.hasNull(emailInfo.getSmtp(),emailInfo.getEmail(),emailInfo.getPassword())){
			return new MsgEntity(-1,"参数有误");
		}
		if(!EmailSenderUtil.connectionTest(emailInfo.getSmtp(), emailInfo.getEmail(), emailInfo.getPassword())){
			return new MsgEntity(-1,"smtp连接失败");
		}
		Long code=emailService.saveEmailInfo(emailInfo);
		if(code<1){
			return new MsgEntity(-1,"操作失败");
		}
		return new MsgEntity(0,"操作成功");
	}
	
	@RequestMapping("/emailDel")
	@Power("emailManage")
	@ResponseBody
	public Object emailDel(Integer id){
		Long code=emailService.delEmailInfo(id);
		if(code<1){
			return new MsgEntity(-1,"操作失败");
		}
		return new MsgEntity(0,"操作成功");
	}
}
