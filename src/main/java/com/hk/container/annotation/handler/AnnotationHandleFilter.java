package com.hk.container.annotation.handler;

import com.hk.container.annotation.DefineWebServlet;
import com.hk.container.util.ScanClassUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author : HK意境
 * @ClassName : AnnotationHandleFilter
 * @date : 2021/9/20 19:03
 * @description : 定义注解的处理器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class AnnotationHandleFilter implements Filter {

    //全局唯一ServletContext
    private ServletContext servletContext = null;

    /**
     * @methodName : 初始化过滤器
     * @author : HK意境
     * @date : 2021/9/20 19:22
     * @description : 初始化时扫描指定包下使用了DefineWebServlet 的类
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---AnnotationHandleFilter过滤器初始化开始---");
        servletContext = filterConfig.getServletContext();
        //类名称和类映射集合
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

        //获取web.xml中配置的要扫描的包
        String basePackage = filterConfig.getInitParameter("basePackage");

        //获取初始化扫描的包路径
        //如果配置了多个包，例如：<param-value>com.hk.container.controller,com.hk.container.system</param-value>
        if (basePackage.indexOf(",")>0) {
            //按逗号进行分隔
            String[] packageNameArr = basePackage.split(",");
            for (String packageName : packageNameArr) {
                //将包路径下面使用了@DefineWebServlet 注解的类添加到ServletContext 中
                addServletClassToServletContext(packageName,classMap);
            }
        }else {
            //一个包路径
            addServletClassToServletContext(basePackage,classMap);
        }
        System.out.println("----AnnotationHandleFilter过滤器初始化结束---");
    }



    /**
     * @methodName : addServletClassToServletContext
     * @author : HK意境
     * @date : 2021/9/20 19:26
     * @description : 添加Servlet类到ServletContext 中
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    private void addServletClassToServletContext(String packageName,Map<String, Class<?>> classMap){
        //将包下的
        Set<Class<?>> setClasses =  ScanClassUtil.getClasses(packageName);
        for (Class<?> clazz :setClasses) {

            //如果该类被DefineWebServlet 注解修饰
            if (clazz.isAnnotationPresent(DefineWebServlet.class)) {

                //获取DefineWebServlet这个Annotation的实例
                DefineWebServlet annotationInstance = clazz.getAnnotation(DefineWebServlet.class);

                //获取Annotation的实例的value属性的值 , url 路劲值
                String annotationAttrValue = annotationInstance.value();
                if (!annotationAttrValue.equals("")) {
                    //将url 请求路劲和 servlet 类放入 map 映射中
                    classMap.put(annotationAttrValue, clazz);
                }
                //获取Annotation的实例的urlPatterns属性的值
                String[] urlPatterns = annotationInstance.urlPatterns();
                for (String urlPattern : urlPatterns) {
                    //将路径和servlet 类映射起来
                    classMap.put(urlPattern, clazz);
                }

                //将 url -- servlet 映射集合放入全局变量中
                servletContext.setAttribute("servletClassMap", classMap);
                //Servlet 请求路径
                System.out.println("annotationAttrValue："+annotationAttrValue);
                //Servlet 类类名
                String targetClassName = annotationAttrValue.substring(annotationAttrValue.lastIndexOf("/")+1);
                System.out.println("targetClassName："+targetClassName);
                System.out.println(clazz);
            }
        }
    }






    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            System.out.println("---进入注解处理过滤器---");
            //将ServletRequest强制转换成HttpServletRequest
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse res = (HttpServletResponse)response;
            Map<String, Class<?>> classMap = (Map<String, Class<?>>) servletContext.getAttribute("servletClassMap");
            //获取contextPath , 获取虚拟目录
            String contextPath = req.getContextPath();
            //获取用户请求的URI资源
            String uri = req.getRequestURI();


            //如果没有指明要调用Servlet类中的哪个方法
            if (uri.indexOf("!")==-1) {
                //获取用户使用的请求方式
                String reqMethod = req.getMethod();
                //获取要请求的servlet路径
                //String requestServletName = uri.substring(contextPath.length(),uri.lastIndexOf("."));
                String requestServletName = uri.substring(contextPath.length()) ;
                //获取要使用的类
                Class<?> clazz = classMap.get(requestServletName);
                //创建类的实例
                Object obj = null;
                try {
                    //通过放射获取实例对象
                    obj = clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                Method targetMethod = null;
                if (reqMethod.equalsIgnoreCase("get")) {
                    try {
                        targetMethod = clazz.getDeclaredMethod("doGet",HttpServletRequest.class,HttpServletResponse.class);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        //doPost 方法
                        targetMethod = clazz.getDeclaredMethod("doPost",
                                com.hk.container.entity.http.HttpServletRequest.class,
                                com.hk.container.entity.http.HttpServletResponse.class);

                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    //调用对象的方法进行处理 ： 调用Servlet对应方法
                    //转换request 和 response 对象

                    targetMethod.invoke(obj,req,res);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            else {
                //获取要请求的servlet路径
                String requestServletName = uri.substring(contextPath.length(),uri.lastIndexOf("!"));
                //获取要调用的servlet的方法
                String invokeMethodName = uri.substring(uri.lastIndexOf("!")+1,uri.lastIndexOf("."));

                //获取要使用的类
                Class<?> clazz = classMap.get(requestServletName);
                //创建类的实例
                Object obj = null;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                //获得clazz类定义的所有方法
                Method[] methods = clazz.getDeclaredMethods();
                //获取WebServlet这个Annotation的实例
                WebServlet annotationInstance = clazz.getAnnotation(WebServlet.class);
                //获取注解上配置的初始化参数数组
                WebInitParam[] initParamArr = annotationInstance.initParams();
                Map<String, String> initParamMap = new HashMap<String, String>();
                for (WebInitParam initParam : initParamArr) {
                    initParamMap.put(initParam.paramName(), initParam.paramValue());
                }
                //遍历clazz类中的方法
                for (Method method : methods) {
                    //该方法的返回类型
                    Class<?> retType = method.getReturnType();
                    //获得方法名
                    String methodName = method.getName();
                    //打印方法修饰符
                    System.out.print(Modifier.toString(method.getModifiers()));
                    System.out.print(" "+retType.getName() + " " + methodName +"(");
                    //获得一个方法参数数组（getparameterTypes用于返回一个描述参数类型的Class对象数组）
                    Class<?>[] paramTypes = method.getParameterTypes();
                    for(int j = 0 ; j < paramTypes.length ; j++){
                        //如果有多个参数，中间则用逗号隔开，否则直接打印参数
                        if (j > 0){
                            System.out.print(",");
                        }
                        System.out.print(paramTypes[j].getName());
                    }
                    System.out.println(");");
                    if (method.getName().equalsIgnoreCase("init")) {
                        try {
                            //调用Servlet的初始化方法
                            method.invoke(obj, initParamMap);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //获取WebServlet这个Annotation的实例
                System.out.println("invokeMethodName："+invokeMethodName);
                try {
                    try {
                        //利用反射获取方法实例，方法的签名必须符合：
                        //public void 方法名(HttpServletRequest request, HttpServletResponse response)
                        //例如：public void loginHandle(HttpServletRequest request, HttpServletResponse response)
                        Method targetMethod = clazz.getDeclaredMethod(invokeMethodName,HttpServletRequest.class,HttpServletResponse.class);
                        //调用对象的方法进行处理
                        targetMethod.invoke(obj,req,res);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
    }
}
