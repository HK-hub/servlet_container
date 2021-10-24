package com.hk.container.annotation;

/**
 * @author : HK意境
 * @ClassName : DefineWebServlet
 * @date : 2021/9/20 17:38
 * @description : 实现注解定义Servlet
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义WebServlet注解，模拟Servlet3.0的WebServlet注解
 * @author HK意境
 * @Target 注解的属性值表明了 @WebServlet注解只能用于类或接口定义声明的前面，
 * @WebServlet注解有一个必填的属性 value 。
 * 调用方式为： @WebServlet(value="/xxxx") ，
 * 因语法规定如果属性名为 value 且只填 value属性值时，可以省略 value属性名，即也可以写作：@WebServlet("/xxxx")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DefineWebServlet {

    //Servlet 请求路径
    String value() ;
    //Servlet 访问路径
    String[] urlPatterns() default {""};
    //Servlet 的藐视
    String info() default "" ;
    //Servlet 的显示名称
    String displayName() default "" ;
    //Servlet 的名称
    String name() default "" ;
    //Servlet 的init 参数
    DefineWebInitParam[] initParameters() default {} ;

}
