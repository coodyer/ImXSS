package org.coody.framework.context.entity;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class TaskEntity extends BaseModel{
		
		private String serverId;
		
		private Integer status;
		
		private String serverName;
		
		private String rootPath;
		
		private String methodKey;
		
		private String cron;
		
		private String lastRunTime;
		
		

		public String getLastRunTime() {
			return lastRunTime;
		}

		public void setLastRunTime(String lastRunTime) {
			this.lastRunTime = lastRunTime;
		}

		public String getCron() {
			return cron;
		}

		public void setCron(String cron) {
			this.cron = cron;
		}

		public String getMethodKey() {
			return methodKey;
		}

		public void setMethodKey(String methodKey) {
			this.methodKey = methodKey;
		}

		public String getServerId() {
			return serverId;
		}

		public void setServerId(String serverId) {
			this.serverId = serverId;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getServerName() {
			return serverName;
		}

		public void setServerName(String serverName) {
			this.serverName = serverName;
		}

		public String getRootPath() {
			return rootPath;
		}

		public void setRootPath(String rootPath) {
			this.rootPath = rootPath;
		}
		
		
	}