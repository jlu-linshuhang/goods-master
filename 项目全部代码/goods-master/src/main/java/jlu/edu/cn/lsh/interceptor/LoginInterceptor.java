package jlu.edu.cn.lsh.interceptor;


import jlu.edu.cn.lsh.po.Admin;
import jlu.edu.cn.lsh.po.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
       /* String url = httpServletRequest.getRequestURI();
        if (url.indexOf("login")>=0||url.indexOf("regist")>=0||url.indexOf("main")>=0||url.indexOf("activation")>=0
            ||url.indexOf("book")>=0||url.indexOf("category")>=0||url.indexOf("captcha")>=0
            ||url.indexOf("adminjsps/login")>=0){
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        User userCustom = (User) session.getAttribute("sessionUser");

        if (userCustom!=null){

            return true;
        }
        httpServletRequest.setAttribute("code","error");
        httpServletRequest.setAttribute("msg","您还没有登陆，抓紧登陆！");
        httpServletResponse.sendRedirect("/jsps/msg.jsp");
        return false;*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
