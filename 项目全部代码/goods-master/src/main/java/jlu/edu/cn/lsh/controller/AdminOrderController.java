package jlu.edu.cn.lsh.controller;


import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.po.OrderCustom;
import jlu.edu.cn.lsh.po.OrderVo;
import jlu.edu.cn.lsh.po.Orderitem;
import jlu.edu.cn.lsh.po.User;
import jlu.edu.cn.lsh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/adminOrder")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    //获取页面上的页码，传入其它出了数字以外的，抛异常，设为默认值1
    private int getPc(HttpServletRequest request){
        int pc = 1;
        String param = request.getParameter("pc");
        if (param != null&&!param.trim().isEmpty()){
            try {
                pc = Integer.parseInt(param);
            }catch (RuntimeException e){ }
        }
        return pc;
    }
    //单传pc的时候
    private String getUrl(HttpServletRequest request){
        String s1 = request.getRequestURI();//获得？前内容
        String s2 = request.getQueryString();//获得？后内容
        String s3;
        if (s2==null||s2==""||s2==""){
            s3 = s1+"?";
        }else {
            s3 = s1 + "?" + s2;
        }
        String url;
        int index = s3.lastIndexOf("pc=");
        if (index !=-1){
            url = s3.substring(0,index);
        }else {
            url = s3;
        }
        return url;
    }
    //传入多个参数的时候
    private String getUrl2(HttpServletRequest request){
        String s1 = request.getRequestURI();//获得？前内容
        String s2 = request.getQueryString();//获得？后内容
        String s3;
        if (s2==null||s2==""||s2==""){
            s3 = s1+"?";
        }else {
            s3 = s1 + "?" + s2;
        }
        String url;
        int index = s3.lastIndexOf("&pc=");
        if (index !=-1){
            url = s3.substring(0,index);
        }else {
            url = s3;
        }
        return url;
    }

    //查询所有订单信息，按照时间排序
    @RequestMapping("/findAll")
    public String findAll(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{

        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Order_Page_Size;
        int tr = orderService.findAllOrderCount();
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);
        List<OrderCustom> orderCustomList = orderService.findAll(pc);
        for (int i = 0;i<orderCustomList.size();i++){
            String oid = orderCustomList.get(i).getOid();
            List<Orderitem> orderitemList = orderService.findOrderItemsByOid(oid);
            orderCustomList.get(i).setOrderitemList(orderitemList);
        }
        OrderVo orderVo = new OrderVo();
        orderVo.setPs(ps);
        orderVo.setPc(pc);
        orderVo.setTp(tp);
        orderVo.setTr(tr);
        orderVo.setUrl(url);

        request.setAttribute("pb",orderVo);
        request.setAttribute("orderCustomList",orderCustomList);
        return "forward:/adminjsps/admin/order/list.jsp";
    }

    //按状态查询订单信息
    @RequestMapping("/findOrderByStatus")
    public String findOrderByStatus(HttpServletResponse response,HttpServletRequest request, Model model)throws Exception{

        int status = Integer.parseInt(request.getParameter("status"));

        int pc = getPc(request);
        String url = getUrl2(request);
        int ps = PageConstants.Order_Page_Size;
        int tr = orderService.findCountByStatus(status);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);
        List<OrderCustom> orderCustomList = orderService.findOrdersByStatus(pc,status);
        for (int i = 0;i<orderCustomList.size();i++){
            String oid = orderCustomList.get(i).getOid();
            List<Orderitem> orderitemList = orderService.findOrderItemsByOid(oid);
            orderCustomList.get(i).setOrderitemList(orderitemList);
        }
        OrderVo orderVo = new OrderVo();
        orderVo.setPs(ps);
        orderVo.setPc(pc);
        orderVo.setTp(tp);
        orderVo.setTr(tr);
        orderVo.setUrl(url);
        orderVo.setStatus(status);

        request.setAttribute("pb",orderVo);
        request.setAttribute("orderCustomList",orderCustomList);
        return "forward:/adminjsps/admin/order/list.jsp";
    }

    //加载订单详细信息
    @RequestMapping("/load")
    public String load(HttpServletResponse response,HttpServletRequest request, Model model)throws Exception{

        String oid = request.getParameter("oid");
        String btn = request.getParameter("btn");
        OrderCustom orderCustom = orderService.findOrderByOid(oid);
        List<Orderitem> orderitemList = orderService.findOrderItemsByOid(oid);
        BigDecimal b1;
        BigDecimal b2;
        for (int i = 0;i<orderitemList.size();i++){
            b1 = orderitemList.get(i).getCurrprice();
            b2 = BigDecimal.valueOf(orderitemList.get(i).getQuantity());
            orderitemList.get(i).setSubtotal(b1.multiply(b2));
        }
        orderCustom.setOrderitemList(orderitemList);
        request.setAttribute("order",orderCustom);
        request.setAttribute("btn",btn);
        return  "forward:/adminjsps/admin/order/desc.jsp";
    }

    //取消订单
    @RequestMapping("/cancel")
    public String cancel(HttpServletRequest request,HttpServletResponse response, Model model)throws Exception{

        String oid = request.getParameter("oid");
        int status = orderService.findStatusByOid(oid);
        if (status==0){
            request.setAttribute("code","error");
            request.setAttribute("msg","查询状态错误！");
            return "forward:/adminjsps/msg.jsp";
        }
        if (status!=1){
            request.setAttribute("code","error");
            request.setAttribute("msg","状态错误，不能取消订单");
            return "forward:/adminjsps/msg.jsp";
        }else {
            orderService.updateStatusByOid(oid,5);
            request.setAttribute("code","success");
            request.setAttribute("msg","恭喜您操作成功，订单已取消！");
            return "forward:/adminjsps/msg.jsp";
        }
    }

    //发货
    @RequestMapping("/deliver")
    public String deliver(HttpServletRequest request,HttpServletResponse response, Model model)throws Exception{

        String oid = request.getParameter("oid");
        int status = orderService.findStatusByOid(oid);
        if (status==0){
            request.setAttribute("code","error");
            request.setAttribute("msg","查询状态错误！");
            return "forward:/adminjsps/msg.jsp";
        }
        if (status!=2){
            request.setAttribute("code","error");
            request.setAttribute("msg","状态错误，不能完成发货");
            return "forward:/adminjsps/msg.jsp";
        }else {
            orderService.updateStatusByOid(oid,3);
            request.setAttribute("code","success");
            request.setAttribute("msg","恭喜您操作成功，请查看物流信息确认收货！");
            return "forward:/adminjsps/msg.jsp";
        }
    }
}
