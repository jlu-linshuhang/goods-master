package jlu.edu.cn.lsh.filter;




import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object user = request.getSession().getAttribute("sessionUser");
        if (user==null||user==""){
            request.setAttribute("code","error");
            request.setAttribute("msg","您还没有登陆，抓紧登陆！");
            request.getRequestDispatcher("/jsps/user/login.jsp").forward(servletRequest,servletResponse);
            //response.sendRedirect("/jsps/msg.jsp");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
