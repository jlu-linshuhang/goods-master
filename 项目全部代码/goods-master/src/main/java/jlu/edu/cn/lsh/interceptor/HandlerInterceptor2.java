package jlu.edu.cn.lsh.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerInterceptor2 implements HandlerInterceptor{

//进入handler方法之前执行，用于身份认证等，return false表示执行不通过验证，true表示可以
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //System.out.print("HandlerInterceptor2....preHandle");



        return true;
    }
//进入handler方法之后，返回modelandview之前执行，将模型数据传到视图，包括统一指定视图
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.print("HandlerInterceptor2....postHandle");



    }
//完成执行handler之后执行，统一异常处理，日志处理
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        //System.out.print("HandlerInterceptor2....afterCompletion");



    }
}
