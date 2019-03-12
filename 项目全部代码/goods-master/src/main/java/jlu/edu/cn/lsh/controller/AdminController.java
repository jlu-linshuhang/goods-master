package jlu.edu.cn.lsh.controller;

import jlu.edu.cn.lsh.po.Admin;
import jlu.edu.cn.lsh.po.Category;
import jlu.edu.cn.lsh.po.CategoryCustom;
import jlu.edu.cn.lsh.service.AdminService;
import jlu.edu.cn.lsh.service.BookService;
import jlu.edu.cn.lsh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    //管理员登录
/*    @RequestMapping("/login")
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
    }*/

    //查询所有分类
    @RequestMapping("/findAll")
    public String findAll(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);
        return "forward:/adminjsps/admin/category/list.jsp";
    }

    //添加父分类
    @RequestMapping("/addParent")
    public String addParent(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{

        String cname = request.getParameter("cname");
        String desc = request.getParameter("desc");
        categoryService.addFirstClass(cname,desc);
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);
        return "forward:/adminjsps/admin/category/list.jsp";
    }

    //添加子分类准备
    @RequestMapping("/addChildPre")
    public String addChildPre(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{

        String pid = request.getParameter("pid");
        List<CategoryCustom> categoryCustomList = categoryService.findFirstClass();
        request.setAttribute("pid",pid);
        request.setAttribute("parents",categoryCustomList);
        return "forward:/adminjsps/admin/category/add2.jsp";
    }

    //添加子分类
    @RequestMapping("/addChild")
    public String addChild(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        String pid = request.getParameter("pid");
        String cname = request.getParameter("cname");
        String desc = request.getParameter("desc");
        categoryService.addSecondClass(pid,cname,desc);
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);

        return "forward:/adminjsps/admin/category/list.jsp";
    }

    //修改父分类准备
    @RequestMapping("/editParentPre")
    public String editParentPre(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{

        String cid = request.getParameter("cid");
        CategoryCustom categoryCustom = categoryService.findClassByCid(cid);
        request.setAttribute("parent",categoryCustom);
        return "forward:/adminjsps/admin/category/edit.jsp";
    }

    //修改父分类
    @RequestMapping("/editParent")
    public String editParent(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{

        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        String desc = request.getParameter("desc");
        CategoryCustom categoryCustom = new CategoryCustom();
        categoryCustom.setCid(cid);
        categoryCustom.setCname(cname);
        categoryCustom.setDesc(desc);
        categoryService.updateClassByCid(categoryCustom);
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);
        return "forward:/adminjsps/admin/category/list.jsp";
    }

    //修改子分类准备
    @RequestMapping("/editChildPre")
    public String editChildPre(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        String cid = request.getParameter("cid");
        CategoryCustom categoryCustom = categoryService.findClassByCid(cid);
        List<CategoryCustom> categoryCustomList = categoryService.findFirstClass();
        CategoryCustom categoryCustom1 = categoryService.findClassByCid(categoryCustom.getPid());
        categoryCustom.setParent(categoryCustom1);


        request.setAttribute("child",categoryCustom);
        request.setAttribute("parents",categoryCustomList);
        return "forward:/adminjsps/admin/category/edit2.jsp";
    }

    //修改子分类
    @RequestMapping("/editChild")
    public String editChild(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        String desc = request.getParameter("desc");
        String pid = request.getParameter("pid");
        CategoryCustom categoryCustom = new CategoryCustom();
        categoryCustom.setCid(cid);
        categoryCustom.setCname(cname);
        categoryCustom.setDesc(desc);
        categoryCustom.setPid(pid);
        categoryService.updateSecondClassByCid(categoryCustom);
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);
        return "forward:/adminjsps/admin/category/list.jsp";
    }

    //删除父分类
    @RequestMapping("/deleteParent")
    public String deleteParent(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        String cid = request.getParameter("cid");
        int a = categoryService.findChildCountByParentId(cid);
        if (a != 0) {
            request.setAttribute("msg","该分类下还有信息，无法删除！");
            return "forward:/adminjsps/msg.jsp";
        }else {
            categoryService.deleteByCid(cid);
            List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
            request.setAttribute("parents" ,categoryCustomList);
            return "forward:/adminjsps/admin/category/list.jsp";
        }
    }

    //删除子分类
    @RequestMapping("/deleteChild")
    public String deleteChild(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        String cid = request.getParameter("cid");
        int a  = bookService.findCountByCid(cid);
        if (a!=0){
            request.setAttribute("msg","该分类下还有图书，无法删除！");
            return "forward:/adminjsps/msg.jsp";
        }else {
            categoryService.deleteByCid(cid);
            List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
            request.setAttribute("parents" ,categoryCustomList);
            return "forward:/adminjsps/admin/category/list.jsp";
        }
    }
}
