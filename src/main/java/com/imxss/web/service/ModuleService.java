package com.imxss.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.entity.Pager;
import org.coody.framework.context.entity.Where;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.ModuleInfo;

@Service
public class ModuleService {

	@Resource
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheFinal.MODULE_LIST, fields = { "userId", "keyWorld", "pager.currPage",
			"pager.pageSize" }, time = 2)
	public Pager loadUserModules(Integer userId, Pager pager, String keyWorld) {
		Where where = new Where();
		if(userId!=null){
			where.set("userId", userId);
		}
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("title", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(ModuleInfo.class, where, pager, "id", true);
	}

	@CacheWrite(key = CacheFinal.MODULE_COMM_LIST, fields = { "keyWorld", "pager.currPage",
			"pager.pageSize" }, time = 10)
	public Pager loadCommModules(Pager pager, String keyWorld) {
		Where where = new Where();
		where.set("type", 1);
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("title", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(ModuleInfo.class, where, pager, "id", true);
	}

	@CacheWrite(key = CacheFinal.MODULE_INFO, fields = "moduleId", time = 72000)
	public ModuleInfo loadModuleInfo(Integer moduleId) {
		return jdbcHandle.findBeanFirst(ModuleInfo.class, "id", moduleId);
	}

	@CacheWipe(key = CacheFinal.MODULE_INFO, fields = "moduleInfo.id")
	@CacheWipe(key = CacheFinal.MODULE_USER_LIST, fields = "moduleInfo.userId")
	@CacheWipe(key = CacheFinal.MODULE_NUM, fields = "moduleInfo.userId")
	public Long saveModuleInfo(ModuleInfo moduleInfo) {
		return jdbcHandle.saveOrUpdateAuto(moduleInfo);
	}

	@CacheWipe(key = CacheFinal.MODULE_INFO, fields = "module.id")
	@CacheWipe(key = CacheFinal.MODULE_NUM, fields = "module.userId")
	public Long delModule(ModuleInfo module) {
		String sql = "delete from module_info where id=? limit 1";
		Long code = jdbcHandle.doUpdate(sql, module.getId());
		return code;
	}

	@CacheWrite(key = CacheFinal.MODULE_SYS_LIST, time = 10)
	public List<ModuleInfo> loadSysModules() {
		Where where = new Where();
		where.set("type", 1);
		return jdbcHandle.findBean(ModuleInfo.class, where, "id", true);
	}

	@CacheWrite(key = CacheFinal.MODULE_USER_LIST, fields = "userId", time = 60)
	public List<ModuleInfo> loadUserModules(Integer userId) {
		return jdbcHandle.findBean(ModuleInfo.class, "userId", userId, "id", true);
	}

	@CacheWrite(key = CacheFinal.MODULE_NUM, fields = "userId", time = 50)
	public Integer loadModuleNum(Integer userId) {
		String sql = "select count(*) from module_info where userId=?";
		return jdbcHandle.getCount(sql, userId);
	}
}
