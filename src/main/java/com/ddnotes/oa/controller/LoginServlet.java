package com.ddnotes.oa.controller;

import com.alibaba.fastjson.JSON;
import com.ddnotes.oa.entity.User;
import com.ddnotes.oa.service.UserService;
import com.ddnotes.oa.service.exception.BussinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = {"/check_login"})
public class LoginServlet extends HttpServlet {
    //设置log
    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    //调用userService
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //取得前端输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //使用Map转成json输出
        Map<String, Object> result = new HashMap<>();
        //检查用户名密码是否正确，并输出消息提示
        try {
            User user = userService.checkLogin(username, password);
            HttpSession session = request.getSession();
            //向session存入登录用户信息，属性名：login_user
            session.setAttribute("login_user",user);
            result.put("code","0");
            result.put("message","success");
            result.put("redirect_url", "/index");
        } catch (BussinessException e) {
            logger.error(e.getMessage(),e);
            result.put("code",e.getCode());
            result.put("message",e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result.put("code",e.getClass().getSimpleName());
            result.put("message",e.getMessage());
        }
        String json = JSON.toJSONString(result);
        response.getWriter().println(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
