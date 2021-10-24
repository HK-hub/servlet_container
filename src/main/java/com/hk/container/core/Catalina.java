package com.hk.container.core;

import com.hk.container.entity.Request;
import com.hk.container.entity.Response;
import com.hk.container.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * @author : HK意境
 * @ClassName : Catalina
 * @date : 2021/9/20 12:40
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Catalina {

    private ServerSocket serverSocket ;
    //静态代码块初始化服务器
    static {
        //构建 url -- servlet 地址对象映射
        Properties properties = new Properties();
        try {
            Container.WEB_CONFIG.load(Catalina.class.getClassLoader().getResourceAsStream("web.properties"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //创建服务器
        Catalina catalina = new Catalina();
        //开启服务器
        catalina.start();
    }


    /**
     * @methodName : 开始服务器
     * @author : HK意境
     * @date : 2021/9/20 13:07
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
    public void start() {
        try {
            //创建serverSocket
            serverSocket = new ServerSocket();
            //绑定监听端口
            serverSocket.bind(new InetSocketAddress(8888));
            //开启服务端服务
            while(true){
                this.receive();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * @methodName : 接收客户端请求
     * @author : HK意境
     * @date : 2021/9/20 13:07
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
    public void receive() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        //接受请求
        Socket socket = serverSocket.accept();
        //获取流对象
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        //构造请求对象
        Request request = Request.builder(socket) ;
        //构造相应对象
        Response response = Response.builder(socket);

        //调用对应Servlet 处理请求, 更具请求地址
        Servlet servlet = Container.getServletInstance(request.getUrl());
        //把request ，response 交给Servlet处理
        servlet.service(request, response);

    }


    /**
     * @methodName : 停止服务器
     * @author : HK意境
     * @date : 2021/9/20 13:12
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
    public void stop(){



    }


}
