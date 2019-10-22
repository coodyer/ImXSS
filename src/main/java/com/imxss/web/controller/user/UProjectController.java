package com.imxss.web.controller.user;

import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.JUUIDUtil;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.ModuleInfo;
import com.imxss.web.domain.ProjectInfo;
import com.imxss.web.domain.ProjectModuleMapping;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.schema.ProjectModuleMappingSchema;
import com.imxss.web.schema.ProjectSchema;
import com.imxss.web.service.LetterService;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.ProjectService;
import com.imxss.web.service.SortUrlService;

@Controller
@RequestMapping("/user/project")
public class UProjectController extends BaseController {

	@Resource
	ProjectService projectService;
	@Resource
	LetterService letterService;
	@Resource
	ModuleService moduleService;
	@Resource
	SortUrlService sortUrlService;

	@RequestMapping("/projectCenter")
	@Power("projectCenter")
	public String projectCenter() {
		UserInfo userInfo = RequestUtil.getUser(request);
		Pager pager = getBeanAll(Pager.class);
		pager = projectService.loadUserModules(userInfo.getId(), pager, getPara("keyWorld"));
		keepParas();
		if (pager == null || StringUtil.isNullOrEmpty(pager.getData())) {
			return "user/project/project_list";
		}
		List<ProjectInfo> projects = pager.getData();
		if (!StringUtil.isNullOrEmpty(projects)) {
			List<ProjectSchema> schemas = PropertUtil.getNewList(projects, ProjectSchema.class);
			for (ProjectSchema schema : schemas) {
				try {
					schema.setLetterNum(letterService.loadLetterNum(schema.getId()));
					ModuleInfo module = moduleService.loadModuleInfo(schema.getModuleId());
					if (module != null) {
						schema.setModuleName(module.getTitle());
					}
				} catch (Exception e) {
				}
			}
			pager.setData(schemas);
		}
		setAttribute("dataPager", pager);
		return "user/project/project_list";
	}

	@RequestMapping("/projectModuleCustom")
	@Power("projectCenter")
	public String projectModuleCustom(Integer projectId) {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		List<ModuleInfo> userModules = moduleService.loadUserModules(userInfo.getId());
		setAttribute("userModules", userModules);
		if (project == null || project.getUserId() != userInfo.getId().intValue()) {
			return "user/project/project_custom_list";
		}
		setAttribute("projectInfo", project);
		Pager pager = getBeanAll(Pager.class);
		pager = projectService.loadProjectMappings(userInfo.getId(), projectId, pager, getPara("keyWorld"));
		List<ProjectModuleMapping> mappings = pager.getData();
		if (!StringUtil.isNullOrEmpty(mappings)) {
			List<ProjectModuleMappingSchema> schemas = PropertUtil.getNewList(mappings,
					ProjectModuleMappingSchema.class);
			for (ProjectModuleMappingSchema schema : schemas) {
				try {
					ModuleInfo module = moduleService.loadModuleInfo(schema.getModuleId());
					schema.setModuleName(module.getTitle());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			pager.setData(schemas);
		}
		setAttribute("dataPager", pager);
		keepParas();
		return "user/project/project_custom_list";
	}

	@RequestMapping("/projectModuleCustomEdit")
	@Power("projectCenter")
	public String projectModuleCustomEdit(String id, Integer projectId) {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectModuleMapping mapping = projectService.loadProjectMappings(id);
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		List<ModuleInfo> userModules = moduleService.loadUserModules(userInfo.getId());
		setAttribute("userModules", userModules);
		if (mapping != null) {
			projectId = mapping.getProjectId();
		}
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		if (project == null || project.getUserId() != userInfo.getId().intValue()) {
			return "user/project/project_custom_edit";
		}
		setAttribute("projectInfo", project);
		setAttribute("mapping", mapping);
		return "user/project/project_custom_edit";
	}

	@RequestMapping("/projectModuleCustomSave")
	@Power("projectCenter")
	@ResponseBody
	public Object projectModuleCustomSave() {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectModuleMapping mapping = projectService.loadProjectMappings(getPara("id"));
		if (!StringUtil.isNullOrEmpty(mapping)) {
			if (mapping.getUserId() != userInfo.getId().intValue()) {
				return new MsgEntity(-1, "无权操作");
			}
		}
		if (StringUtil.isNullOrEmpty(mapping)) {
			mapping = new ProjectModuleMapping();
			mapping.setId(JUUIDUtil.createUuid());
		}
		mapping = getBeanAccept(mapping, "projectId", "moduleId", "mapping", "type");
		if (mapping == null
				|| StringUtil.hasNull(mapping.getMapping(), mapping.getModuleId(), mapping.getProjectId())) {
			return new MsgEntity(-1, "参数有误");
		}
		ProjectInfo project = projectService.loadProjectInfo(mapping.getProjectId());
		if (project == null || project.getUserId() != userInfo.getId().intValue()) {
			return new MsgEntity(-1, "无权操作");
		}
		mapping.setUserId(userInfo.getId());
		Long code = projectService.saveProjectModuleMapping(mapping);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		return new MsgEntity(0, "操作成功");
	}

	@RequestMapping("/projectModuleCustomDel")
	@Power("projectCenter")
	@ResponseBody
	public Object projectModuleCustomDel(String id) {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectModuleMapping mapping = projectService.loadProjectMappings(id);
		if (mapping == null) {
			return new MsgEntity(-1, "数据不存在");
		}
		ProjectInfo project = projectService.loadProjectInfo(mapping.getProjectId());
		if (project == null || project.getUserId() != userInfo.getId().intValue()) {
			return new MsgEntity(-1, "无权操作");
		}
		Long code = projectService.delProjectModuleMapping(mapping);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		return new MsgEntity(0, "操作成功");
	}

	@RequestMapping("/projectDel")
	@Power("projectCenter")
	@ResponseBody
	public Object projectDel(Integer id) {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectInfo projectInfo = projectService.loadProjectInfo(id);
		if (projectInfo == null || projectInfo.getUserId() != userInfo.getId().intValue()) {
			return new MsgEntity(-1, "无权操作");
		}
		Long code = projectService.delProjectInfo(projectInfo);
		if (code < 1) {
			return new MsgEntity(0, "删除失败");
		}
		return new MsgEntity(0, "删除成功");
	}

	@RequestMapping("/projectEdit")
	@Power("projectCenter")
	public String projectEdit(Integer projectId) {
		UserInfo userInfo = RequestUtil.getUser(request);
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		List<ModuleInfo> userModules = moduleService.loadUserModules(userInfo.getId());
		setAttribute("userModules", userModules);
		if (project == null || project.getUserId() != userInfo.getId().intValue()) {
			return "user/project/project_edit";
		}
		ProjectSchema projectSchema = new ProjectSchema();
		BeanUtils.copyProperties(project, projectSchema);
		setAttribute("projectInfo", projectSchema);
		// 加载模块定制信息
		List<ProjectModuleMapping> mappings = projectService.loadProjectMappings(projectId);
		if (!StringUtil.isNullOrEmpty(mappings)) {
			List<ProjectModuleMappingSchema> schemas = PropertUtil.getNewList(mappings,
					ProjectModuleMappingSchema.class);
			for (ProjectModuleMappingSchema schema : schemas) {
				try {
					ModuleInfo module = moduleService.loadModuleInfo(schema.getModuleId());
					schema.setModuleName(module.getTitle());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			setAttribute("mappings", schemas);
		}
		return "user/project/project_edit";
	}

	@RequestMapping("/projectSave")
	@Power("projectCenter")
	@ResponseBody
	public Object projectSave() {
		if (StringUtil.hasNull(getPara("title"), getPara("moduleId"))) {
			return new MsgEntity(-1, "参数有误");
		}
		UserInfo userInfo = RequestUtil.getUser(request);
		Integer id = getParaInteger("id");
		ProjectInfo projectInfo = new ProjectInfo().initModel();
		projectInfo.setId(null);
		projectInfo.setUserId(userInfo.getId());
		if (id != null) {
			projectInfo = projectService.loadProjectInfo(id);
			if (projectInfo.getUserId() != userInfo.getId().intValue()) {
				return new MsgEntity(-1, "无权操作");
			}
		}
		projectInfo = getBeanAccept(projectInfo, "title", "moduleId", "openEmail", "ignoreRef", "ignoreIp","isOpen");
		ModuleInfo moduleInfo = moduleService.loadModuleInfo(projectInfo.getModuleId());
		if (moduleInfo == null
				|| (moduleInfo.getUserId() != userInfo.getId().intValue() && moduleInfo.getType() != 1)) {
			return new MsgEntity(-1, "模板选择有误");
		}
		if (id != null) {
			projectInfo.setUri(
					getSessionPara("basePath") + "s/" + projectInfo.getId() + "." + getSessionPara("defSuffix"));
			String sortUrl = sortUrlService.getSortUrl("http:" + projectInfo.getUri());
			projectInfo.setSortUri(sortUrl);
		}
		Long code = projectService.saveProjectInfo(projectInfo);
		if (code < 1) {
			return new MsgEntity(-1, "保存失败");
		}
		if (id == null) {
			projectInfo.setId(code.intValue());
			projectInfo.setUri(
					getSessionPara("basePath") + "s/" + projectInfo.getId() + "." + getSessionPara("defSuffix"));
			String sortUrl = sortUrlService.getSortUrl("http:" + projectInfo.getUri());
			projectInfo.setSortUri(sortUrl);
			projectService.saveProjectInfo(projectInfo);
		}
		return new MsgEntity(0, "保存成功");
	}

}
