package com.hk.container.core;

import com.hk.container.controller.system.NotFoundServlet;
import com.hk.container.servlet.Servlet;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author : HK意境
 * @ClassName : Container
 * @date : 2021/9/20 12:40
 * @description : Servlet 容器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Container {
    //Servlet 容器
    public static final Map<String , Servlet> SERVLET_CONTAINER = new HashMap<String, Servlet>(8) ;
    //web 配置信息
    public static final Properties WEB_CONFIG = new Properties();

    /**
     * @methodName : 获取Servlet
     * @author : HK意境
     * @date : 2021/9/20 15:04
     * @description : 从当前配置文件获取Servlet
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public static Servlet getServletInstance(String url) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Servlet servlet = Container.SERVLET_CONTAINER.get(url);
        //当前容器没有servlet
        if (servlet == null){
            //去配置文件读取信息
            String fullClassName = Container.WEB_CONFIG.getProperty(url);
           //使用放射获取对象实例
            if (fullClassName != null){

                Servlet newServlet = (Servlet) Class.forName(fullClassName).getDeclaredConstructor().newInstance();
                servlet =  newServlet;

            }else{
                //去获取NotFoundServlet 如果没有则创建
                Servlet notFoundServlet = Container.SERVLET_CONTAINER.get("/not-found");
                if (notFoundServlet == null){
                    servlet = new NotFoundServlet();
                }else{
                    servlet = notFoundServlet ;
                }
            }
            //放入容器中
            SERVLET_CONTAINER.put(url , servlet) ;
        }
        return servlet ;
    }



}
