package org.coody.framework.context.base;


import org.apache.log4j.Logger;
import org.coody.framework.util.AspectUtil;
import org.coody.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

/**
 * 日志
 * 
 * @author Max
 * 
 */
public class BaseLogger {

	private Logger logger;

	public BaseLogger(Logger logger) {
		this.logger = logger;
	}
	

	/**
	 * 获取日志
	 * @param clazz
	 * @return
	 */
	public static BaseLogger getLogger(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz);
		return new BaseLogger(logger);
	}
	
	/**
	 * 获取日志-生产环境日志级别ERROR
	 * @param clazz
	 * @return
	 */
	public static BaseLogger getLoggerPro(Class<?> clazz) {
		Logger logger = Logger.getLogger(clazz);
		return new BaseLogger(logger);
	}

	/**
	 * 获取日志
	 * @param name
	 * @return
	 */
	public static BaseLogger getLogger(String name) {
		Logger logger = Logger.getLogger(name);
		return new BaseLogger(logger);
	}
	
	
	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public void info(Object message) {
		if (message instanceof String) {
			logger.info(getCurrModule() + message);
			return;
		}
		logger.info(getCurrModule() + JSON.toJSONString(message));
	}

	public void debug(Object message) {
		try {
			if (message instanceof String) {
				logger.debug(getCurrModule() + message);
				return;
			}
			logger.debug(getCurrModule() + JSON.toJSONString(message));
		} catch (Exception e) {
		}
	}

	public void error(Object message) {
		try {
			if (message instanceof String) {
				logger.error(getCurrModule() + message);
				return;
			}
			logger.error(getCurrModule() + JSON.toJSONString(message));
		} catch (Exception e) {
		}
	}

	public void error(Object message, Throwable t) {
		try {
			if (message instanceof String) {
				logger.error(getCurrModule() + message, t);
				return;
			}
			logger.error(getCurrModule() + JSON.toJSONString(message), t);
		} catch (Exception e) {
		}
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	/**
	 * 获取日志所属模块名
	 * 
	 * @return
	 */
	private String getCurrModule() {
		String module = AspectUtil.getCurrLog();
		if (StringUtil.isNullOrEmpty(module)) {
			return "";
		}
		return "[" + module + "]";
	}
}
