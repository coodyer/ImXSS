package com.imxss.web.service;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.entity.Where;
import org.coody.framework.core.jdbc.JdbcHandle;
import org.springframework.stereotype.Service;

import com.imxss.web.constant.CacheFinal;
import com.imxss.web.domain.EmailRecord;

@Service
public class EmailRecordService {

	@Resource
	JdbcHandle jdbcHandle;
	
	@CacheWrite(key=CacheFinal.EMAIL_RECORD,time=3600,fields= {"email","day"})
	public EmailRecord getEmailRecord(String email,String day) {
		Where where=new Where();
		where.set("email", email);
		where.set("day", day);
		return jdbcHandle.findBeanFirst(EmailRecord.class,where);
	}
	
	@CacheWipe(key=CacheFinal.EMAIL_RECORD,fields= {"email","day"})
	public Long addEmailRecord(String email,String day) {
		EmailRecord record=new EmailRecord();
		record.setDay(day);
		record.setEmail(email);
		record.setSended(1);
		return jdbcHandle.saveOrUpdateAuto(record, "sended");
	}
}
