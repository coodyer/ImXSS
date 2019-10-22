package org.coody.framework.core.thread;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SysTimerHandle {
	public static final ScheduledExecutorService systemTimer = Executors
			.newScheduledThreadPool(200);
	public static void execute(TimerTask task,Integer delayed){
		try {
			systemTimer.schedule(task, delayed*1000,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		}
		
	}
}
