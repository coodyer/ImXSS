package org.coody.framework.util;

import org.coody.framework.context.base.BaseLogger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHelper implements ApplicationContextAware {

	static BaseLogger logger=BaseLogger.getLogger(SpringContextHelper.class);
	
	public static ApplicationContext context;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		int i = 0;
		while (context == null && i < 1000) {
			try {
				Thread.sleep(10);
				i++;
			} catch (InterruptedException e) {
				PrintException.printException(logger, e);
			}
		}
		return (T) context.getBean(beanName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> beanType) {
		try {
			int i = 0;
			while (context == null && i < 100) {
				try {
					Thread.sleep(10);
					i++;
				} catch (InterruptedException e) {
					PrintException.printException(logger, e);
				}
			}
			return (T) context.getBean(beanType);
		} catch (Exception e) {
			return null;
		}
	}
}
