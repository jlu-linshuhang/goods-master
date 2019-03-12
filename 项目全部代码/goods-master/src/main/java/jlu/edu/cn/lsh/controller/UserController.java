package jlu.edu.cn.lsh.controller;


import com.google.code.kaptcha.Constants;
import jlu.edu.cn.lsh.exception.UserException;
import jlu.edu.cn.lsh.po.User;
import jlu.edu.cn.lsh.po.UserCustom;
import jlu.edu.cn.lsh.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //没什么用，单纯的测试使用的
    @RequestMapping("/registTest")
    public String registTest(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception {

        boolean b1 = true;
        boolean b2 = true;
        boolean b3 = true;
        b1 = userService.ifqueryCountByLoginname(userCustom.getLoginname());
        respond.getWriter().print(b1);
        b2 = userService.ifqueryCountByEmail(userCustom.getEmail());
        respond.getWriter().print(b2);

        String s = request.getParameter("verifyCode");
        String vcode = (String) request.getSession().getAttribute("verifyCodeValue");
        b3 = s.equalsIgnoreCase(vcode);
        respond.getWriter().print(b3);

        return null;

    }

    //ajax检验用户名是否重复
    @RequestMapping("/regist2")
    public String regist2(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception {

        boolean b =userService.ifqueryCountByLoginname(userCustom.getLoginname());
        respond.getWriter().print(b);
        return null;
    }

    //ajax检验email是否重复
    @RequestMapping("/regist3")
    public String regist3(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception {

        boolean b = userService.ifqueryCountByEmail(userCustom.getEmail());
        //boolean b =userService.ifqueryCountByLoginname(userCustom.getLoginname());
        respond.getWriter().print(b);
        return null;
    }

    //ajax检验验证码是否正确
    @RequestMapping("/regist4")
    public String regist4(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception {
        String s = request.getParameter("verifyCode");
        String vcode = (String) request.getSession().getAttribute("verifyCodeValue");
        boolean b = s.equalsIgnoreCase(vcode);
        respond.getWriter().print(b);
        return null;
    }

    //校验邮箱激活码
    @RequestMapping("/activation")
    public String activation(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        String activationCode = request.getParameter("activationCode");
        try {
            userService.activation(activationCode);
            request.setAttribute("code","success");
            request.setAttribute("msg","恭喜您激活成功，请登陆您的账号！");
        }catch (UserException e){
            request.setAttribute("code", "error");
            request.setAttribute("msg",e.getMessage());
        }

        return "forward:/jsps/msg.jsp";
    }

    //点击提交的时候，进行真正的数据库校验
    @RequestMapping("/regist")
    public String regist(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception {

        Map<String, String> errors = new HashMap<>();

        String loginname = userCustom.getLoginname();
        if (loginname == null || loginname.trim().isEmpty()) {
            errors.put("loginname", "用户名不能为空");
        } else if (loginname.length() < 3 || loginname.length() > 20) {
            errors.put("loginname", "用户名长度在3-20之间！");
        } else if (!userService.ifqueryCountByLoginname(loginname)) {
            errors.put("loginname", "用户名已被注册");
        }

        String loginpass = userCustom.getLoginpass();
        if (loginpass == null | loginpass.trim().isEmpty()) {
            errors.put("loginpass", "密码不能为空");
        } else if (loginpass.length() < 3 | loginpass.length() > 20) {
            errors.put("loginpass", "密码长度在3-20之间！");
        }

        String reloginpass = userCustom.getReloginpass();
        if (reloginpass == null || reloginpass.trim().isEmpty()) {
            errors.put("reloginpass", "确认密码不能为空");
        } else if (!reloginpass.equals(loginpass)) {
            errors.put("reloginpass", "两次输入不一致");
        }

        String email = userCustom.getEmail();
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email不能为空");
        } else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
            errors.put("email", "错误的Email格式");
        } else if (!userService.ifqueryCountByEmail(email)) {
            errors.put("email", "Email已被注册");
        }

        String verifyCode = userCustom.getVerifyCode();
        String original = (String) request.getSession().getAttribute("verifyCodeValue");
        if (verifyCode == null || verifyCode.trim().isEmpty()) {
            errors.put("verifyCode", "验证码不能为空");
        } else if (!verifyCode.equalsIgnoreCase(original)) {
            errors.put("verifyCode", "错误的验证码");
        }

        if (errors.size() > 0) {
            model.addAttribute("form", userCustom);
            request.setAttribute("errors", errors);
            return "forward:/jsps/user/regist.jsp";
        }

        userService.insertUser(userCustom);

        request.setAttribute("code", "success");
        request.setAttribute("msg", "注册成功，请马上到您的邮箱激活");
        return "forward:/jsps/msg.jsp";
    }

    //登陆用户
    @RequestMapping("/login")
    public String login(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model) throws Exception{

        Map<String,String> errors = new HashMap<String, String>();
        String loginname = userCustom.getLoginname();
        if (loginname==null||loginname.trim().isEmpty()){
            errors.put("loginname","用户名不能为空！");
        }else if (loginname.length()<3||loginname.length()>20){
            errors.put("loginname","用户名长度在3-20之间");
        }
        String loginpass = userCustom.getLoginpass();
        if (loginpass==null||loginpass.trim().isEmpty()){
            errors.put("loginpass","密码不能为空");
        }else if (loginpass.length()<3||loginpass.length()>20){
            errors.put("loginpass","密码长度在3-20之间！");
        }
        String verifyCode = userCustom.getVerifyCode();
        String original = (String) request.getSession().getAttribute("verifyCodeValue");
        if (verifyCode==null||verifyCode.trim().isEmpty()){
            errors.put("verifyCode","验证码不能为空");
        }else if (!verifyCode.equalsIgnoreCase(original)){
            errors.put("verifyCode","错误的验证码");
        }
        if (errors.size()>0){
            request.setAttribute("errors",errors);
            model.addAttribute("user",userCustom);
            return "forward:/jsps/user/login.jsp";
        }

        User user1 = userService.login(userCustom);
        if (user1==null){
            request.setAttribute("msg","用户名或密码错误！");
            model.addAttribute("user",userCustom);
            return "forward:/jsps/user/login.jsp";
        }else {
            if (user1.getStatus()==0){
                request.setAttribute("msg","用户还没有激活！");
                model.addAttribute("user",userCustom);
                return "forward:/jsps/user/login.jsp";
            }else {
                request.getSession().setAttribute("sessionUser",user1);
                String loginname2 = user1.getLoginname();
                loginname2 = URLEncoder.encode("loginname2","utf-8");
                Cookie cookie = new Cookie("loginname2",loginname2);
                cookie.setMaxAge(60*60*24*10);//保存10天
                respond.addCookie(cookie);
                return "redirect:/index.jsp";
            }
        }
    }

    //用户修改密码
    @RequestMapping("/updatePassword")
    public String updatePassword(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model){

        Map<String,String> errors = new HashMap<String,String>();
        String loginpass = userCustom.getLoginpass();//旧密码
        if (loginpass==null||loginpass.trim().isEmpty()){
            errors.put("loginpass","原密码不能为空");
        }else if (loginpass.length()<3||loginpass.length()>20){
            errors.put("loginpass","请输入3到20位的用户密码");
        }
        String newloginpass = userCustom.getNewloginpass();
        if (newloginpass==null||newloginpass.trim().isEmpty()){
            errors.put("newloginpass","新密码不能为空");
        }else if (newloginpass.length()<3||newloginpass.length()>20){
            errors.put("newloginpass","新密码长度应该在3到20个字符之间！");
        }
        String reloginpass = userCustom.getReloginpass();
        if (reloginpass==null||reloginpass.trim().isEmpty()){
            errors.put("reloginpass","确认密码不能为空！");
        }else if (!reloginpass.equals(newloginpass)){
            errors.put("reloginpass","确认密码与新密码不相同！");
        }
        String verifyCode = userCustom.getVerifyCode();
        String original = (String)request.getSession().getAttribute("verifyCodeValue");
        if (verifyCode==null||verifyCode.trim().isEmpty()){
            errors.put("verifyCode","验证码不能为空");
        }else if (!verifyCode.equalsIgnoreCase(original)){
            errors.put("verifyCode","验证码输入错误！");
        }
        if (errors.size()>0){
            request.setAttribute("errors",errors);
            model.addAttribute("user",userCustom);
            return "forward:/jsps/user/pwd.jsp";
        }

        User userCustom1 = (User) request.getSession().getAttribute("sessionUser");
        if (userCustom1 == null){
            request.setAttribute("msg","您还没有登陆！");
            return "forward:/jsps/user/login.jsp";
        }
        try {
            userService.updatePassword(userCustom1.getUid(),userCustom.getLoginpass(),userCustom.getNewloginpass());
            request.setAttribute("msg","修改密码成功！");
            request.setAttribute("code","success");
            return "forward:/jsps/msg.jsp";
        } catch (UserException e) {
            request.setAttribute("msg",e.getMessage());
            model.addAttribute("user",userCustom);
            return "forward:/jsps/user/pwd.jsp";
        }
    }

    //退出用户
    @RequestMapping("/quit")
    public String quit(UserCustom userCustom, HttpServletRequest request, HttpServletResponse respond, Model model)throws Exception{
        request.getSession().invalidate();
        return "redirect:/index.jsp";
    }
}
