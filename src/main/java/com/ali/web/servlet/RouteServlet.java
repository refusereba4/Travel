package com.ali.web.servlet;

import com.ali.domain.PageBean;
import com.ali.domain.Route;
import com.ali.domain.User;
import com.ali.service.FavoriteService;
import com.ali.service.RouteService;
import com.ali.service.impl.FavriteServiceImpl;
import com.ali.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ser.Serializers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService=new RouteServiceImpl();
    private FavoriteService favoriteService=
            new FavriteServiceImpl();

    /*
        分页查询
     */

    public void pageQuery(HttpServletRequest request,
                          HttpServletResponse response)
        throws ServletException,IOException{
        //1.接受参数
        String currentPageStr=request.getParameter("currentPage");
        String pageSizeStr=request.getParameter("pageSize");
        String cidStr=request.getParameter("cid");


        // 接受rname线路名称
        String rname=request.getParameter("rname");
        rname=new String(rname.getBytes("ISO-8859-1"),"UTF-8");
        //2.处理参数
        //2.1
        int cid=0;//类别id cid
        if(cidStr!=null&&cidStr.length()>0){
            cid=Integer.parseInt(cidStr);
        }

        //2.2 当前页参数
        int currentPage=0;//当前页码，如果不传递，则默认为第一页
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage=Integer.parseInt(currentPageStr);
        }else{
            currentPage=1;
        }

        //2.3 处理pageSize
        int pageSize=0;
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize=Integer.parseInt(pageSizeStr);
        }else{
            pageSize=10; //每页显示的条数，如果不传，默认显示5条记录
        }
        //调用service查询PageBean对象
        PageBean<Route> pb=
                routeService.pageQuery(cid,currentPage,pageSize,rname);

        //将PageBean对象序列化json,返回
        writeValue(pb,response);
    }


    public void findOne(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{

        //1。接暇id
        String id=request.getParameter("rid");
        //2.调用Service
        Route route=routeService.findOne(id);
        //3.转转成json返回给客户端
        writeValue(route,response);
    }

    public void addFavorite(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        //1.获取线路
        String rid=request.getParameter("rid");
        //2.获取当前用户
        User user=(User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            //用户尚未登录
            return ;
        }else{
            //
            uid=user.getUid();
        }

        //3.
        favoriteService.add(rid,uid);
    }

    public void isFavorite(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        //1。
        String rid=request.getParameter("rid");
        //2.获取当前登录用户
        User user=(User)request.getSession().getAttribute("user");
        int uid;
        if(user==null){
            uid=0;
        }else{
          uid=user.getUid();
        }

        //3.调用
        boolean flag=favoriteService.isFavorite(rid,uid);

        //4.写回客户端
        writeValue(flag,response);
    }
}
