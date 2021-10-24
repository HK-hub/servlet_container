package com.hk.container.controller.system;

import com.hk.container.entity.Request;
import com.hk.container.entity.Response;
import com.hk.container.servlet.Servlet;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : NotFoundServlet
 * @date : 2021/9/20 15:38
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class NotFoundServlet implements Servlet {
    public NotFoundServlet() {
        this.init();
    }

    @Override
    public void init() {
        System.out.println("404 初始化");
    }

    @Override
    public void service(Request request, Response response) throws IOException {
        response.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                " \n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                " \n" +
                "<head>\n" +
                " \n" +
                "<meta charset=\"UTF-8\" http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                " \n" +
                "<title>404-对不起！您访问的页面不存在</title>\n" +
                " \n" +
                "<style type=\"text/css\">\n" +
                " \n" +
                ".head404{ width:580px; height:234px; margin:50px auto 0 auto; background:url(https://www.daixiaorui.com/Public/images/head404.png) no-repeat; }\n" +
                " \n" +
                ".txtbg404{ width:499px; height:169px; margin:10px auto 0 auto; background:url(https://www.daixiaorui.com/Public/images/txtbg404.png) no-repeat;}\n" +
                " \n" +
                ".txtbg404 .txtbox{ width:390px; position:relative; top:30px; left:60px;color:#eee; font-size:13px;}\n" +
                " \n" +
                ".txtbg404 .txtbox p {margin:5px 0; line-height:18px;}\n" +
                " \n" +
                ".txtbg404 .txtbox .paddingbox { padding-top:15px;}\n" +
                " \n" +
                ".txtbg404 .txtbox p a { color:#eee; text-decoration:none;}\n" +
                " \n" +
                ".txtbg404 .txtbox p a:hover { color:#FC9D1D; text-decoration:underline;}\n" +
                " \n" +
                "</style>\n" +
                " \n" +
                "</head>\n" +
                " \n" +
                " \n" +
                " \n" +
                "<body bgcolor=\"#494949\">\n" +
                " \n" +
                "   \t<div class=\"head404\"></div>\n" +
                " \n" +
                "   \t<div class=\"txtbg404\">\n" +
                " \n" +
                "  <div class=\"txtbox\">\n" +
                " \n" +
                "      <p>对不起，您请求的页面不存在、或已被删除、或暂时不可用</p>\n" +
                " \n" +
                "      <p class=\"paddingbox\">请点击以下链接继续浏览网页</p>\n" +
                " \n" +
                "      <p>》<a style=\"cursor:pointer\" οnclick=\"history.back()\">返回上一页面</a></p>\n" +
                " \n" +
                "      <p>》<a href=\"https://www.daixiaorui.com\">返回网站首页</a></p>\n" +
                " \n" +
                "    </div>\n" +
                " \n" +
                "  </div>\n" +
                " \n" +
                "</body>\n" +
                " \n" +
                "</html>\n" +
                "</html>");
        response.pushToClient(404);
    }

    @Override
    public void destroy() {

    }
}
