package com.cisdi.info.simple.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DEntity {
	/**
	 * 中文名称
	 * @return
	 */
	public String label() default ""; 
	

	/**
	 * 提示
	 * @return
	 */
	public String comment() default "";


	public String moduleLabel() default "";

}
