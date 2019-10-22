package com.imxss.web.queue;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.StringUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShellQueue {

	private static BaseLogger logger=BaseLogger.getLogger(ShellQueue.class);
	
	private static ConcurrentLinkedQueue<String> ipQueue=new ConcurrentLinkedQueue<String>();
	
	private static Set<String> wallIps=new HashSet<String>();
	
	public static void writeWallIp(String ip){
		if(wallIps.contains(ip)){
			return;
		}
		try {
			ipQueue.add(ip);
		} catch (Exception e) {
			PrintException.printException(logger, e);
		}	
	}
	
	@Scheduled(cron="0/1 * * * * ? ")
	public void writeWall() throws InterruptedException{
		String ip=ipQueue.poll();
		logger.debug("防火墙运行中：");
		while(!StringUtil.isNullOrEmpty(ip)){
			try {
				String shell=MessageFormat.format("iptables -I INPUT -s {0} -j DROP", ip);
				Runtime.getRuntime().exec(shell);
				shell="service iptables save";
				Runtime.getRuntime().exec(shell);
				logger.info("封杀IP："+ip);
				Thread.sleep(20);
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}finally {
				ip=ipQueue.poll();
			}
		}
		
	}
	
	
}
