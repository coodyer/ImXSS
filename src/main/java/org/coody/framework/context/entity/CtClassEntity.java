package org.coody.framework.context.entity;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.coody.framework.context.base.BaseModel;
import org.coody.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class CtClassEntity extends BaseModel {

	private String name;

	private List<CtAnnotationEntity> annotations;

	private List<CtBeanEntity> fields;

	private List<CtMethodEntity> methods;

	private Boolean isAbstract = false;
	
	private Boolean isEnum = false;
	
	private Boolean isInterface = false;
	
	private String modifier;
	
	private Boolean isFinal = false;
	
	private Class<?> sourceClass;
	
	private Class<?> superClass;
	
	private Class<?> [] interfaces;
	
	private Map<String,String> enumInfo;
	

	public Boolean getIsFinal() {
		return isFinal;
	}


	public Class<?> getSourceClass() {
		return sourceClass;
	}


	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}


	public Class<?>[] getInterfaces() {
		return interfaces;
	}


	public void setInterfaces(Class<?>[] interfaces) {
		this.interfaces = interfaces;
	}


	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Class<?> getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Class<?> superClass) {
		this.superClass = superClass;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Boolean getIsEnum() {
		return isEnum;
	}

	public Boolean getIsInterface() {
		return isInterface;
	}

	public void setIsInterface(Boolean isInterface) {
		this.isInterface = isInterface;
	}

	public void setIsEnum(Boolean isEnum) {
		this.isEnum = isEnum;
	}

	public Boolean getIsAbstract() {
		return isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		if (Modifier.isPrivate(modifier)) {
			this.modifier = "private";
			return;
		}
		if (Modifier.isPublic(modifier)) {
			this.modifier = "public";
			return;
		}
		if (Modifier.isProtected(modifier)) {
			this.modifier = "protected";
			return;
		}
		if (Modifier.isNative(modifier)) {
			this.modifier = "native";
			return;
		}
	}

	public String getName() {
		return name;
	} 

	public void setName(String name) {
		this.name = name;
	}

	public List<CtAnnotationEntity> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<CtAnnotationEntity> annotations) {
		this.annotations = annotations;
	}

	public List<CtBeanEntity> getFields() {
		return fields;
	}

	public void setFields(List<CtBeanEntity> fields) {
		this.fields = fields;
	}

	public List<CtMethodEntity> getMethods() {
		return methods;
	}

	public void setMethods(List<CtMethodEntity> methods) {
		this.methods = methods;
	}


	public Map<String, String> getEnumInfo() {
		return enumInfo;
	}


	public void setEnumInfo(Map<String, Record> enumInfo) {
		if(StringUtil.isNullOrEmpty(enumInfo)){
			return ;
		}
		Map<String, String> map=new HashMap<String, String>();
		for(String key:enumInfo.keySet()){
			try {
				map.put(key, JSON.toJSONString(enumInfo.get(key)));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		this.enumInfo=map;
	}

}
