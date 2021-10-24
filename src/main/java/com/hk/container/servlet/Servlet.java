package com.hk.container.servlet;

import com.hk.container.entity.Request;
import com.hk.container.entity.Response;
import lombok.Data;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : Servlet
 * @date : 2021/9/20 12:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

public interface Servlet {

    public ServletContext servletContext = null;

    /**
     * @methodName : 初始化
     * @author : HK意境
     * @date : 2021/9/20 12:44
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    void init() ;

    /**
     * @methodName : 服务方法
     * @author : HK意境
     * @date : 2021/9/20 12:44
     * @description : 处理请求的服务
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    void service(Request request, Response response) throws IOException;

    /**
     * @methodName : 销毁方法
     * @author : HK意境
     * @date : 2021/9/20 12:44
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    void destroy() ;



}
