package org.coody.framework.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.coody.framework.context.annotation.CacheWipe;
import org.coody.framework.context.annotation.CacheWrite;
import org.coody.framework.context.annotation.DateSource;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.MonitorEntity;
import org.coody.framework.core.cache.LocalCache;
import org.coody.framework.util.AspectUtil;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.SimpleUtil;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;

@Aspect
@Component
public class AppAspect {

	private final BaseLogger logger = BaseLogger.getLoggerPro(this.getClass());
	/**
	 * 新版本迭代控制未测试状态方法,屏蔽报错
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.coody.framework.context.annotation.DeBug)")
	public Object fDeBugMonitor(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			try {
				return pjp.proceed();
			} catch (Exception e) {
				PrintException.printException(logger, e);
				return null;
			}
		} finally {
			sw.stop();
		}
	}

	/**
	 * 数据库主从控制
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.coody.framework.context.annotation.DateSource)")
	public Object eDbMonitor(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			DateSource handle = method.getAnnotation(DateSource.class);
			AspectUtil.writeDBTemplate(handle.value());
			return pjp.proceed();
		} finally {
			AspectUtil.minusDBTemplate();
			sw.stop();
		}
	}

	/**
	 * 写缓存操作
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.coody.framework.context.annotation.CacheWrite)")
	public Object cCacheWrite(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> clazz=pjp.getTarget().getClass();
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			// 获取注解
			CacheWrite handle = method.getAnnotation(CacheWrite.class);
			if (handle == null) {
				return pjp.proceed();
			}
			// 封装缓存KEY
			Object[] paras = pjp.getArgs();
			String key = handle.key();
			try {
				if (StringUtil.isNullOrEmpty(key)) {
					key = SimpleUtil.getMethodCacheKey(clazz,method);
				}
				if (StringUtil.isNullOrEmpty(handle.fields())) {
					String paraKey=AspectUtil.getBeanKey(paras);
					if(!StringUtil.isNullOrEmpty(paraKey)){
						key += ":";
						key += paraKey;
					}
				}
				if (!StringUtil.isNullOrEmpty(handle.fields())) {
					key = AspectUtil.getFieldKey(clazz,method, paras, key,
							handle.fields());
				}
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
			Integer cacheTimer = ((handle.time() == 0) ? 24 * 3600
					: handle.time());
			// 获取缓存
			try {
				Object result = LocalCache.getCache(key);
				logger.debug("获取缓存:"+key+",结果:"+result);
				if (!StringUtil.isNullOrEmpty(result)) {
					return result;
				}
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
			Object result = pjp.proceed();
			if (result != null) {
				try {
					LocalCache.setCache(key, result, cacheTimer);
					logger.debug("设置缓存:"+key+",结果:"+result+",缓存时间:"+cacheTimer);
				} catch (Exception e) {
				}
			}
			return result;
		} finally {
			sw.stop();
		}
	}

	/**
	 * 缓存清理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.coody.framework.context.annotation.CacheWipe)||@annotation(org.coody.framework.context.annotation.CacheWipes)")
	public Object zCacheWipe(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// 启动监听
			sw.start(pjp.getSignature().toShortString());
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> clazz=pjp.getTarget().getClass();
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			Object[] paras = pjp.getArgs();
			Object result = pjp.proceed();
			CacheWipe[] handles = method.getAnnotationsByType(CacheWipe.class);
			if (StringUtil.isNullOrEmpty(handles)) {
				return result;
			}
			for (CacheWipe handle : handles) {
				try {
					String key = handle.key();
					if (StringUtil.isNullOrEmpty(handle.key())) {
						key = (SimpleUtil.getMethodCacheKey(clazz,method));
					}
					if (!StringUtil.isNullOrEmpty(handle.fields())) {
						key = AspectUtil.getFieldKey(clazz,method, paras, key,
								handle.fields());
					}
					logger.debug("删除缓存:"+key);
					LocalCache.delCache(key);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
			}
			return result;
		} finally {
			sw.stop();
		}
	}

	/**
	 * 日志管理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.coody.framework.context.annotation.LogHead)")
	public Object gLoggerMonitor(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			Class<?> clazz = PropertUtil.getClass(method);
			String module = AspectUtil.getCurrLog();
			if (!StringUtil.isNullOrEmpty(module)) {
				module += "_";
			}
			String classLog = AspectUtil.getClassLog(clazz);
			if (!StringUtil.isNullOrEmpty(classLog)) {
				module += classLog;
			}
			if (!StringUtil.isNullOrEmpty(module)) {
				module += ".";
			}
			String methodLog = AspectUtil.getMethodLog(method);
			if (!StringUtil.isNullOrEmpty(methodLog)) {
				module += methodLog;
			} else {
				module += method.getName();
			}
			AspectUtil.writeLog(module);
			return pjp.proceed();
		} finally {
			AspectUtil.minusLog();
			sw.stop();
		}
	}

	@SuppressWarnings("unchecked")
	@Around("execution(* com.imxss.web..*.*(..)))||execution(* org.coody.framework..*.*(..)))")
	public Object aServiceMonitor(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> clazz=pjp.getTarget().getClass();
			Method method = methodSignature.getMethod();
			PropertUtil.setProperties(method, "clazz", clazz);
			String key = SimpleUtil.getMethodKey(clazz,method);
			if (LocalCache.contains(key)) {
				Object[] args = pjp.getArgs();
				Date runTime = new Date();
				Object result = null;
				try {
					result = pjp.proceed();
				} catch (Exception e) {
					result=PrintException.getErrorStack(e);
				}
				Date resultTime = new Date();
				try {
					String input = getJson(args);
					String output = getJson(result);
					MonitorEntity entity = new MonitorEntity();
					entity.setInput(input);
					entity.setOutput(output);
					entity.setRunTime(runTime);
					entity.setResultTime(resultTime);
					List<MonitorEntity> entitys = (List<MonitorEntity>) LocalCache
							.getCache(key);
					if (StringUtil.isNullOrEmpty(entitys)) {
						entitys = new ArrayList<MonitorEntity>();
					}
					entitys.add(entity);
					LocalCache.setCache(key, entitys);
				} catch (Exception e) {
					PrintException.printException(logger, e);
				}
				return result;
			}
			Object result = pjp.proceed();
			return result;
		} finally {
			sw.stop();
		}
	}
	
	
	


	private static String getJson(Object... args) {
		if (StringUtil.isNullOrEmpty(args)) {
			return "";
		}
		List<Object> newArgs = new ArrayList<Object>();
		for (Object arg : args) {
			Object tmp = arg;
			if (arg != null) {
				if (ServletRequest.class.isAssignableFrom(arg.getClass())
						|| ServletResponse.class.isAssignableFrom(arg
								.getClass())) {
					tmp = arg.getClass();
				}
			}
			newArgs.add(tmp);
		}
		return JSON.toJSONString(newArgs);
	}

	public static void main(String[] args) {
	}
}
