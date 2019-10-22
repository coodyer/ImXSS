package com.imxss.web.controller.admin;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.UserInfo;
import com.imxss.web.service.MenuService;
import com.imxss.web.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

	@Resource
	UserService userService;
	@Resource
	MenuService menuService;
	
	@RequestMapping(value = {"/login",""})
	public String login(HttpServletResponse response) throws ServletException, IOException {
		return "admin/login";
	}
	@RequestMapping(value = {"/doLogin"})
	@ResponseBody
	public Object doLogin(HttpServletResponse response) throws ServletException, IOException {
		String username = getPara("username");
		String password = getPara("password");
		String verCode = getPara("verCode");
		if (StringUtil.isNullOrEmpty(verCode)) {
			return new MsgEntity(3, "验证码为空");
		}
		if (StringUtil.hasNull(username, password)) {
			return new MsgEntity(1, "用户名或密码为空");
		}
		String sessionCode = RequestUtil.getCode(request);
		setSessionPara("piccode", null);
		if (sessionCode == null || !sessionCode.equals(verCode)) {
			return new MsgEntity(4, "验证码有误");

		}
		UserInfo member = userService.loadUserInfo(username);
		if (StringUtil.isNullOrEmpty(member)) {
			return new MsgEntity(2, "该用户不存在");
		}
		if(member.getRoleId()!=1){
			return new MsgEntity(2, "该用户不是管理员");
		}
		password = EncryptUtil.customEnCode(password);
		if (!password.equals(member.getUserPwd())) {
			return new MsgEntity(3, "密码有误");
		}
		RequestUtil.setUser(request, member);
		setSessionPara("menus",menuService.loadMenus(member.getRoleId()));
		setSessionPara("loginTime", new Date());
		return new MsgEntity(0, "登录成功");
	}

}
