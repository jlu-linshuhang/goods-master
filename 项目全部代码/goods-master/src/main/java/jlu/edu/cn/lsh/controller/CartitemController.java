package jlu.edu.cn.lsh.controller;

import jlu.edu.cn.lsh.po.Cartitem;
import jlu.edu.cn.lsh.po.CartitemVo;
import jlu.edu.cn.lsh.po.User;
import jlu.edu.cn.lsh.service.CartitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartitemController {
    @Autowired
    private CartitemService cartitemService;

    @RequestMapping("/myCart")
    public String myCart(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        User user = (User) request.getSession().getAttribute("sessionUser");
        List<CartitemVo> cartitemVos =  cartitemService.myCart(user.getUid());
        request.setAttribute("cartItemList",cartitemVos);
        return "forward:/jsps/cart/list.jsp";
    }

    @RequestMapping("/add")
    public String add(Cartitem cartitem,HttpServletRequest request, HttpServletResponse response, Model model)throws Exception {
        User user = (User) request.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        String bid = cartitem.getBid();
        Integer quantity = cartitem.getQuantity();

        CartitemVo cartitemVo = cartitemService.findByUidAndBid(bid,uid);
        if (cartitemVo==null){
            cartitemService.insetCartItem(quantity,bid,uid);
        }else {
            int totalquantity = cartitemVo.getQuantity()+quantity;
            String cartitemid = cartitemVo.getCartitemid();
            cartitemService.updateQuantityByCartItemId(totalquantity,cartitemid);
        }
        List<CartitemVo> cartitemVos =  cartitemService.myCart(uid);
        request.setAttribute("cartItemList",cartitemVos);
        return "forward:/jsps/cart/list.jsp";
    }

    @RequestMapping("/delete")
    public String delete(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        String cartItemIds = request.getParameter("cartItemIds");
        cartitemService.deleteCartItemByCartItemId(cartItemIds);
        User user = (User) request.getSession().getAttribute("sessionUser");
        List<CartitemVo> cartitemVos =  cartitemService.myCart(user.getUid());
        request.setAttribute("cartItemList",cartitemVos);
        return "forward:/jsps/cart/list.jsp";
    }

    @RequestMapping("/deletebatch")
    public String deletebatch(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        String cartItemIds = request.getParameter("cartItemIds");
        Object[] a = cartItemIds.split(",");
        for (int i = 0;i<a.length;i++){
            cartitemService.deleteCartItemByCartItemId(a[i]+"");
        }
        User user = (User) request.getSession().getAttribute("sessionUser");
        List<CartitemVo> cartitemVos =  cartitemService.myCart(user.getUid());
        request.setAttribute("cartItemList",cartitemVos);
        return "forward:/jsps/cart/list.jsp";
    }

    @RequestMapping("/updateQuantity")
    public @ResponseBody CartitemVo updateQuantity(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        String cartitemid= request.getParameter("cartitemid");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        CartitemVo cartitemVo = cartitemService.updateQuantityByCariItemId2(cartitemid,quantity);
        return cartitemVo;
    }

    @RequestMapping("/loadCartItems")
    public String loadCartItems(HttpServletRequest request, HttpServletResponse response, Model model)throws Exception{
        String cartItemIds = request.getParameter("cartItemIds");
        Double total = Double.valueOf(request.getParameter("total"));
        Object[] a = cartItemIds.split(",");
        List<CartitemVo> cartitemVoList = new ArrayList<CartitemVo>();
        for (int i = 0;i<a.length;i++){
            CartitemVo cartitemVo = cartitemService.findCartItemByCartItemId(a[i]+"");
            BigDecimal b1 =new BigDecimal(cartitemVo.getCurrprice()+"");
            BigDecimal b2 = new BigDecimal(cartitemVo.getQuantity()+"");
            BigDecimal b3 = b1.multiply(b2);
            cartitemVo.setSubtotal(b3);
            cartitemVoList.add(cartitemVo);
        }
        request.setAttribute("cartItemList",cartitemVoList);
        request.setAttribute("cartItemIds",cartItemIds);
        request.setAttribute("total",total);
        return "/jsps/cart/showitem.jsp";
    }
}
