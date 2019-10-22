package com.imxss.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.context.entity.Where;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.coody.framework.core.thread.SysThreadHandle;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.LetterInfo;
import com.imxss.web.domain.ProjectInfo;
import com.imxss.web.domain.ProjectModuleMapping;
import com.imxss.web.domain.UserInfo;

/**
 * @author coody
 * @date 2017年7月11日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
@Service
public class ProjectService {

	@Resource
	LetterService letterService;
	@Resource
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheFinal.PROJECT_LIST, fields = { "userId", "keyWorld", "pager.currPage",
			"pager.pageSize" }, time = 2)
	public Pager loadUserModules(Integer userId, Pager pager, String keyWorld) {
		Where where = new Where();
		if(userId!=null){
			where.set("userId", userId);
		}
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("title", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(ProjectInfo.class, where, pager, "id", true);
	}

	@CacheWrite(key = CacheFinal.PROJECT_INFO, fields = "id", time = 72000)
	public ProjectInfo loadProjectInfo(Integer id) {
		return jdbcHandle.findBeanFirst(ProjectInfo.class, "id", id);
	}
	@CacheWipe(key=CacheFinal.PROJECT_LIST,fields="project.userId")
	@CacheWipe(key = CacheFinal.PROJECT_INFO, fields = "project.id")
	@CacheWipe(key = CacheFinal.PROJECT_NUM, fields = "project.userId")
	public Long saveProjectInfo(ProjectInfo project) {
		return jdbcHandle.saveOrUpdateAuto(project);
	}
	@CacheWipe(key=CacheFinal.PROJECT_LIST,fields="project.userId")
	@CacheWipe(key = CacheFinal.PROJECT_INFO, fields = "project.id")
	@CacheWipe(key = CacheFinal.PROJECT_NUM, fields = "project.userId")
	public Long delProjectInfo(ProjectInfo project) {
		String sql = "delete from project_info where id=? limit 1";
		Long code=jdbcHandle.doUpdate(sql, project.getId());
		if(code<1){
			return code;
		}
		SysThreadHandle.sysThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				List<LetterInfo> letters=letterService.loadLettersByProjectNoCache(project);
				while(!StringUtil.isNullOrEmpty(letters)){
					for(LetterInfo letter:letters){
						letterService.delLetterInfo(letter);
					}
					letters=letterService.loadLettersByProjectNoCache(project);
				}
			}
		});
		return code;
	}
	
	public List<ProjectInfo> loadProjectsByUserNoCache(UserInfo userInfo){
		Where where=new Where();
		where.set("userId", userInfo.getId());
		Pager pager=new Pager();
		pager.setPageSize(100);
		return jdbcHandle.findBean(ProjectInfo.class,where,pager);
	}
	@CacheWrite(key = CacheFinal.PROJECT_LIST, fields = { "userInfo.id"}, time = 72000)
	public List<ProjectInfo> loadProjects(UserInfo userInfo){
		Where where=new Where();
		where.set("userId", userInfo.getId());
		return jdbcHandle.findBean(ProjectInfo.class,where,"id",true);
	}
	@CacheWrite(key = CacheFinal.PROJECT_LIST, time = 72000)
	public List<ProjectInfo> loadProjects(){
		Where where=new Where();
		return jdbcHandle.findBean(ProjectInfo.class,where,"id",true);
	}
	@CacheWrite(key = CacheFinal.PROJECT_MODULE_MAPPING_LIST, fields = "projectId", time = 72000)
	public List<ProjectModuleMapping> loadProjectMappings(Integer projectId) {
		return jdbcHandle.findBean(ProjectModuleMapping.class, "projectId", projectId);
	}

	@CacheWrite(key = CacheFinal.PROJECT_MODULE_MAPPING_LIST, fields = { "userId", "keyWorld", "pager.currPage",
			"pager.pageSize" }, time = 2)
	public Pager loadProjectMappings(Integer userId, Integer projectId, Pager pager, String keyWorld) {
		Where where = new Where();
		where.set("userId", userId);
		where.set("projectId", projectId);
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("mapping", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(ProjectModuleMapping.class, where, pager, "mapping", false);
	}

	@CacheWrite(key = CacheFinal.PROJECT_MODULE_MAPPING_INFO, fields = { "id" }, time = 72000)
	public ProjectModuleMapping loadProjectMappings(String id) {
		return jdbcHandle.findBeanFirst(ProjectModuleMapping.class, "id", id);
	}
	@CacheWipe(key=CacheFinal.PROJECT_LIST)
	@CacheWipe(key=CacheFinal.PROJECT_LIST,fields="mapping.userId")
	@CacheWipe(key = CacheFinal.PROJECT_MODULE_MAPPING_LIST, fields = "mapping.projectId")
	@CacheWipe(key = CacheFinal.PROJECT_MODULE_MAPPING_INFO, fields = { "mapping.id" })
	public Long saveProjectModuleMapping(ProjectModuleMapping mapping) {
		return jdbcHandle.saveOrUpdateAuto(mapping);
	}

	@CacheWrite(key = CacheFinal.PROJECT_NUM, fields = "userId", time = 60)
	public Integer loadProjectNum(Integer userId) {
		String sql = "select count(*) from project_info where userId=?";
		return jdbcHandle.getCount(sql, userId);
	}
	@CacheWipe(key=CacheFinal.PROJECT_LIST)
	@CacheWipe(key=CacheFinal.PROJECT_LIST,fields="mapping.userId")
	@CacheWipe(key = CacheFinal.PROJECT_MODULE_MAPPING_LIST, fields = "mapping.projectId")
	@CacheWipe(key = CacheFinal.PROJECT_MODULE_MAPPING_INFO, fields = { "mapping.id" })
	public Long delProjectModuleMapping(ProjectModuleMapping mapping) {
		String sql = "delete from project_module_mapping where id=? limit 1";
		return jdbcHandle.doUpdate(sql, mapping.getId());
	}
}
