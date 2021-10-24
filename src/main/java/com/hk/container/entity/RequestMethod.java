package com.hk.container.entity;

/**
 * @author : HK意境
 * @ClassName : RequestMethod
 * @date : 2021/9/20 13:33
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum RequestMethod {
    //POST 请求
    POST("post"),
    //GET 请求
    GET("get"),
    PUT("put"),
    DELETE("delete"),
    UPDATE("update")
    ;
    
    public String method ;

    RequestMethod(String method) {
        this.method = method ;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
