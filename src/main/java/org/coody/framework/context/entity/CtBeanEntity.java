package org.coody.framework.context.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.coody.framework.context.base.BaseModel;
import org.coody.framework.util.SimpleUtil;
import org.coody.framework.util.StringUtil;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class CtBeanEntity extends BaseModel{

	private String fieldName;
	private Object fieldValue;
	private String stringValue;
	private Class<?> fieldType;
	private List<CtAnnotationEntity> annotations;
	private Field sourceField;
	private Boolean isStatic=false;
	private Boolean isFinal=false;
	private String modifier;
	
	
	
	
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	public Field getSourceField() {
		return sourceField;
	}
	public Boolean getIsStatic() {
		return isStatic;
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
	public void setSourceField(Field sourceField) {
		this.sourceField = sourceField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		if(!StringUtil.isNullOrEmpty(fieldValue)){
			try {
				if(SimpleUtil.isSignType(fieldValue.getClass())){
					stringValue=fieldValue.toString();
				}else{
					stringValue=JSON.toJSONString(fieldValue);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.fieldValue = fieldValue;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	
	public List<CtAnnotationEntity> getAnnotations() {
		return annotations;
	}
	public void setAnnotations(List<CtAnnotationEntity> annotations) {
		this.annotations = annotations;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	public static void main(String[] args) {
	}
	
	public Annotation getAnnotation(Class<?> clazz){
		if(StringUtil.isNullOrEmpty(annotations)){
			return null;
		}
		for (CtAnnotationEntity annotation:annotations) {
			if(clazz.isAssignableFrom(annotation.getAnnotation().annotationType())){
				return annotation.getAnnotation();
			}
		}
		return null;
	}
	
}
