package com.imxss.web.controller.user;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.AddressInfo;
import com.imxss.web.domain.LetterInfo;
import com.imxss.web.domain.LetterParas;
import com.imxss.web.domain.ModuleInfo;
import com.imxss.web.domain.ProjectInfo;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.schema.CookieSchema;
import com.imxss.web.schema.LetterSchema;
import com.imxss.web.service.IpService;
import com.imxss.web.service.LetterService;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.ProjectService;

@Service
@RequestMapping("/user/letter")
public class ULetterController extends BaseController {

	@Resource
	LetterService letterService;
	@Resource
	ProjectService projectService;
	@Resource
	ModuleService moduleService;
	@Resource
	IpService ipService;

	@RequestMapping("/letterCenter")
	@Power("letterCenter")
	public String letterCenter() {
		UserInfo userInfo = RequestUtil.getUser(request);
		Pager pager = getBeanAll(Pager.class);
		pager.setPageSize(12);
		pager = letterService.loadUserLetters(userInfo.getId(), pager, getPara("keyWorld"),
				getParaInteger("projectId"));
		List<LetterInfo> letters = pager.getData();
		if (!StringUtil.isNullOrEmpty(letters)) {
			List<LetterSchema> schemas = PropertUtil.getNewList(letters, LetterSchema.class);
			for (LetterSchema schema : schemas) {
				try {
					ProjectInfo project = projectService.loadProjectInfo(schema.getProjectId());
					if (project != null) {
						schema.setProjectName(project.getTitle());
					}
					AddressInfo info = ipService.loadIpInfo(schema.getIp());
					if (info != null) {
						schema.setIpInfo(info.toString());
					}
				} catch (Exception e) {
				}
			}
			pager.setData(schemas);
		}
		setAttribute("dataPager", pager);
		// 加载项目列表
		List<ProjectInfo> projects = projectService.loadProjects(userInfo);
		setAttribute("projects", projects);
		keepParas();
		return "user/letter/letter_list";
	}

	@RequestMapping("/letterInfo")
	@Power("letterCenter")
	public String letterInfo(Integer letterId) throws UnsupportedEncodingException {
		UserInfo userInfo = RequestUtil.getUser(request);
		LetterInfo letter = letterService.loadLetterInfo(letterId);
		if (letter.getIsReaded() == 0) {
			letterService.writeLetterStatus(letterId, 1);
			letter.setIsReaded(1);
		}
		if (letter == null || letter.getUserId() != userInfo.getId().intValue()) {
			return "user/letter/letter_edit";
		}
		LetterSchema schema = new LetterSchema();
		BeanUtils.copyProperties(letter, schema);
		// 加载信封项目
		ProjectInfo project = projectService.loadProjectInfo(schema.getProjectId());
		if (project != null) {
			schema.setProjectName(project.getTitle());
			if (letter.getModuleId() == null) {
				letter.setModuleId(project.getModuleId());
			}
			ModuleInfo module = moduleService.loadModuleInfo(letter.getModuleId());
			if (module != null) {
				schema.setModuleName(module.getTitle());
			}
		}
		if (schema != null && schema.getIp() != null) {
			AddressInfo info = ipService.loadIpInfo(schema.getIp());
			if (info != null) {
				schema.setIpInfo(info.toString());
			}
		}
		setAttribute("letterInfo", schema);
		// 加载信封参数
		List<LetterParas> paras = letterService.loadParas(letterId);
		setAttribute("letterParas", paras);
		// 格式化cookie
		LetterParas para = PropertUtil.getByList(paras, "paraName", "cookie");
		if (!StringUtil.hasNull(para, letter.getRefUrl())) {
			try {
				String cookie = CookieSchema.buildCookies(letter.getRefUrl(), para.getParaValue());
				setAttribute("cookieX", URLEncoder.encode(cookie, "UTF-8"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return "user/letter/letter_edit";
	}

	@RequestMapping("/letterDel")
	@Power("letterCenter")
	@ResponseBody
	public Object letterDel(Integer id) {
		UserInfo userInfo = RequestUtil.getUser(request);
		LetterInfo letter = letterService.loadLetterInfo(id);
		if (letter == null || letter.getUserId() != userInfo.getId().intValue()) {
			return new MsgEntity(-1, "无权操作");
		}
		Long code = letterService.delLetterInfo(letter);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		return new MsgEntity(0, "操作成功");
	}
}
