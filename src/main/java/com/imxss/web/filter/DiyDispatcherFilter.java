package com.imxss.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.coody.framework.context.wrapper.XssHttpServletRequestWrapper;
import org.coody.framework.util.RequestUtil;
import org.coody.framework.util.SpringContextHelper;
import org.coody.framework.util.StringUtil;
import org.springframework.web.servlet.DispatcherServlet;

import com.imxss.web.domain.SettingInfo;
import com.imxss.web.install.InstallHandle;
import com.imxss.web.service.SettingService;
import com.imxss.web.service.SuffixService;

@SuppressWarnings("serial")
public class DiyDispatcherFilter extends DispatcherServlet implements Filter {

	static List<String> languageList = Arrays.<String>asList(new String[] { "ASP.NET", "ASP", "PHP/5.4.27", "JScript",
			"VB.NET", "VBScript", "CGI", "Python", "Perl", "JAVA", "ELanguage" });

	static List<String> serverList = Arrays.<String>asList(new String[] { "Microsoft-IIS/10.0", "Microsoft-IIS/9.0",
			"Microsoft-IIS/9.5", "Microsoft-IIS/3.0", "Microsoft-IIS/3.5", "Microsoft-IIS/2.0", "Microsoft-IIS/2.5",
			"Coody-Server/2.0", "Coody-Server/3.0", "Coody-Server/9.0", "Hacker-Server/2.0", "Hacker-Server/3.0",
			"Hacker-Server/4.0", "Hacker-Server/8.0", "Hacker-Server/9.0", "Hacker-Server/2.5", "Hacker-Server/3.5",
			"Hacker-Server/4.5", "Hacker-Server/8.5", "Hacker-Server/9.5", "ASP-Server/2.5", "ASP-Server/3.5",
			"ASP-Server/4.5", "ASP-Server/5.5", "Xampp-Server/2.5", "Xampp-Server/3.5", "Xampp-Server/5.5",
			"Xampp-Server/6.0", "Xampp-Server/8.5", });

	public void baseFilter(HttpServletRequest req, HttpServletResponse res) {
		try {
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			String XPBy = languageList.get(StringUtil.getRanDom(0, languageList.size() - 1));
			res.setHeader("X-Powered-By", XPBy);
			String server = serverList.get(StringUtil.getRanDom(0, serverList.size() - 1));
			res.setHeader("Server", server);
		} catch (Exception e) {
		}
	}

	public DiyDispatcherFilter() {
		super();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		baseFilter(req, res);
		if (!InstallHandle.isInstall()) {
			String basePath = loadBasePath(req);
			if (!req.getRequestURI().endsWith("/")) {
				chain.doFilter(req, res);
				return;
			}
			res.sendRedirect(basePath + "install/install.jsp");
			return;
		}
		String uri=RequestUtil.getRequestUri(req);
		if(uri.startsWith("/s/")){
			doSpringMvc(req, res);
			return;
		}
		SuffixService suffixService = SpringContextHelper.getBean(SuffixService.class);
		List<String> suffixs = suffixService.loadSpringSuffixs();
		List<String> staList = suffixService.loadStaSuffix();
		String suffix = RequestUtil.getURLSuffix(req);
		if (staList.contains(suffix)) {
			res.setHeader("Cache-Control", "max-age=600");
			chain.doFilter(req, res);
			return;
		}
		String defSuffix = (String) req.getSession().getAttribute("defSuffix");
		if (defSuffix == null) {
			defSuffix = suffixService.loadSpringDefaultSuffix();
			req.getSession().setAttribute("defSuffix", defSuffix);
		}
		if (suffixs.contains(suffix)) {
			doSpringMvc(req, res);
			return;
		}
		req.getRequestDispatcher("/WEB-INF/view/404.jsp").forward(request, response);
		;
		return;
	}

	private void doSpringMvc(HttpServletRequest req,HttpServletResponse res ) throws ServletException, IOException{
		loadBasePath(req);
		loadSetting(req);
		service(new XssHttpServletRequestWrapper(req), res);
		return;
	}
	private void loadSetting(HttpServletRequest request) {
		SettingService settingService = SpringContextHelper.getBean(SettingService.class);
		SettingInfo setting = settingService.loadSiteSetting();
		request.setAttribute("setting", setting);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(new ServletFilter(filterConfig));
	}

	private String loadBasePath(HttpServletRequest request) {
		return RequestUtil.loadBasePath(request);
	}

	private static class ServletFilter implements ServletConfig, FilterConfig {

		private FilterConfig filterConfig;

		public ServletFilter(FilterConfig filterConfig) {
			this.filterConfig = filterConfig;
		}

		public String getFilterName() {
			return filterConfig.getFilterName();
		}

		public String getInitParameter(String s) {
			return filterConfig.getInitParameter(s);
		}

		public Enumeration<String> getInitParameterNames() {
			return filterConfig.getInitParameterNames();
		}

		public ServletContext getServletContext() {
			return filterConfig.getServletContext();
		}

		public String getServletName() {
			return filterConfig.getFilterName();
		}

	}

}