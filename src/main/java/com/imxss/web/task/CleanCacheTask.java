package com.imxss.web.task;

import org.coody.framework.context.annotation.LogHead;
import org.coody.framework.core.cache.LocalCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanCacheTask {

	@Scheduled(cron="0 0 0,12 * * ?")
	@LogHead("刷新文章类别任务")
	public void cleanCache() {
		LocalCache.clearCache();
	}
}
