package org.coody.framework.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.coody.framework.constant.GeneralFinal;
import org.coody.framework.context.annotation.LogHead;
import org.coody.framework.context.entity.BeanEntity;

public class AspectUtil {

	public static ThreadLocal<Map<String, Object>> moduleThread = new ThreadLocal<Map<String, Object>>();

	public static String getMethodLog(Method method) {
		LogHead handle = method.getAnnotation(LogHead.class);
		if (handle == null) {
			return null;
		}
		return handle.value();
	}

	public static String getClassLog(Class<?> clazz) {
		LogHead handle = clazz.getAnnotation(LogHead.class);
		if (handle == null) {
			return clazz.getSimpleName();
		}
		return handle.value();
	}

	public static void writeLog(String module) {
		Map<String, Object> record = getCurrRecord();
		record.put(GeneralFinal.LOGGER_WRAPPER, module);
		moduleThread.set(record);
	}

	public static Map<String, Object> getCurrRecord() {
		Map<String, Object> record = moduleThread.get();
		if (StringUtil.isNullOrEmpty(record)) {
			record = new HashMap<String, Object>();
		}
		return record;
	}

	public static String getCurrLog() {
		Map<String, Object> record = getCurrRecord();
		String logHead = (String) record.get(GeneralFinal.LOGGER_WRAPPER);
		if (logHead == null) {
			return "";
		}
		return logHead;
	}

	public static String minusLog() {
		Map<String, Object> record = getCurrRecord();
		String logHead = (String) record.get(GeneralFinal.LOGGER_WRAPPER);
		if (logHead == null) {
			return "";
		}
		String tabs[] = logHead.split("_");
		if (tabs.length == 1) {
			record.put(GeneralFinal.LOGGER_WRAPPER, "");
			moduleThread.set(record);
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tabs.length - 1; i++) {
			if (!StringUtil.isNullOrEmpty(sb)) {
				sb.append("_");
			}
			sb.append(tabs[i]);
		}
		record.put(GeneralFinal.LOGGER_WRAPPER, sb.toString());
		moduleThread.set(record);
		return sb.toString();
	}

	public static String getFieldKey(Class<?> clazz,Method method, Object[] paras,
			String key, String[] fields){
		if(StringUtil.isNullOrEmpty(key)){
			key=SimpleUtil.getMethodKey(clazz,method);
			key=key.replace(".", "_");
			key=key.replace(",", "_");
		}
		StringBuilder paraKey = new StringBuilder();
		for (String field : fields) {
			Object paraValue = AspectUtil.getMethodPara(method, field, paras);
			if (StringUtil.isNullOrEmpty(paraValue)) {
				paraValue = "";
			}
			paraKey.append("_")
					.append(JSONWriter.write(paraValue));
		}
		key=key+"_"+EncryptUtil.md5Code(paraKey.toString());
		return key;
	}
	// 将对象内所有字段名、字段值拼接成字符串，用于缓存Key
	public static String getBeanKey(Object... obj) {
		if (StringUtil.isNullOrEmpty(obj)) {
			return "";
		}
		String str = JSONWriter.write(obj);
		return EncryptUtil.md5Code(str);
	}
	public static Object getMethodPara(Method method, String fieldName,
			Object[] args) {
		List<BeanEntity> beanEntitys = PropertUtil.getMethodParas(method);
		if (StringUtil.isNullOrEmpty(beanEntitys)) {
			return "";
		}
		String[] fields = fieldName.split("\\.");
		BeanEntity entity = (BeanEntity) PropertUtil.getByList(
				beanEntitys, "fieldName", fields[0]);
		if (StringUtil.isNullOrEmpty(entity)) {
			return "";
		}
		Object para = args[beanEntitys.indexOf(entity)];
		if (fields.length > 1 && para != null) {
			for (int i = 1; i < fields.length; i++) {
				para = PropertUtil.getFieldValue(para, fields[i]);
			}
		}
		return para;
	}

	@SuppressWarnings("unchecked")
	public static String getCurrDBTemplate() {
		Map<String, Object> record = getCurrRecord();
		List<String> dbTemplates = (List<String>) record
				.get(GeneralFinal.JDBC_WRAPPER);
		if (StringUtil.isNullOrEmpty(dbTemplates)) {
			return null;
		}
		return dbTemplates.get(dbTemplates.size() - 1);
	}

	@SuppressWarnings("unchecked")
	public static void writeDBTemplate(String dbTemplate) {
		Map<String, Object> record = getCurrRecord();
		List<String> dbTemplates = (List<String>) record
				.get(GeneralFinal.JDBC_WRAPPER);
		if (StringUtil.isNullOrEmpty(dbTemplates)) {
			dbTemplates = new ArrayList<String>();
		}
		dbTemplates.add(dbTemplate);
		record.put(GeneralFinal.JDBC_WRAPPER, dbTemplates);
		moduleThread.set(record);
	}

	@SuppressWarnings("unchecked")
	public static void minusDBTemplate() {
		Map<String, Object> record = getCurrRecord();
		List<String> dbTemplates = (List<String>) record
				.get(GeneralFinal.JDBC_WRAPPER);
		if (dbTemplates == null) {
			return;
		}
		if (dbTemplates.size() <= 1) {
			record.put(GeneralFinal.JDBC_WRAPPER, null);
			moduleThread.set(record);
			return;
		}
		dbTemplates.remove(dbTemplates.size() - 1);
		record.put(GeneralFinal.JDBC_WRAPPER, dbTemplates);
		moduleThread.set(record);
	}


	public static void createDebugKey(String method) {
		Map<String, Object> record = getCurrRecord();
		record.put(GeneralFinal.SIMPLE_WRAPPER, method);
		moduleThread.set(record);
	}

	public static String getDebugKey() {
		Map<String, Object> record = getCurrRecord();
		Object method = record.get(GeneralFinal.SIMPLE_WRAPPER);
		return (String) method;
	}

	public static void cleanDebugKey() {
		Map<String, Object> record = getCurrRecord();
		record.remove(GeneralFinal.SIMPLE_WRAPPER);
		record.remove(GeneralFinal.LOG_WRAPPER);
		moduleThread.set(record);
	}
}
