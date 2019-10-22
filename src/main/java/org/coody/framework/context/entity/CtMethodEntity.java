package org.coody.framework.context.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class CtMethodEntity extends BaseModel{

	private List<CtAnnotationEntity> annotations;
	
	private String name;
	
	private Class<?> returnType;
	
	private List<CtBeanEntity> paramsType;

	private Boolean isStatic=false;
	private Boolean isFinal=false;
	private Boolean isAbstract=false;
	private String modifier;
	private Boolean isSynchronized=false;
	private Method sourceMethod;
	
	private String key;
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getIsSynchronized() {
		return isSynchronized;
	}

	public Method getSourceMethod() {
		return sourceMethod;
	}

	public void setSourceMethod(Method sourceMethod) {
		this.sourceMethod = sourceMethod;
	}

	public void setIsSynchronized(Boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public Boolean getIsAbstract() {
		return isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean isFinal() {
		return isFinal;
	}


	public String getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		if(Modifier.isPrivate(modifier)){
			this.modifier = "private";
			return;
		}
		if(Modifier.isPublic(modifier)){
			this.modifier = "public";
			return;
		}
		if(Modifier.isProtected(modifier)){
			this.modifier = "protected";
			return;
		}
		if(Modifier.isNative(modifier)){
			this.modifier = "native";
			return;
		}
	}

	public List<CtAnnotationEntity> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<CtAnnotationEntity> annotations) {
		this.annotations = annotations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<?> returnType) {
		this.returnType = returnType;
	}

	public List<CtBeanEntity> getParamsType() {
		return paramsType;
	}

	public void setParamsType(List<CtBeanEntity> paramsType) {
		this.paramsType = paramsType;
	}
}
