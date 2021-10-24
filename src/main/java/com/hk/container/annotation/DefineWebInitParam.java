package com.hk.container.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : HK意境
 * @ClassName : DefineWebInitParam
 * @date : 2021/9/20 19:00
 * @description : 定义Servlet 的初始化参数注解
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefineWebInitParam {

    //参数名称
    String key() default "";
    //参数值
    String value() default "";


}
