package com.ali.web.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {


    protected void service(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("baseServlet中的service方法被执行了");

        //完成路径方法分发
        //1.获取请求路径
        String uri=request.getRequestURI();//  /travel/user/login   /tarvel/user/regist /tarvel/user/active
        System.out.println("请求URI"+uri);
        //2.获取方法的名称
        String methodName=uri.substring(uri.lastIndexOf("/")+1);
        System.out.println("请求方法名"+methodName);

        //3.通过方法名反射获得方法对象
        try {
            Method method=this.getClass()
                          .getMethod(methodName,
                                  HttpServletRequest.class,
                                  HttpServletResponse.class);
            //4.执行一下
            // 暴力反射
               // method.setAccessible(true);
                method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
                e.printStackTrace();
        } catch (InvocationTargetException e) {
                e.printStackTrace();
        }


    }

    /*
        直接将传对象序列化json,并且写回客户端
     */

    public void writeValue(Object object,HttpServletResponse response)
        throws IOException{
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=urf-8");
        mapper.writeValue(response.getOutputStream(),object);
    }

    public String writeValueAsString(Object obj)
        throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }


}
