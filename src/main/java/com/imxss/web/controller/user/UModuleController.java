package com.imxss.web.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.context.wrapper.XssHttpServletRequestWrapper;
import org.coody.framework.core.controller.BaseController;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.ModuleInfo;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.service.ModuleService;
import com.imxss.web.service.UserService;

@Controller
@RequestMapping("/user/module")
public class UModuleController extends BaseController{

	@Resource
	UserService userService;
	@Resource
	ModuleService moduleService;
	
	
	@RequestMapping("/moduleCenter")
	@Power("moduleCenter")
	public String moduleCenter(){
		UserInfo userInfo=RequestUtil.getUser(request);
		Pager pager=getBeanAll(Pager.class);
		pager=moduleService.loadUserModules(userInfo.getId(), pager,getPara("keyWorld"));
		setAttribute("dataPager", pager);
		keepParas();
		return "user/module/module_list";
	}
	
	@RequestMapping("/moduleComm")
	@Power("moduleComm")
	public String moduleComm(){
		Pager pager=getBeanAll(Pager.class);
		pager=moduleService.loadCommModules(pager,getPara("keyWorld"));
		setAttribute("dataPager", pager);
		keepParas();		
		setAttribute("isCommon", true);
		return "user/module/module_list";
	}
	

	
	@RequestMapping("/moduleDel")
	@Power("moduleCenter")
	@ResponseBody
	public Object moduleDel(){
		UserInfo userInfo=RequestUtil.getUser(request);
		Integer id=getParaInteger("id");
		ModuleInfo moduleInfo=moduleService.loadModuleInfo(id);
		if(moduleInfo==null||moduleInfo.getUserId()!=userInfo.getId().intValue()){
			return new MsgEntity(-1,"无权操作");
		}
		Long code=moduleService.delModule(moduleInfo);
		if(code<1){
			return new MsgEntity(0,"删除失败");
		}
		return new MsgEntity(0,"删除成功");
	}
	
	@RequestMapping("/moduleEdit")
	@Power("moduleCenter")
	public String moduleEdit(){
		Integer moduleId=getParaInteger("moduleId");
		UserInfo userInfo=RequestUtil.getUser(request);
		if(moduleId!=null){
			ModuleInfo moduleInfo=moduleService.loadModuleInfo(moduleId);
			if(moduleInfo!=null&&moduleInfo.getType()!=1&&moduleInfo.getUserId()!=userInfo.getId().intValue()){
				return "module/module_edit";
			}
			try {
				moduleInfo.setContent(moduleInfo.getContent().replace("<", "&lt;").replace(">", "&gt;"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			setAttribute("moduleInfo", moduleInfo);
		}
		return "user/module/module_edit";
	}
	@RequestMapping("/moduleSave")
	@Power("moduleCenter")
	@ResponseBody
	public Object moduleSave(HttpServletRequest req){
		if(StringUtil.hasNull(getPara("title"),getPara("content"))){
			return new MsgEntity(-1,"参数有误");
		}
		UserInfo userInfo=RequestUtil.getUser(req);
		Integer moduleId=getParaInteger("id");
		ModuleInfo moduleInfo=new ModuleInfo().initModel();
		moduleInfo.setId(null);
		moduleInfo.setUserId(userInfo.getId());
		if(moduleId!=null){
			moduleInfo=moduleService.loadModuleInfo(moduleId);
			if(moduleInfo.getUserId()!=userInfo.getId().intValue()){
				return new MsgEntity(-1,"无权操作");
			}
		}
		moduleInfo=getBeanAccept(moduleInfo, "title","remark","content");
		moduleInfo.setContent(((XssHttpServletRequestWrapper) req).getOrgRequest().getParameter("content"));
		if(moduleService.saveModuleInfo(moduleInfo)<1){
			return new MsgEntity(-1,"保存失败");
		}
		return new MsgEntity(0,"保存成功");
	}
	
}
