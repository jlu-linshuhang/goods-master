package jlu.edu.cn.lsh.controller;


import jlu.edu.cn.lsh.po.Admin;
import jlu.edu.cn.lsh.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/adminUser")
public class AdminUserController {

    @Autowired
    private AdminService adminService;

    //管理员登录
    @RequestMapping("/login")
    public String login(HttpServletResponse response, HttpServletRequest request, Admin admin, Model model)throws Exception{

        String adminname = admin.getAdminname();
        String adminpwd = admin.getAdminpwd();
        Admin admin1 = adminService.login(adminname,adminpwd);
        if (admin1==null){
            request.setAttribute("msg","账号或密码错误，别瞎填！");
            model.addAttribute("admin",admin);
            return "forward:/adminjsps/login.jsp";
        }else {
            request.getSession().setAttribute("admin",admin1);
            return "redirect:/adminjsps/admin/index.jsp";
        }
    }

    //退出
    @RequestMapping("/quit")
    public String quit(HttpServletResponse response, HttpServletRequest request, Admin admin, Model model)throws Exception{
        request.getSession().invalidate();
        return "redirect:/adminjsps/login.jsp";
    }
}
