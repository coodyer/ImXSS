package com.imxss.web.controller.admin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.context.wrapper.XssHttpServletRequestWrapper;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.ModuleInfo;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.schema.ModuleSchema;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.UserService;

@RequestMapping("/admin/module")
@Controller
public class AModuleController extends BaseController{

	@Resource
	UserService userService;
	@Resource
	ModuleService moduleService;
	
	
	@RequestMapping("/moduleManage")
	@Power("moduleManage")
	public String moduleManage(){
		Pager pager=getBeanAll(Pager.class);
		pager=moduleService.loadUserModules(null, pager,getPara("keyWorld"));
		List<ModuleInfo> modules=pager.getData();
		if(!StringUtil.isNullOrEmpty(modules)){
			List<ModuleSchema> schemas=PropertUtil.getNewList(modules, ModuleSchema.class);
			for(ModuleSchema schema:schemas){
				if(schema.getUserId()==null||schema.getUserId()<1){
					continue;
				}
				UserInfo user=userService.loadUserInfo(schema.getUserId());
				if(user!=null){
					schema.setUserEmail(user.getEmail());
				}
			}
			pager.setData(schemas);
		}
		setAttribute("dataPager", pager);
		keepParas();
		return "admin/module/module_list";
	}
	

	
	@RequestMapping("/moduleDel")
	@Power("moduleManage")
	@ResponseBody
	public Object moduleDel(){
		Integer id=getParaInteger("id");
		ModuleInfo moduleInfo=moduleService.loadModuleInfo(id);
		Long code=moduleService.delModule(moduleInfo);
		if(code<1){
			return new MsgEntity(0,"删除失败");
		}
		return new MsgEntity(0,"删除成功");
	}
	
	@RequestMapping("/moduleEdit")
	@Power("moduleManage")
	public String moduleEdit(){
		Integer moduleId=getParaInteger("moduleId");
		if(moduleId!=null){
			ModuleInfo moduleInfo=moduleService.loadModuleInfo(moduleId);
			try {
				moduleInfo.setContent(moduleInfo.getContent().replace("<", "&lt;").replace(">", "&gt;"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			ModuleSchema schema=new ModuleSchema();
			BeanUtils.copyProperties(moduleInfo, schema);
			if(schema.getUserId()!=null&&schema.getUserId()>0){
				UserInfo user=userService.loadUserInfo(schema.getUserId());
				schema.setUserEmail(user.getEmail());
			}
			setAttribute("moduleInfo", schema);
		}
		return "admin/module/module_edit";
	}
	@RequestMapping("/moduleSave")
	@Power("moduleManage")
	@ResponseBody
	public Object moduleSave(HttpServletRequest req){
		Integer moduleId=getParaInteger("id");
		if(StringUtil.hasNull(getPara("title"),getPara("content"))){
			return new MsgEntity(-1,"参数有误");
		}
		ModuleInfo moduleInfo=new ModuleInfo().initModel();
		moduleInfo.setId(null);
		moduleInfo.setType(1);
		if(moduleId!=null){
			moduleInfo=moduleService.loadModuleInfo(moduleId);
		}
		moduleInfo=getBeanAccept(moduleInfo, "title","remark","content");
		moduleInfo.setContent(((XssHttpServletRequestWrapper) req).getOrgRequest().getParameter("content"));
		if(moduleService.saveModuleInfo(moduleInfo)<1){
			return new MsgEntity(-1,"保存失败");
		}
		return new MsgEntity(0,"保存成功");
	}
}
