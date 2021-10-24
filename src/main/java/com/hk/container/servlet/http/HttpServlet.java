package com.hk.container.servlet.http;

import com.hk.container.entity.Request;
import com.hk.container.entity.RequestMethod;
import com.hk.container.entity.Response;
import com.hk.container.entity.http.HttpServletRequest;
import com.hk.container.entity.http.HttpServletResponse;
import com.hk.container.servlet.GenericServlet;
import lombok.Data;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : HttpServlet
 * @date : 2021/9/20 16:33
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@WebServlet
public abstract class HttpServlet extends GenericServlet {

    private ServletContext servletContext ;

    @Override
    public void service(Request request, Response response) throws IOException {
        String method = request.getMethod();
        if (RequestMethod.GET.getMethod().equals(method)){
            this.doGet((HttpServletRequest)request, (HttpServletResponse)response);
        }else if (RequestMethod.POST.getMethod().equals(method)){
            this.doPost((HttpServletRequest)request, (HttpServletResponse)response);
        }else if (RequestMethod.PUT.getMethod().equals(method)){
        }
    }

    /**
     * @methodName : Get 请求方式
     * @author : HK意境
     * @date : 2021/9/20 16:35
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
    public abstract void doGet(HttpServletRequest request , HttpServletResponse response) ;


    /**
     * @methodName : doPost 请求
     * @author : HK意境
     * @date : 2021/9/20 16:35
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
    public abstract void doPost(HttpServletRequest request , HttpServletResponse response) ;

    public void doPut(Request request, Response response) {

    }

    public void doDelete(Request request, Response response) {

    }



}
