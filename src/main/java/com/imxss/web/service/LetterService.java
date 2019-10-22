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
import com.imxss.web.domain.LetterInfo;
import com.imxss.web.domain.LetterParas;
import com.imxss.web.domain.ProjectInfo;

@Service
public class LetterService {

	@Resource
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.PROJECT_LETTER_NUM,fields="projectId",time=72000)
	public Integer loadLetterNum(Integer projectId){
		String sql="select count(*) from letter_info where projectId=?";
		return jdbcHandle.queryFirstAuto(Integer.class, sql, projectId);
	}
	
	@CacheWrite(key=CacheFinal.LETTER_LIST,fields={"userId","keyWorld","pager.currPage","pager.pageSize"},time=2)
	public Pager loadUserLetters(Integer userId,Pager pager,String keyWorld,Integer projectId){
		Where where=new Where();
		if(userId!=null){
			where.set("userId", userId);
		}
		if(!StringUtil.isNullOrEmpty(keyWorld)){
			where.set("refUrl", "like","%"+keyWorld+"%");
		}
		if(projectId!=null&&projectId!=0){
			where.set("projectId", projectId);
		}
		return jdbcHandle.findPager(LetterInfo.class, where,pager,"id",true);
	}
	@CacheWrite(key=CacheFinal.LETTER_NUM,fields={"userId","isReaded"},time=5)
	public Integer loadNotReaderNum(Integer userId,Integer isReaded){
		if(isReaded!=null){
			String sql="select count(*) from letter_info where userId=? and isReaded=?";
			return jdbcHandle.getCount(sql,userId,isReaded);
		}
		String sql="select count(*) from letter_info where userId=?";
		return jdbcHandle.getCount(sql,userId);
	}
	@CacheWrite(key=CacheFinal.LETTER_INFO,fields={"id"},time=72000)
	public LetterInfo loadLetterInfo(Integer id){
		LetterInfo letter= jdbcHandle.findBeanFirst(LetterInfo.class, "id",id);
		return letter;
	}
	@CacheWipe(key=CacheFinal.LETTER_INFO,fields="letter.id")
	public void writeLetterStatus(Integer letterId,Integer status){
		String sql="update letter_info set isReaded=? where id=? limit 1";
		jdbcHandle.doUpdate(sql,status,letterId);
	}
	@CacheWrite(key=CacheFinal.LETTER_PARAS,fields="letterId",time=72000)
	public List<LetterParas> loadParas(Integer letterId){
		return jdbcHandle.findBean(LetterParas.class, "letterId",letterId);
	}
	
	@CacheWrite(key=CacheFinal.LETTER_INFO,fields="unionId",time=72000)
	public LetterInfo loadLetterInfo(String unionId){
		return jdbcHandle.findBeanFirst(LetterInfo.class, "unionId",unionId);
	}
	
	@CacheWipe(key=CacheFinal.PROJECT_LETTER_NUM,fields="letter.projectId")
	public Long writeLetterInfo(LetterInfo letter){
		return jdbcHandle.insert(letter);
	}
	
	@CacheWipe(key=CacheFinal.PROJECT_LETTER_NUM,fields="letter.projectId")
	@CacheWipe(key=CacheFinal.LETTER_INFO,fields="letter.unionId")
	@CacheWipe(key=CacheFinal.LETTER_INFO,fields="letter.id")
	@CacheWipe(key=CacheFinal.LETTER_PARAS,fields="letter.id")
	public Long delLetterInfo(LetterInfo letter){
		String sql="delete from letter_info where id=? limit 1";
		Long code=jdbcHandle.doUpdate(sql,letter.getId());
		if(code<1){
			return code;
		}
		sql="delete from letter_paras where letterId=?";
		return jdbcHandle.doUpdate(sql,letter.getId());
	}
	@CacheWipe(key=CacheFinal.LETTER_PARAS,fields="letterId")
	public Long writeLetterParas(LetterParas para){
		return jdbcHandle.insert(para);
	}
	
	public List<LetterInfo> loadLettersByProjectNoCache(ProjectInfo project){
		Where where=new Where();
		where.set("projectId", project.getId());
		Pager pager=new Pager();
		pager.setPageSize(100);
		return jdbcHandle.findBean(LetterInfo.class,where,pager);
	}
}
