package com.imxss.web.controller.admin;

import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.JUUIDUtil;
import org.coody.framework.util.PropertUtil;
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
import com.imxss.web.service.UserService;

@RequestMapping("/admin/project")
@Controller
public class AProjectController extends BaseController{

	@Resource
	ProjectService projectService;
	@Resource
	UserService userService;
	@Resource
	LetterService letterService;
	@Resource
	ModuleService moduleService;
	@Resource
	SortUrlService sortUrlService;

	@RequestMapping("/projectManage")
	@Power("projectManage")
	public String projectManage(){
		Pager pager = getBeanAll(Pager.class);
		pager = projectService.loadUserModules(null, pager, getPara("keyWorld"));
		keepParas();
		if (pager == null || StringUtil.isNullOrEmpty(pager.getData())) {
			return "admin/project/project_list";
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
					UserInfo user=userService.loadUserInfo(schema.getUserId());
					if(user!=null){
						schema.setUserEmail(user.getEmail());;
					}
				} catch (Exception e) {
				}
			}
			pager.setData(schemas);
		}
		setAttribute("dataPager", pager);
		return "admin/project/project_list";
	}
	
	@RequestMapping("/projectDel")
	@Power("projectManage")
	@ResponseBody
	public Object projectDel(Integer id) {
		ProjectInfo projectInfo = projectService.loadProjectInfo(id);
		Long code = projectService.delProjectInfo(projectInfo);
		if (code < 1) {
			return new MsgEntity(0, "删除失败");
		}
		return new MsgEntity(0, "删除成功");
	}
	
	@RequestMapping("/projectEdit")
	@Power("projectManage")
	public String projectEdit(Integer projectId) {
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		sysModules=PropertUtil.doSeq(sysModules, "id");
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		List<ModuleInfo> userModules = moduleService.loadUserModules(project.getUserId());
		setAttribute("userModules", userModules);
		ProjectSchema projectSchema=new ProjectSchema();
		BeanUtils.copyProperties(project, projectSchema);
		UserInfo userInfo=userService.loadUserInfo(projectSchema.getUserId());
		if(userInfo!=null){
			projectSchema.setUserEmail(userInfo.getEmail());
		}
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
		return "admin/project/project_edit";
	}
	
	
	@RequestMapping("/projectSave")
	@Power("projectManage")
	@ResponseBody
	public Object projectSave() {
		Integer id = getParaInteger("id");
		ProjectInfo projectInfo = projectService.loadProjectInfo(id);
		projectInfo = getBeanAccept(projectInfo, "title", "moduleId", "openEmail", "ignoreRef","ignoreIp","isOpen");
		if(StringUtil.hasNull(projectInfo.getTitle(),projectInfo.getModuleId())){
			return new MsgEntity(-1,"参数有误");
		}
		ModuleInfo moduleInfo = moduleService.loadModuleInfo(projectInfo.getModuleId());
		if (moduleInfo == null
				|| (moduleInfo.getUserId() != projectInfo.getUserId().intValue() && moduleInfo.getType() != 1)) {
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
	
	
	
	
	
	@RequestMapping("/projectModuleCustom")
	@Power("projectManage")
	public String projectModuleCustom(Integer projectId) {
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		List<ModuleInfo> userModules = moduleService.loadUserModules(project.getUserId());
		setAttribute("userModules", userModules);
		setAttribute("projectInfo", project);
		Pager pager = getBeanAll(Pager.class);
		pager = projectService.loadProjectMappings(project.getUserId(), projectId, pager, getPara("keyWorld"));
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
		return "admin/project/project_custom_list";
	}

	@RequestMapping("/projectModuleCustomEdit")
	@Power("projectManage")
	public String projectModuleCustomEdit(String id,Integer projectId) {
		ProjectModuleMapping mapping = projectService.loadProjectMappings(id);
		if(!StringUtil.isNullOrEmpty(mapping)){
			projectId=mapping.getProjectId();
		}
		// 加载系统模块
		List<ModuleInfo> sysModules = moduleService.loadSysModules();
		setAttribute("sysModules", sysModules);
		// 加载用户模块
		ProjectInfo project = projectService.loadProjectInfo(projectId);
		List<ModuleInfo> userModules = moduleService.loadUserModules(project.getUserId());
		setAttribute("userModules", userModules);
		setAttribute("projectInfo", project);
		setAttribute("mapping", mapping);
		return "admin/project/project_custom_edit";
	}

	@RequestMapping("/projectModuleCustomSave")
	@Power("projectManage")
	@ResponseBody
	public Object projectModuleCustomSave() {
		if(StringUtil.hasNull(getPara("title"),getPara("moduleId"))){
			return new MsgEntity(-1,"参数有误");
		}
		ProjectModuleMapping mapping = projectService.loadProjectMappings(getPara("id"));
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
		mapping.setUserId(project.getUserId());
		Long code = projectService.saveProjectModuleMapping(mapping);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		return new MsgEntity(0, "操作成功");
	}

	@RequestMapping("/projectModuleCustomDel")
	@Power("projectManage")
	@ResponseBody
	public Object projectModuleCustomDel(String id) {
		ProjectModuleMapping mapping = projectService.loadProjectMappings(id);
		if (mapping == null) {
			return new MsgEntity(-1, "数据不存在");
		}
		Long code = projectService.delProjectModuleMapping(mapping);
		if (code < 1) {
			return new MsgEntity(-1, "操作失败");
		}
		return new MsgEntity(0, "操作成功");
	}

}
