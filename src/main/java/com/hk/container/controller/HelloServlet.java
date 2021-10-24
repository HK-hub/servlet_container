package com.hk.container.controller;

import com.hk.container.entity.Request;
import com.hk.container.entity.Response;
import com.hk.container.servlet.Servlet;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : HelloServlet
 * @date : 2021/9/20 14:55
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class HelloServlet implements Servlet {
    public HelloServlet() {
        this.init();
    }

    @Override
    public void init() {
        System.out.println("hello servlet 初始化");
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        System.out.println("这是 HelloServlet");
        response.println("Hello World!");
        response.pushToClient(200);
    }

    @Override
    public void destroy() {

    }
}
