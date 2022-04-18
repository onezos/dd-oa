package com.ddnotes.oa.controller;

import com.ddnotes.oa.entity.Department;
import com.ddnotes.oa.entity.Employee;
import com.ddnotes.oa.entity.Node;
import com.ddnotes.oa.entity.User;
import com.ddnotes.oa.service.DepartmentService;
import com.ddnotes.oa.service.EmployeeService;
import com.ddnotes.oa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentsService = new DepartmentService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //得到当前登录用户对象
        User user = (User) session.getAttribute("login_user");
        //获取当前登录的员工对象
        Employee employee = employeeService.selectById(user.getEmployeeId());
        //获取员工对应的部门
        Department department = departmentsService.selectById(employee.getDepartmentId());
        //获取登录用户可用功能模块列表
        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
        //放入请求属性
        request.setAttribute("node_list", nodeList);
        //放入session为了其它页面也可以使用数据，不仅仅是基于这个请求
        session.setAttribute("current_employee" , employee);
        session.setAttribute("current_department", department);
        request.getRequestDispatcher("/index.ftl").forward(request,response);
    }
}
