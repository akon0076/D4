package com.cisdi.info.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface DColumn {

    /**
     * 中文名
     * @return
     */
    public int index() default 0;


    /**
     * 中文名
     * @return
     */
    public String label() ;


    /**
     * 约束
     * @return
     */
    public String constrains() default "";

    /**
     * 提示
     * @return
     */
    public String comment() default "";

    /**
     * 外鍵表名
     * 如果不是外键，则为Object.class
     * @return
     */
    public String foreignEntity() default "";

    /**
     * 码表的键
     * @return
     */
    public String codeTable() default "";

}
