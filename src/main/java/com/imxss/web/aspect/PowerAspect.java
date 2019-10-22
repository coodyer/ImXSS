package com.imxss.web.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.coody.framework.context.annotation.Power;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.entity.MsgEntity;
import org.coody.framework.util.PropertUtil;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.SpringContextHelper;
import org.coody.framework.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imxss.web.domain.SysMenus;
import com.imxss.web.domain.UserInfo;
import com.imxss.web.schema.MenuSchema;
import com.imxss.web.service.MenuService;

@Aspect
@Component
public class PowerAspect {
	
	private final BaseLogger logger = BaseLogger.getLoggerPro(this.getClass());

	@SuppressWarnings("rawtypes")
	@Around("@annotation(org.coody.framework.context.annotation.Power)")
	public Object bPpowerMonitor(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			// 获取注解
			Power handle = method.getAnnotation(Power.class);
			if (handle == null || StringUtil.isNullOrEmpty(handle.value())) {
				return pjp.proceed();
			}
			// 获取当前登陆用户
			UserInfo currMember = RequestUtil.getUser(RequestUtil
					.getRequest());
			if (currMember == null) {
				return printTimeOut(method);
			}
			// 获得当前用户菜单权限
			MenuService menuService = SpringContextHelper
					.getBean(MenuService.class);
			List<SysMenus> menus = menuService.loadSourceMenus(currMember
					.getRoleId());
			List<String> codes = PropertUtil.getFieldValues(menus, "code");
			if (StringUtil.isNullOrEmpty(codes)) {
				logger.info("无权操作:"+method);
				return printPrower(method);
			}
			if (!codes.contains(handle.value())) {
				logger.info("无权操作:"+method);
				return printPrower(method);
			}
			Object result = pjp.proceed();
			//方法执行完毕，刷新菜单
			List<MenuSchema> menuSchemas= menuService.parseMenus(menus);
			Map<Object, List> menuMaps=PropertUtil.listToMaps(menuSchemas, "groupName");
			RequestUtil.getRequest().setAttribute("menuMaps", menuMaps);
			String uri= RequestUtil.getRequestURI(RequestUtil.getRequest());
			RequestUtil.getRequest().setAttribute("uri", uri);
			return result;
		} finally {
			sw.stop();
		}
	}
	
	private static Object printTimeOut(Method method) {
		ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
		if (responseBody == null) {
			return "redirect:"+RequestUtil.getRequest().getScheme()+":"+RequestUtil.loadBasePath(RequestUtil.getRequest())+"login."+RequestUtil.getRequest().getSession().getAttribute("defSuffix");
		}
		return new MsgEntity(-1, "登录超时");
	}
	private static Object printPrower(Method method) {
		ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
		if (responseBody == null) {
			return "404";
		}
		return new MsgEntity(-1, "无权操作");
	}
}
