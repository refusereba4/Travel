package com.ali.web.servlet;

import com.ali.service.CategoryService;
import com.ali.service.impl.CategoryServiceImpl;
import com.ali.domain.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    CategoryService service=
            new CategoryServiceImpl();

    public void findAll(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{

        //1.调用service完成查询
        List<Category> cs=service.findAll();
        //2.序列化json返回
        writeValue(cs,response);


    }
}
