package com.hk.container.entity;



import lombok.Data;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * @author : HK意境
 * @ClassName : Request
 * @date : 2021/9/20 12:56
 * @description : 封装request 请求对象
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class Request {
    //请求方式
    private String method;
    //请求资源
    private String url;
    //请求参数
    private Map<String, List<String>> parameterMapValues;
    //请求IP地址
    private String remoteAddress ;
    //ServletContext
    private ServletContext servletContext;
    //请求虚拟目录
    private String contextPath = "" ;
    //获取请求URI
    private String uri ;
    //Socket 对象
    private Socket socket ;
    //内部
    public static final String CRLF = "\r\n";
    private InputStream is;
    private String requestInfo;


    /**
     * @methodName : request 配置构造器
     * @author : HK意境
     * @date : 2021/9/20 16:18
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
    public static Request builder(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        Request request = new Request(inputStream);
        //请求用户IP地址
        request.setRemoteAddress(socket.getInetAddress().getHostAddress());
        return request ;
    }

    /**
     * @methodName : 使用Soket 对象初始化
     * @author : HK意境
     * @date : 2021/9/20 20:14
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
    public Request(Socket socket) throws IOException {
        this(socket.getInputStream());
        this.socket = socket ;
        this.remoteAddress = socket.getInetAddress().getHostAddress();
    }


    public Request(){
        method = "";
        url = "";
        parameterMapValues = new HashMap<String,List<String>>();
        requestInfo = "";
    }

    public Request(InputStream is){
        this();
        this.is = is;
        try {
            byte[] data = new byte[204800];
            int len = is.read(data);
            requestInfo = new String(data,0,len);
        } catch (IOException e) {
            return;
        }
        //分析请求信息
        parseRequestInfo();
    }


    /**
     * 分析请求信息
     */
    private void parseRequestInfo(){
        if((null==requestInfo) || (requestInfo=requestInfo.trim()).equals("")){
            return;
        }

        /**
         * ====================================
         * 从信息的首行分解出：请求方式  请求路径  请求参数（get可能存在）
         *   如：GET /index.html?uname=intputUname&pwd=inputPassword HTTP/1.1
         *
         * 如果为post方式，请求参数可能在最后正文中
         * ====================================
         */
        String paramString = "";//接收请求参数

        //获取请求行
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));

        //1、获取请求方式
        this.getRequestMethod(firstLine);
        int idx = requestInfo.indexOf("/");
        //2.获取请求路径url
        String urlStr = getRequestUrl(firstLine,idx);

        //post方式
        if(this.method.equalsIgnoreCase(RequestMethod.POST.getMethod())){
            this.url = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();

        } else if(this.method.equalsIgnoreCase(RequestMethod.GET.getMethod())){
            //get方式
            //url 路径是否包含请求参数，get 方式具有
            if(urlStr.contains("?")){
                String[] urlArray = urlStr.split("\\?");
                this.url = urlArray[0];
                //接收请求参数： 后面参数使用 & 拼接
                paramString = urlArray[1];
            }else{
                this.url = urlStr;
            }
        }

        //2、将请求参数封装到Map中
        parseParams(paramString);
    }

    /**
     * @methodName : 获取请求路径url
     * @author : HK意境
     * @date : 2021/9/20 13:23
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
    private String getRequestUrl(String firstLine ,int index){
        return firstLine.substring(index,firstLine.indexOf("HTTP/")).trim();
    }

    /**
     * @methodName : 获取请求方式
     * @author : HK意境
     * @date : 2021/9/20 13:23
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
    private void getRequestMethod(String firstLine){
        //1、获取请求方式
        //String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
        //获取 / 位置
        int idx = requestInfo.indexOf("/");
        //得到请求方式
        this.method = firstLine.substring(0,idx).trim();

    }

    /**
     * 将请求参数封装到Map中
     * @param paramString
     */
    private void parseParams(String paramString){
        //分割，将字符串转成数组
        StringTokenizer token = new StringTokenizer(paramString,"&");

        while(token.hasMoreTokens()){
            //参数键值对； key=value
            String keyValue = token.nextToken();
            //获取参数名称
            String[] keyValues = keyValue.split("=");

            if(keyValues.length == 1){
                keyValues = Arrays.copyOf(keyValues, 2);
                keyValues[1] = null;
            }

            String key = keyValues[0].trim();
            //中文处理编码
            String value = null==keyValues[1]?null:decode(keyValues[1].trim(),"gbk");
            //分拣，转换成Map
            if(!parameterMapValues.containsKey(key)){
                parameterMapValues.put(key, new ArrayList<String>());
            }

            List<String> values = parameterMapValues.get(key);
            values.add(value);
        }
    }


    /**
     * 解决中文
     * @param value
     * @param code
     * @return
     */
    private String decode(String value,String code){
        try {
            return java.net.URLDecoder.decode(value, code);
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据页面的name获取对应的多个值
     */
    public String[] getParameterValues(String name){
        List<String> values = null;
        if( (values=parameterMapValues.get(name))==null ){
            return null;
        }else{
            return values.toArray(new String[0]);
        }
    }

    /**
     * 根据页面的name获取对应的单个值
     */
    public String getParameterValue(String name){
        String[] values = getParameterValues(name);
        if(null==values){
            return null;
        }
        return values[0];
    }

    /**
     * 获取 contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    public String getUri() {
        return uri;
    }
}