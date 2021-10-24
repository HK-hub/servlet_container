package com.hk.container.servlet;

import com.hk.container.entity.Request;
import com.hk.container.entity.RequestMethod;
import com.hk.container.entity.Response;
import lombok.Data;

import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : GenericServlet
 * @date : 2021/9/20 16:32
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public abstract class GenericServlet implements Servlet{

    private ServletContext servletContext ;

    @Override
    public void init() {
    }

    @Override
    public void service(Request request, Response response) throws IOException {

    }

    @Override
    public void destroy() {

    }

}
