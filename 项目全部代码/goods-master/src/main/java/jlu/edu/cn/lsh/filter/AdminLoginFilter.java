package jlu.edu.cn.lsh.filter;




import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Object admin = request.getSession().getAttribute("admin");
        if (admin==null||admin==""){
            request.setAttribute("code","error");
            request.setAttribute("msg","管理员还没有登陆，抓紧登陆！");
            request.getRequestDispatcher("/adminjsps/login.jsp").forward(servletRequest,servletResponse);
            //response.sendRedirect("/jsps/msg.jsp");
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
