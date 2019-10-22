package org.coody.framework.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 合并接口
 * @author admin
 *
 */
public @interface MergeApi {

	String cmd();
	
	String action();
}
