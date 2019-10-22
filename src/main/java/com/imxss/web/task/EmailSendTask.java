package com.imxss.web.task;

import java.util.List;

import javax.annotation.Resource;

import org.coody.framework.context.annotation.LogHead;
import org.coody.framework.core.thread.ThreadBlockHandle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imxss.web.domain.EmailQueue;
import com.imxss.web.install.InstallHandle;
import com.imxss.web.service.EmailService;

@Component
public class EmailSendTask {

	@Resource
	EmailService emailService;
	
	
	@Scheduled(cron="0/2 * * * * ? ")
	@LogHead("发送邮件队列")
	public synchronized void sendEmailTask(){
		if(!InstallHandle.isInstall()){
			return;
		}
		List<EmailQueue> queues=emailService.getEmailQueues();
		if(queues==null){
			return;
		}
		ThreadBlockHandle handle=new ThreadBlockHandle();
		for(EmailQueue queue:queues){
			try {
				Runnable runn=new Runnable() {
					@Override
					public void run() {
						emailService.sendEmail(queue);
					}
				};
				handle.pushTask(runn);
			} catch (Exception e) {
			}
		}
		handle.execute();
	}
	
	@Scheduled(cron="0 0/1 * * * ? ")
	@LogHead("处理过期的邮件")
	public synchronized void updateErrorEmailTask(){
		emailService.updateErrorEmailTask();
	}
	
}
