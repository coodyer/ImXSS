package com.imxss.web.service;

import java.net.MalformedURLException;
import java.net.URL;
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
import com.imxss.web.domain.ProjectInfo;
import com.imxss.web.domain.UserInfo;

/**
 * @author coody
 * @date 2017年7月11日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
@Service
public class UserService {

	@Resource
	JdbcHandle jdbcHandle;
	@Resource
	ProjectService projectService;

	@CacheWrite(key = CacheFinal.USER_INFO, fields = "account", time = 60)
	public UserInfo loadUserInfo(String account) {
		String sql = "select * from user_info where email=? or mobile=? limit 1";
		return jdbcHandle.queryFirstAuto(UserInfo.class, sql, account, account);
	}

	@CacheWrite(key = CacheFinal.USER_INFO, fields = "id", time = 72000)
	public UserInfo loadUserInfo(Integer id) {
		return jdbcHandle.findBeanFirst(UserInfo.class, "id", id);
	}

	@CacheWipe(key = CacheFinal.USER_INFO, fields = { "userInfo.id" })
	@CacheWipe(key = CacheFinal.USER_INFO, fields = { "userInfo.email" })
	@CacheWipe(key = CacheFinal.USER_INFO, fields = "userInfo.mobile")
	public Long updateUserInfo(UserInfo userInfo) {
		return jdbcHandle.saveOrUpdateAuto(userInfo);
	}

	@CacheWipe(key = CacheFinal.USER_INFO, fields = { "userInfo.id" })
	@CacheWipe(key = CacheFinal.USER_INFO, fields = { "userInfo.email" })
	@CacheWipe(key = CacheFinal.USER_INFO, fields = "userInfo.mobile")
	public Long delUser(UserInfo userInfo) {
		String sql = "delete from user_info where id=? limit 1";
		Long code=jdbcHandle.doUpdate(sql, userInfo.getId());
		if(code<1){
			return code;
		}
		SysThreadHandle.sysThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				List<ProjectInfo> projects=projectService.loadProjectsByUserNoCache(userInfo);
				while(!StringUtil.isNullOrEmpty(projects)){
					for(ProjectInfo project:projects){
						projectService.delProjectInfo(project);
					}
					projects=projectService.loadProjectsByUserNoCache(userInfo);
				}
			}
		});
		return code;
	}
	public Long writeUserInfo(UserInfo userInfo) {
		return jdbcHandle.insert(userInfo);
	}

	@CacheWrite(key = CacheFinal.USER_LIST, fields = { "keyWorld", "pager.currPage", "pager.pageSize" }, time = 2)
	public Pager loadUsers(Pager pager, String keyWorld) {
		Where where = new Where();
		if (!StringUtil.isNullOrEmpty(keyWorld)) {
			where.set("email", "like", "%" + keyWorld + "%");
		}
		return jdbcHandle.findPager(UserInfo.class, where, pager, "id", true);
	}

	
	public static void main(String[] args) throws MalformedURLException {
		URL url=new URL("https://www.qianniaog.com/search.php?mod=forum&searchid=15&orderby=lastpost&ascdesc=desc&searchsubmit=yes&kw=%2A");
		System.out.println(url.getFile());
	}
}
