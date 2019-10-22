/**
 * 
 */
package com.imxss.web.controller.user;

import java.util.Date;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.core.cache.LocalCache;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.EmailSenderUtil;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.coody.framework.util.UploadUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.InviteInfo;
import com.imxss.web.domain.SettingInfo;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.service.EmailService;
import com.imxss.web.service.InviteService;
import com.imxss.web.service.LetterService;
import com.imxss.web.service.MenuService;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.ProjectService;
import com.imxss.web.service.RoleService;
import com.imxss.web.service.SettingService;
import com.imxss.web.service.UserService;

/**
 * @author coody
 * @date 2017年7月11日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
@Service
@RequestMapping("/user")
public class UUserController extends BaseController {

	@Resource
	UserService userService;
	@Resource
	EmailService emailService;
	@Resource
	MenuService menuService;
	@Resource
	LetterService letterService;
	@Resource
	ProjectService projectService;
	@Resource
	ModuleService moduleService;
	@Resource
	RoleService roleService;
	@Resource
	SettingService settingService;
	@Resource
	InviteService inviteService;

	@RequestMapping("/login")
	@ResponseBody
	public Object login(String email, String userPwd) {
		if (StringUtil.hasNull(email, userPwd)) {
			return new MsgEntity(-1, "参数有误");
		}
		String loginKey = CacheFinal.LOGIN_NUM + email;
		Integer num = LocalCache.getCache(loginKey);
		if (num == null) {
			num = 0;
		}
		if (num != null && num > 10) {
			return new MsgEntity(-1, "登录过于频繁");
		}
		LocalCache.setCache(loginKey, num + 1,180);
		UserInfo userInfo = userService.loadUserInfo(email);
		if (userInfo == null) {
			return new MsgEntity(-1, "用户不存在");
		}
		if (!userInfo.getUserPwd().equals(EncryptUtil.customEnCode(userPwd))) {
			return new MsgEntity(-1, "密码不正确");
		}
		if (userInfo.getStatus() != 0) {
			return new MsgEntity(-1, "用户已冻结");
		}
		LocalCache.delCache(loginKey);
		RequestUtil.setUser(request, userInfo);
		setSessionPara("menus", menuService.loadMenus(userInfo.getRoleId()));
		return new MsgEntity(0, "登录成功");
	}

	@RequestMapping("/reg")
	@ResponseBody
	public Object reg(String email, String verCode, String userPwd) {
		SettingInfo setting = settingService.loadSiteSetting();
		if (setting.getOpenReg() != 1) {
			return new MsgEntity(-1, "已关闭注册");
		}
		UserInfo userInfo = userService.loadUserInfo(email);
		if (userInfo != null) {
			return new MsgEntity(-1, "该邮箱已注册");
		}
		if (StringUtil.hasNull(email, userPwd, verCode) || !StringUtil.isEmail(email)) {
			return new MsgEntity(-1, "参数有误");
		}
		if (!emailService.checkVerification(email, verCode)) {
			return new MsgEntity(-1, "验证码不正确");
		}
		String invite = getPara("invite");
		if (setting.getNeedInvite() == 1) {
			if (StringUtil.isNullOrEmpty(invite)) {
				return new MsgEntity(-1, "邀请码不正确");
			}
			InviteInfo inviteInfo = inviteService.loadInviteInfo(invite);
			if (inviteInfo.getStatus() != 0) {
				return new MsgEntity(-1, "邀请码已被使用");
			}
			Long code = inviteService.popInvite(invite);
			if (code < 1) {
				return new MsgEntity(-1, "邀请码已被使用");
			}
		}
		userInfo = new UserInfo();
		userInfo.setCreateTime(new Date());
		userInfo.setEmail(email);
		userInfo.setStatus(0);
		userInfo.setRoleId(0);
		userInfo.setUserPwd(EncryptUtil.customEnCode(userPwd));
		Long userId = userService.writeUserInfo(userInfo);
		if (userId < 1) {
			return new MsgEntity(-1, "注册失败");
		}
		if (setting.getNeedInvite() == 1) {
			// 写入邀请码注册用户
			inviteService.pushUserIdToInvite(invite, userId.intValue());
		}
		userInfo.setId(userId.intValue());
		RequestUtil.setUser(request, userInfo);
		setSessionPara("menus", menuService.loadMenus(userInfo.getRoleId()));
		
		return new MsgEntity(0, "登录成功");
	}

	
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Object resetPwd(String email, String verCode, String userPwd) {
		UserInfo userInfo = userService.loadUserInfo(email);
		if (userInfo == null) {
			return new MsgEntity(-1, "该邮箱未注册");
		}
		if (StringUtil.hasNull(email, userPwd, verCode) || !StringUtil.isEmail(email)) {
			return new MsgEntity(-1, "参数有误");
		}
		if (!emailService.checkVerification(email, verCode)) {
			return new MsgEntity(-1, "验证码不正确");
		}
		userInfo.setUserPwd(EncryptUtil.customEnCode(userPwd));
		Long code = userService.updateUserInfo(userInfo);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		RequestUtil.setUser(request, userInfo);
		setSessionPara("menus", menuService.loadMenus(userInfo.getRoleId()));
		return new MsgEntity(0, "操作成功");
	}
	@RequestMapping("/sendCode")
	@ResponseBody
	public Object sendCode(String email, String action) {
		String data=RequestUtil.getPostContent(request);
		System.out.println(data);
		if (StringUtil.hasNull(email) || !StringUtil.isEmail(email)) {
			return new MsgEntity(-1, "参数有误");
		}
		UserInfo userInfo = userService.loadUserInfo(email);
		if ("resetPwd".equals(action)) {
			if (userInfo == null) {
				return new MsgEntity(-1, "该邮箱未注册");
			}
		}
		if ("reg".equals(action)) {
			if (userInfo != null) {
				return new MsgEntity(-1, "该邮箱已注册");
			}
		}

		Integer code = emailService.sendCode(email);
		if (code == 0) {
			return new MsgEntity(0, "发送成功,请关注邮箱收件箱(部分邮件在垃圾箱)");
		}
		return new MsgEntity(-1, "发送过于频繁，请稍后再试");
	}

	@RequestMapping("/index")
	@Power("userSetting")
	public String index() {
		UserInfo userInfo = RequestUtil.getUser(request);
		// 加载未读信封
		Integer letterNotReadedNum = letterService.loadNotReaderNum(userInfo.getId(), 0);
		setAttribute("letterNotReadedNum", letterNotReadedNum);
		// 加载全部信封
		Integer letterNum = letterService.loadNotReaderNum(userInfo.getId(), null);
		setAttribute("letterNum", letterNum);
		// 加载项目数
		Integer projectNum = projectService.loadProjectNum(userInfo.getId());
		setAttribute("projectNum", projectNum);
		// 加载模块数
		Integer moduleNum = moduleService.loadModuleNum(userInfo.getId());
		setAttribute("moduleNum", moduleNum);
		return "user/index";
	}

	@RequestMapping("/loginOut")
	public String loginOut() {
		RequestUtil.setUser(request, null);
		return "redirect:" + request.getScheme() + ":" + getSessionPara("basePath").toString() + "login."
				+ getSessionPara("defSuffix").toString();
	}

	@Power("userSetting")
	@RequestMapping("/userSetting")
	public String userSetting() {
		UserInfo currUser = RequestUtil.getUser(request);
		setAttribute("currUser", currUser);
		return "user/setting";
	}

	@Power("userSetting")
	@RequestMapping("/uploadHead")
	@ResponseBody
	public Object uploadHead() {
		String url = UploadUtil.doUpload(request);
		if (url == null) {
			return new MsgEntity(-1, "上传失败");
		}
		return new MsgEntity(0, "上传成功", url);
	}

	@RequestMapping("/saveUserSetting")
	@ResponseBody
	@Power("userSetting")
	public Object saveUserSetting() {
		UserInfo sessionUser = RequestUtil.getUser(request);
		Integer userId = sessionUser.getId();
		UserInfo dbUserInfo = userService.loadUserInfo(userId);
		UserInfo userInfo = new UserInfo();
		BeanUtils.copyProperties(dbUserInfo, userInfo);
		userInfo = getBeanAccept(userInfo, "logo", "nickName", "smtp", "sendEmail", "sendPwd");
		if (!StringUtil.hasNull(userInfo.getSmtp(), userInfo.getSendEmail(), userInfo.getSendPwd())) {
			if (!StringUtil.isEmail(userInfo.getSendEmail())) {
				return new MsgEntity(-1, "邮箱格式不正确");
			}
			if (!EmailSenderUtil.connectionTest(userInfo.getSmtp(), userInfo.getSendEmail(), userInfo.getSendPwd())) {
				return new MsgEntity(-1, "smtp连接失败");
			}
		}
		userInfo.setId(userId);
		if (!StringUtil.isNullOrEmpty(userInfo.getLogo())) {
			userInfo.setLogo(userInfo.getLogo().replace("'", "").replace("\"", "").replace(" on", "").replace(";on", "")
					.replace("	on", ""));
		}
		Long code = userService.updateUserInfo(userInfo);
		if (code < 1) {
			return new MsgEntity(-1, "修改失败");
		}
		if (sessionUser.getId() == userId) {
			BeanUtils.copyProperties(userInfo, sessionUser);
			RequestUtil.setUser(request, sessionUser);
		}
		return new MsgEntity(0, "修改成功");
	}
	
	public static void main(String[] args) {
		System.out.println("\0b");
	}

}
