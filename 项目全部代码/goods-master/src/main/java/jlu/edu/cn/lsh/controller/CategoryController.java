package jlu.edu.cn.lsh.controller;

import jlu.edu.cn.lsh.po.CategoryCustom;
import jlu.edu.cn.lsh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/findAllClass")
    public String findAllClass(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        List<CategoryCustom> parents = categoryService.findAllClass();
        request.setAttribute("parents",parents);
        return "/jsps/left.jsp";
    }
}
