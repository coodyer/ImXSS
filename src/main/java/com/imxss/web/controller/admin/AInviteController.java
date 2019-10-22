package com.imxss.web.controller.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.JUUIDUtil;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.InviteInfo;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.schema.InviteSchema;
import com.imxss.web.service.InviteService;
import com.imxss.web.service.UserService;

@RequestMapping("/admin/invite")
@Controller
public class AInviteController extends BaseController {

	@Resource
	InviteService inviteService;
	@Resource
	UserService userService;

	@RequestMapping("/createInvite")
	@ResponseBody
	@Power("inviteManage")
	public Object createInvite() {
		for (int i = 0; i < 20; i++) {
			InviteInfo inviteInfo = new InviteInfo();
			inviteInfo.setStatus(0);
			inviteInfo.setInviteCode(JUUIDUtil.createUuid());
			inviteInfo.setUpdateTime(new Date());
			inviteService.writeInvite(inviteInfo);
		}
		return new MsgEntity(0, "生成成功");
	}

	@RequestMapping("/inviteManage")
	@Power("inviteManage")
	public String inviteManage() {
		Pager pager = getBeanAll(Pager.class);
		pager.setPageSize(10);
		pager = inviteService.loadInvites(pager, getPara("keyWorld"),getParaInteger("status"));
		if (!StringUtil.isNullOrEmpty(pager.getData())) {
			List<InviteSchema> invites = PropertUtil.getNewList((List<?>) pager.getData(), InviteSchema.class);
			for(InviteSchema schema:invites){
				if(schema.getStatus()==0||StringUtil.isNullOrEmpty(schema.getUserId())){
					continue;
				}
				
				UserInfo user=userService.loadUserInfo(schema.getUserId());
				if(!StringUtil.isNullOrEmpty(user)){
					schema.setRegUserEmail(user.getEmail());
				}
			}
			pager.setData(invites);
		}
		setAttribute("dataPager", pager);
		keepParas();
		return "admin/invite/invite_list";
	}
}
