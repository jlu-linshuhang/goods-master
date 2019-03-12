package jlu.edu.cn.lsh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/map")
public class MapController {

    @RequestMapping("/gaodeMap")
    public String gaodeMap(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{

        return "forward:/map/dingwei.jsp";
    }
}
