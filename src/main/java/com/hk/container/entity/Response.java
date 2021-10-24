package com.hk.container.entity;

/**
 * @author : HK意境
 * @ClassName : Response
 * @date : 2021/9/20 12:56
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

import com.hk.container.util.JsonUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
/**
 * 封装响应信息
 */
@Data
@Accessors(chain = true)
public class Response {
    //两个常量
    public static final String CRLF = "\r\n";
    public static final String BLANK = " ";
    //流
    private BufferedWriter bw;
    //正文
    private StringBuilder content;
    //响应体
    private Object data ;
    //存储头信息
    private StringBuilder headInfo;
    //存储正文长度
    private int len = 0;

    /**
     * @methodName : 构建response 的对象
     * @author : HK意境
     * @date : 2021/9/20 16:24
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
    public static Response builder(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        Response response = new Response(outputStream);
        return response ;
    }

    public Response(){
        headInfo = new StringBuilder();
        content = new StringBuilder();
        len = 0;
    }
    public Response(OutputStream os){
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }
    public Response(Socket client){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            headInfo = null;
        }
    }
    /**
     * 构建正文
     */
    public Response print(String info){
        content.append(info);
        len += (info + CRLF).getBytes().length;
        return this;
    }
    /**
     * 构建正文+回车
     */
    public Response println(String info){
        content.append(info).append(CRLF);
        len += (info + CRLF).getBytes().length;
        return this;
    }

    /**
     * @methodName : 构建data 数据正文
     * @author : HK意境
     * @date : 2021/9/20 14:24
     * @description : 返回JSON 数据
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public Response printlnJson(Object data){

        String json = JsonUtil.beanToJson(data);

        content.append(json).append(CRLF);
        len += (json + CRLF).getBytes().length;
        return this;
    }


    /**
     * @methodName : 构建响应头，指定相应内容，相应编码
     * @author : HK意境
     * @date : 2021/9/20 14:08
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
    private void createHeadInfo(int code, String contentType ,String charset){
        //1)HTTP协议版本、状态代码、描述
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch(code){
            case 200:
                headInfo.append("OK");
                break;
            case 404:
                headInfo.append("NOT FOUND");
                break;
            case 500:
                headInfo.append("Server Error");
                break;
            default:
                headInfo.append("BAD REQUEST");
                break;
        }
        headInfo.append(CRLF);
        //2)响应头（Response Head）
        headInfo.append("Server:test Server/0.0.1").append(CRLF);
        headInfo.append("Date:").append(new Date()).append(CRLF);
        //构建相应类型和相应编码
        headInfo.append("Content-type:").append(contentType).append(";charset=").append(charset.toUpperCase()).append(CRLF);
        //正文长度：字节长度
        headInfo.append("Content-type:").append(len).append(CRLF);
        headInfo.append(CRLF);

    }

    /**
     * 构建响应头
     */
    private void createHeadInfo(int code){
        this.createHeadInfo(code, "text/html", "utf-8");
    }

    /**
     * 推送到客户端
     * @throws IOException
     */
    public void pushToClient(int code) throws IOException{
        if(null==headInfo){
            code = 500;
        }
        createHeadInfo(code);
        //头信息+分割符
        bw.append(headInfo.toString());
        //正文
        bw.append(content.toString());
        bw.flush();
        bw.close();
    }
}