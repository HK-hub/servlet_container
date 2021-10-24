package com.hk.container.controller.system;

import com.hk.container.entity.Request;
import com.hk.container.entity.Response;
import com.hk.container.servlet.Servlet;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : IndexServlet
 * @date : 2021/9/20 14:27
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class IndexServlet implements Servlet {

    public IndexServlet() {
        init();
    }

    @Override
    public void init() {
        System.out.println("indexServlet 对象书初始化了");
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        System.out.println("indexServlet 服务调用");
        //创建相应数据
        //返回相应
        response.println("这是首页");
        response.pushToClient(200);
    }

    @Override
    public void destroy() {

    }
}
