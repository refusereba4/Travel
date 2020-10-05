package com.ali.web.servlet;

import com.ali.domain.ResultInfo;
import com.ali.domain.User;
import com.ali.service.UserService;
import com.ali.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    // 调用service注册
    UserService service = new UserServiceImpl();
    /*
       注册功能
     */

    public void regist(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        //验证校验

        String check = request.getParameter("check");
        //session获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //为了保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();

            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");

            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            //将回写到客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);

            return;
        }


        //注册业务

        //1.
        Map<String, String[]> map = request.getParameterMap();
        //2.
        User user = new User();
        //3.
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        boolean flag = service.regist(user);
        //正常应该是true
        System.out.println(flag);


        //结果信息类ResultInfo
        ResultInfo info = new ResultInfo();

        if (flag) {
            //success
            info.setFlag(true);
        } else {
            //fail
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将回写到客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /*
        登录功能
     */

    public void login(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        //1.获取表单数据
        Map<String, String[]> map = request.getParameterMap();
        System.out.println(map);
        //2.
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println(user);
        User u = service.login(user);
        System.out.println(u);

        ResultInfo info = new ResultInfo();

        //4. 判断用户
        if (u == null) {
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        //6。判断激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活");
        }

        if (u != null && "Y".equals(u.getStatus())) {
            //登录成功
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }


        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;chartset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);

    }

    /*
        激活功能
     */

    public void active(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {

            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='http://localhost/travel/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }

    }

    /*
        退出功能
     */

    public void exit(HttpServletRequest request,
                     HttpServletResponse response)
            throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转login.html
        response.sendRedirect(request.getContextPath() + "/login.html");
    }


    /*
    *  查询单个对象
    *  原来：
    *  FindOneServlet
    *     doPost()
    *       UserService
    *       mapper.writeValue......
    * */


    public void findOne(HttpServletRequest request,
                        HttpServletResponse response)
    throws ServletException,IOException{

        //session中获取登录用户
        Object user=request.getSession().getAttribute("user");


     /*   ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
     writeValue(user,response);

    }



}
