package com.cloud.model.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * @author zhouyu
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
	
	/**
	 * 操作模块
	 * @return
	 */
	String module();
	
	/**
	 * 是否记录参数
	 * @return
	 */
	boolean recordParam() default true;

}
