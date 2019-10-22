package org.coody.framework.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author coody
 * @date 2017年7月24日
 * @blog http://54sb.org
 * @email 644556636@qq.com
 */
public class PropertiesPool {

	/**
	 * 配置文件地址
	 */
	private String filePath = "";
	/**
	 * 缓存容器
	 */
	private Map<String, String> propertMap = new ConcurrentHashMap<String, String>();
	/**
	 * 缓存时间，默认一个月
	 */
	private Long cacheExipre = 2592000000l;
	/**
	 * 最近一次加载时间
	 */
	private Date loadTime = new Date();

	/**
	 * @param dirPath
	 *            配置文件地址
	 */
	public PropertiesPool(String filePath) {
		this.filePath = filePath;
		reload();
	}

	/**
	 * @param dirPath
	 *            配置文件路径
	 * @param cacheTimeOut
	 *            重新加载时间 单位秒
	 */
	public PropertiesPool(String filePath, Long cacheTimeOut) {
		this.filePath = filePath;
		this.cacheExipre = cacheTimeOut * 1000;
		reload();
	}
	/**
	 * 加载属性
	 */
	public String get(String key){
		if(!propertMap.containsKey(key)){
			return null;
		}
		if(new Date().getTime()-loadTime.getTime()>cacheExipre){
			reload();
		}
		return propertMap.get(key);
	}
	
	/**
	 * 刷新配置文件
	 */
	public void reload() {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration<Object> keys=props.keys();
			Map<String, String> currMap= new ConcurrentHashMap<String, String>();
			while (keys.hasMoreElements()) {
				String key= StringUtil.toString(keys.nextElement());
				String value=StringUtil.toString(props.get(key));
				if(StringUtil.isNullOrEmpty(value)){
					continue;
				}
				currMap.put(key, value);
			}
			propertMap=currMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
