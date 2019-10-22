package org.coody.framework.context.wrapper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 覆写Request方法，过滤XSS恶意脚本
 * 
 * @author Coody
 * @time 2015-06-09
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {


	HttpServletRequest orgRequest = null;

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		orgRequest = request;
	}
	public String getContentType(){
		String contextType=super.getContentType();
		if(contextType!=null){
			contextType=strutsExpFilter(contextType);
		}
		return contextType;
	}
	/**
	 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
	 */
	@Override
	public String getParameter(String name) {
		
		String value = super.getParameter(name);
		if (value != null) {
			value = strutsExpFilter(value);
		}
		return value;
	}

	/**
	 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。 避免部分head操作引发的xss
	 */
	@Override
	public String getHeader(String name) {

		String value = super.getHeader(name);
		if (value != null) {
			value = strutsExpFilter(value);
		}
		return value;
	}

	/**
	 * 覆盖getHeaderNames方法，避免穷举head参数名引发的xss
	 */
	@Override
	public Enumeration<String> getHeaderNames() {

		Enumeration<String> headNames = super.getHeaderNames();
		String value = null;
		List<String> values = new ArrayList<String>();
		while (headNames.hasMoreElements()) {
			try {
				value = (String) headNames.nextElement();
				if (value == null) {
					continue;
				}
				value = strutsExpFilter(value);
				values.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (values.isEmpty()) {
			return null;
		}
		headNames = new XssEnumerator(0, values.size(), values);
		return headNames;
	}

	/**
	 * 覆盖getParameterNames方法，避免穷举参数名引发的xss
	 */
	@Override
	public Enumeration<String> getParameterNames() {
		Enumeration<String> paraNames = super.getParameterNames();
		if (paraNames == null) {
			return null;
		}
		String value = null;
		List<String> values = new ArrayList<String>();
		while (paraNames.hasMoreElements()) {
			try {
				value = (String) paraNames.nextElement();
				if (value == null) {
					continue;
				}
				value = strutsExpFilter(value);
				values.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (values.isEmpty()) {
			return null;
		}
		paraNames = new XssEnumerator(0, values.size(), values);
		return paraNames;
	}

	/**
	 * 覆盖getParameterMap方法，避免穷举参数名或值引发的xss
	 */
	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = super.getParameterMap();
		Map<String, String[]> paraMap = new HashMap<String, String[]>();
		if (map == null) {
			return null;
		}
		String[] values = null;
		for (String key : map.keySet()) {
			try {
				values = map.get(key);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						try {
							values[i] = strutsExpFilter(values[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				paraMap.put(strutsExpFilter(key), values);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return paraMap;
	}

	/**
	 * 将容易引起xss漏洞的字符清理掉
	 * 
	 * @param s
	 * @return
	 */
	private static String strutsExpFilter(String value) {
		if (value != null) {

			value = value.replace("<", "&lt;");
			value = value.replace(">", "&gt;");
			/*
			 * // 如需开启富文本请撤销以下注释
			 * 
			 * Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
			 * Pattern.CASE_INSENSITIVE); value =
			 * scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("</script>",
			 * Pattern.CASE_INSENSITIVE); value =
			 * scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("<img.*?on.*?=.*?>",
			 * Pattern.CASE_INSENSITIVE); value =
			 * scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("<script(.*?)>",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("eval\\((.*?)\\)",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("expression\\((.*?)\\)",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("javascript:",
			 * Pattern.CASE_INSENSITIVE); value =
			 * scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("vbscript:",
			 * Pattern.CASE_INSENSITIVE); value =
			 * scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("onload(.*?)=",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("<%.*?java.*?%>",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("<jsp:.*?>.*?</jsp:.*?>",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 * 
			 * scriptPattern = Pattern.compile("<meta.*?>",
			 * Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			 * value = scriptPattern.matcher(value).replaceAll("");
			 */

		}
		return value;
	}

	/**
	 * 获取最原始的request
	 * 
	 * @return
	 */
	public HttpServletRequest getOrgRequest() {

		return orgRequest;
	}

	/**
	 * 获取最原始的request的静态方法
	 * 
	 * @return
	 */
	public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
		if (req instanceof XssHttpServletRequestWrapper) {
			return ((XssHttpServletRequestWrapper) req).getOrgRequest();
		}

		return req;
	}

	private class XssEnumerator implements Enumeration<String> {
		int count; // 计数器
		int length; // 存储的数组的长度
		List<String> dataArray; // 存储数据数组的引用

		XssEnumerator(int count, int length, List<String> dataArray) {
			this.count = count;
			this.length = length;
			this.dataArray = dataArray;

		}

		public boolean hasMoreElements() {
			return (count < length);
		}

		public String nextElement() {
			return dataArray.get(count++);
		}
	}
}