package org.coody.framework.context.entity;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.coody.framework.context.base.BaseModel;

@SuppressWarnings("serial")
public class CtAnnotationEntity extends BaseModel{

	
	private Class<?> clazz;
	
	private Map<String,Object> fields;
	
	private Annotation annotation;
	
	private Class<?> annotationType ;
	

	public Class<?> getAnnotationType() {
		return annotationType;
	}

	public void setAnnotationType(Class<?> annotationType) {
		this.annotationType = annotationType;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
		this.annotationType=annotation.annotationType();
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Map<String, Object> getFields() {
		return fields;
	}

	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}
	
	
}
