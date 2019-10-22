package org.coody.framework.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.coody.framework.constant.GeneralFinal;
import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.base.BaseModel;
import org.coody.framework.context.entity.CtAnnotationEntity;
import org.coody.framework.context.entity.CtBeanEntity;
import org.coody.framework.context.entity.CtClassEntity;
import org.coody.framework.context.entity.CtMethodEntity;
import org.springframework.cglib.proxy.MethodProxy;

import com.alibaba.fastjson.JSON;

public class SimpleUtil {


	public static String getMethodCacheKey(Class<?> clazz,Method method){
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getName()).append(".").append(method.getName());
		Class<?>[] paraTypes = method.getParameterTypes();
		sb.append("(");
		if (!StringUtil.isNullOrEmpty(paraTypes)) {
			for (int i = 0; i < paraTypes.length; i++) {
				sb.append(paraTypes[i].getName());
				if (i < paraTypes.length - 1) {
					sb.append(",");
				}
			}
		}
		sb.append(")");
		return  sb.toString();
	}
	public static String getMethodKey(Class<?> clazz, Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getName()).append(".").append(method.getName());
		Class<?>[] paraTypes = method.getParameterTypes();
		sb.append("(");
		if (!StringUtil.isNullOrEmpty(paraTypes)) {
			for (int i = 0; i < paraTypes.length; i++) {
				sb.append(paraTypes[i].getName());
				if (i < paraTypes.length - 1) {
					sb.append(",");
				}
			}
		}
		sb.append(")");
		return GeneralFinal.SYSTEM_RUN_INFO + "-" + sb.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Method getMethodByKey(String key) {
		String classKey = StringUtil.textCutCenter(key,
				GeneralFinal.SYSTEM_RUN_INFO + "-", "(");
		String[] tabs = classKey.split("\\.");
		List<String> list = new ArrayList(Arrays.<String> asList(tabs));
		list.remove(list.size() - 1);
		classKey = StringUtil.collectionMosaic(list, ".");
		try {
			Class<?> clazz = Class.forName(classKey);
			List<Method> methods = loadMethods(clazz);
			if (!StringUtil.isNullOrEmpty(methods)) {
				PropertUtil.setFieldValues(methods, "clazz", clazz);
			}
			for (Method method : methods) {
				String methodKey=getMethodKey(clazz,method);
				if (key.equals(methodKey)) {
					return method;
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class<?> getMethodClassByKey(String key) {
		String classKey = StringUtil.textCutCenter(key,
				GeneralFinal.SYSTEM_RUN_INFO + "-", "(");
		String[] tabs = classKey.split("\\.");
		List<String> list = new ArrayList(Arrays.<String> asList(tabs));
		list.remove(list.size() - 1);
		classKey = StringUtil.collectionMosaic(list, ".");
		try {
			Class<?> clazz = Class.forName(classKey);
			List<Method> methods = loadSourceMethods(clazz);
			for (Method method : methods) {
				if (key.equals(getMethodKey(clazz, method))) {
					return method.getDeclaringClass();
				}
			}
		} catch (Exception e) {

		}
		return null;
	}

	public static List<CtAnnotationEntity> getCtAnnotations(
			Annotation[] annotations) {
		List<CtAnnotationEntity> ctAnnotations = new ArrayList<CtAnnotationEntity>();
		for (Annotation annotation : annotations) {
			CtAnnotationEntity ctAnnotation = new CtAnnotationEntity();
			ctAnnotation.setClazz(annotation.getClass());
			ctAnnotation.setAnnotation(annotation);
			try {
				Method[] mes = annotation.annotationType().getDeclaredMethods();
				if (StringUtil.isNullOrEmpty(mes)) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				Object [] args=null;
				for (Method me : mes) {
					if (!me.isAccessible()) {
						me.setAccessible(true);
					}
					Object value = me.invoke(annotation, args);
					if (StringUtil.isNullOrEmpty(value)) {
						continue;
					}
					if(Annotation[].class.isAssignableFrom(value.getClass())){
						List<CtAnnotationEntity> tmpAnnotations=getCtAnnotations((Annotation[])value);
						if(!StringUtil.isNullOrEmpty(tmpAnnotations)){
							ctAnnotations.addAll(tmpAnnotations);
						}
					}
					map.put(me.getName(), JSON.toJSONString(value));
				}
				if (StringUtil.isNullOrEmpty(map)) {
					continue;
				}
				ctAnnotation.setFields(map);
			} catch (Exception e) {

			} finally {
				ctAnnotations.add(ctAnnotation);
			}
		}
		return ctAnnotations;
	}

	public static List<CtBeanEntity> getClassFields(Class<?> cla) {
		try {
			List<Field> fields = PropertUtil.loadFields(cla);
			List<CtBeanEntity> infos = new ArrayList<CtBeanEntity>();
			for (Field f : fields) {
				if (f.getName().equalsIgnoreCase("serialVersionUID")) {
					continue;
				}
				CtBeanEntity tmp = new CtBeanEntity();
				tmp.setSourceField(f);
				tmp.setAnnotations(getCtAnnotations(f));
				tmp.setFieldName(f.getName());
				tmp.setFieldType(f.getType());
				tmp.setIsFinal(Modifier.isFinal(f.getModifiers()));
				tmp.setModifier(f.getModifiers());
				tmp.setIsStatic(Modifier.isStatic(f.getModifiers()));
				try {
					if (tmp.getIsStatic()) {
						f.setAccessible(true);
						tmp.setFieldValue(f.get(null));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				infos.add(tmp);
			}
			return infos;
		} catch (Exception e) {

			return null;
		}
	}

	/**
	 * 获取class的字段列表
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> loadFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		Field[] fieldArgs = clazz.getDeclaredFields();
		for (Field f : fieldArgs) {
			fields.add(f);
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return fields;
		}
		fields.addAll(loadFields(superClass));
		return fields;
	}

	public static List<CtAnnotationEntity> getCtAnnotations(Class<?> clazz) {
		return getCtAnnotations(clazz.getAnnotations());
	}

	public static List<CtAnnotationEntity> getCtAnnotations(
			AccessibleObject accessible) {
		return getCtAnnotations(accessible.getDeclaredAnnotations());
	}

	public static List<CtBeanEntity> getBeanFields(Object obj) {
		Class<? extends Object> cla = PropertUtil.getObjClass(obj);
		List<CtBeanEntity> infos = getClassFields(cla);
		if (StringUtil.isNullOrEmpty(infos)) {
			return infos;
		}
		if (obj instanceof java.lang.Class) {
			return infos;
		}
		for (CtBeanEntity info : infos) {
			try {
				Field f = info.getSourceField();
				f.setAccessible(true);
				if(isFrom(info.getFieldType(), ServletRequest.class,ServletResponse.class,BaseLogger.class)){
					info.setFieldValue(JSON.toJSONString(info.getFieldType()));
					continue;
				}
				Object value = f.get(obj);
				info.setFieldValue(JSON.toJSONString(value));
			} catch (Exception e) {

			}
		}
		return infos;
	}

	private static boolean isFrom(Class<?> currClass,Class<?>...classes){
		if(StringUtil.hasNull(currClass,classes)){
			return false;
		}
		for(Class<?> clazz:classes){
			if(clazz.isAssignableFrom(currClass)){
				return true;
			}
		}
		return false;
	}
	public static List<CtBeanEntity> getMethodParas(Method method) {
		try {
			Class<?>[] types = method.getParameterTypes();
			if (StringUtil.isNullOrEmpty(types)) {
				return null;
			}
			List<String> paraNames=PropertUtil.getMethodParaNames(method);
			if (StringUtil.isNullOrEmpty(paraNames)) {
				return null;
			}
			Annotation [][] paraAnnotations=method.getParameterAnnotations();
			List<CtBeanEntity> entitys = new ArrayList<CtBeanEntity>();
			for (int i = 0; i < paraNames.size(); i++) {
				CtBeanEntity entity = new CtBeanEntity();
				entity.setFieldName(paraNames.get(i));
				entity.setAnnotations(getCtAnnotations(paraAnnotations[i]));
				entity.setFieldType(types[i]);
				entitys.add(entity);
			}
			return entitys;
		} catch (Exception e) {

		}
		return null;
	}

	public static CtClassEntity getClassEntity(Class<?> clazz) {
		CtClassEntity entity = new CtClassEntity();
		entity.setSourceClass(clazz);
		Object obj=null;
		try {
			obj=SpringContextHelper.getBean(clazz);
			obj=clazz.cast(obj);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(obj==null){
			obj=clazz;
		}
		List<CtBeanEntity> fields = getBeanFields(obj);
		entity.setFields(fields);
		entity.setAnnotations(getCtAnnotations(clazz));
		entity.setName(clazz.getName());
		if (Modifier.isInterface(clazz.getModifiers())) {
			try {
				List<Class<?>> apiClazzs = getAllAssignedClass(clazz);
				if (!StringUtil.isNullOrEmpty(apiClazzs)) {
					clazz = apiClazzs.get(0);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<Method> methods = loadMethods(clazz);
		List<CtMethodEntity> ctMethods = new ArrayList<CtMethodEntity>();
		for (Method method : methods) {
			CtMethodEntity ctMethod = new CtMethodEntity();
			if (!StringUtil.isNullOrEmpty(method.getDeclaredAnnotations())) {
				ctMethod.setAnnotations(getCtAnnotations(method));
			}
			ctMethod.setSourceMethod(method);
			ctMethod.setName(method.getName());
			ctMethod.setReturnType(method.getReturnType());
			ctMethod.setParamsType(getMethodParas(method));
			ctMethod.setIsFinal(Modifier.isFinal(method.getModifiers()));
			ctMethod.setModifier(method.getModifiers());
			ctMethod.setIsStatic(Modifier.isStatic(method.getModifiers()));
			ctMethod.setIsAbstract(Modifier.isAbstract(method.getModifiers()));
			ctMethod.setIsSynchronized(Modifier.isSynchronized(method
					.getModifiers()));
			ctMethod.setKey(getMethodKey(clazz,method));
			ctMethods.add(ctMethod);
		}
		if(!StringUtil.isNullOrEmpty(ctMethods)){
			ctMethods=PropertUtil.doSeq(ctMethods, "name");
		}
		entity.setMethods(ctMethods);
		entity.setIsAbstract(Modifier.isAbstract(clazz.getModifiers()));
		entity.setIsEnum(Enum.class.isAssignableFrom(clazz));
		entity.setIsInterface(Modifier.isInterface(clazz.getModifiers()));
		entity.setModifier(clazz.getModifiers());
		entity.setIsFinal(Modifier.isFinal(clazz.getModifiers()));
		entity.setSuperClass(clazz.getSuperclass());
		entity.setInterfaces(clazz.getInterfaces());
		if(Enum.class.isAssignableFrom(clazz)){
			entity.setEnumInfo(PropertUtil.loadEnumRecord(clazz));
		}
		return entity;
	}

	public static Object initMethodParas(Method method) {
		List<CtBeanEntity> entitys = getMethodParas(method);
		if (StringUtil.isNullOrEmpty(entitys)) {
			return new Object[] {};
		}
		List<Object> paras = new ArrayList<Object>();
		for (CtBeanEntity entity : entitys) {
			paras.add(initPara(entity.getFieldType()));
		}
		return paras.toArray();
	}

	public static boolean isWindows(){
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if(os.toLowerCase().startsWith("win") ){
			return true;
		}
		return false;
	}
	public static List<Method> loadMethods(Class<?> clazz) {
		List<Method> methods = new ArrayList<Method>();
		List<Method> chuckMethods=Arrays.<Method> asList(clazz.getDeclaredMethods());
		for(Method method:chuckMethods){
			try {
				Class<?> methodClazz=PropertUtil.getClass(method);
				if(methodClazz==Object.class){
					continue;
				}
				methods.add(method);
			} catch (Exception e) {
			}
		}
		if (!StringUtil.isNullOrEmpty(clazz.getSuperclass())) {
			List<Method> methodTmps = loadMethods(clazz.getSuperclass());
			if (!StringUtil.isNullOrEmpty(methodTmps)) {
				//PropertUtil.setFieldValues(methodTmps, "clazz", clazz);
				methods.addAll(methodTmps);
			}
		}
		return methods;
	}

	static List<Class<?>> generalTypes=Arrays.asList(new Class<?>[]{boolean.class,byte.class,char.class,short.class,int.class,float.class,long.class,double.class,
			Integer.class,Float.class,Long.class,Double.class,Short.class,Byte.class,Boolean.class,String.class,Date.class,Number.class,Enum.class,MethodProxy.class});
	
	public static boolean isSignType(Class<?> clazz){
		if(clazz.isArray()){
			return false;
		}
		if(clazz.isAnnotation()){
			return false;
		}
		if(clazz.isAnonymousClass()){
			return false;
		}
		if(clazz.isInterface()){
			return false;
		}
		
		if(generalTypes.contains(clazz)){
			return true;
		}
		for(Class<?> cla:generalTypes){
			if(cla.isAssignableFrom(clazz)){
				return true;
			}
		}
		return false;
	}
	public static List<Method> loadSourceMethods(Class<?> clazz) {
		List<Method> methods = new ArrayList<Method>(
				Arrays.<Method> asList(clazz.getDeclaredMethods()));
		if (!StringUtil.isNullOrEmpty(clazz.getSuperclass())) {
			List<Method> methodTmps = loadMethods(clazz.getSuperclass());
			if (!StringUtil.isNullOrEmpty(methodTmps)) {
				methods.addAll(methodTmps);
			}
		}
		return methods;
	}

	private static Object initPara(Class<?> clazz) {
		if (StringUtil.isNullOrEmpty(clazz)) {
			return null;
		}
		try {
			if (BaseModel.class.isAssignableFrom(clazz)) {
				Object object = clazz.newInstance();
				List<CtBeanEntity> entitys = getBeanFields(clazz);
				for (CtBeanEntity entity : entitys) {
					try {
						PropertUtil.setProperties(object,
								entity.getFieldName(),
								getValue(entity.getFieldType()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				return object;
			}
			return getValue(clazz);
		} catch (Exception e) {
			return "";
		}
	}

	private static Object getValue(Class<?> clazz) {
		try {
			if (clazz.isPrimitive()) {
				if (boolean.class.isAssignableFrom(clazz)) {
					return false;
				}
				if (byte.class.isAssignableFrom(clazz)) {
					return 0;
				}
				if (char.class.isAssignableFrom(clazz)) {
					return 0;
				}
				if (short.class.isAssignableFrom(clazz)) {
					return 0;
				}
				if (int.class.isAssignableFrom(clazz)) {
					return 0;
				}
				if (float.class.isAssignableFrom(clazz)) {
					return 0f;
				}
				if (long.class.isAssignableFrom(clazz)) {
					return 0l;
				}
				if (double.class.isAssignableFrom(clazz)) {
					return 0d;
				}
			}
			if (Boolean.class.isAssignableFrom(clazz)) {
				return false;
			}
			if (Integer.class.isAssignableFrom(clazz)) {
				return 0;
			}
			if (Float.class.isAssignableFrom(clazz)) {
				return 0f;
			}
			if (Long.class.isAssignableFrom(clazz)) {
				return 0l;
			}
			if (Double.class.isAssignableFrom(clazz)) {
				return 0d;
			}
			if (String.class.isAssignableFrom(clazz)) {
				return "test";
			}
			if (Date.class.isAssignableFrom(clazz)) {
				return new Date();
			}
			if (Object[].class.isAssignableFrom(clazz)) {
				return new Object[] {};
			}
			if (clazz.isArray()) {
				return new Object[] {};
			}
			if (BaseModel.class.isAssignableFrom(clazz)) {
				return initPara(clazz);
			}
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * 获取同一路径下所有子类或接口实现类
	 * 
	 * @param intf
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getAllAssignedClass(Class<?> cls)
			throws IOException, ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (Class<?> c : getClasses(cls)) {
			if (cls.isAssignableFrom(c) && !cls.equals(c)) {
				classes.add(c);
			}
		}
		return classes;
	}

	/**
	 * 取得当前类路径下的所有类
	 * 
	 * @param cls
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClasses(Class<?> cls) throws IOException,
			ClassNotFoundException {
		String pk = cls.getPackage().getName();
		String path = pk.replace('.', '/');
		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classloader.getResource(path);
		return getClasses(new File(url.getFile()), pk);
	}

	/**
	 * 迭代查找类
	 * 
	 * @param dir
	 * @param pk
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> getClasses(File dir, String pk)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!dir.exists()) {
			return classes;
		}
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				classes.addAll(getClasses(f, pk + "." + f.getName()));
			}
			String name = f.getName();
			if (name.endsWith(".class")) {
				classes.add(Class.forName(pk + "."
						+ name.substring(0, name.length() - 6)));
			}
		}
		return classes;
	}

	public static void main(String[] args) {

	}
}
