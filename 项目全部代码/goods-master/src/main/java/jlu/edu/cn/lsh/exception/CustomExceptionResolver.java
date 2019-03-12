package jlu.edu.cn.lsh.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomExceptionResolver implements HandlerExceptionResolver{


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        /*String message = null;
        if (e instanceof CustomException){
            message = ((CustomException)e).getMessage();
        }else {
            message = "未知错误";
        }*/
        //将上面代码更改为：
        CustomException customException = null;
        if (e instanceof CustomException){
            customException = (CustomException)e;
        }else{
            customException = new CustomException("未知错误");
        }
        String message = customException.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",message);
        modelAndView.setViewName("error");

        return modelAndView;
    }
}
