package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.po.CartitemVo;

import java.util.List;

public interface CartitemService {
    public List<CartitemVo> myCart(String uid)throws Exception;

    public CartitemVo findByUidAndBid(String bid,String uid)throws Exception;

    public void updateQuantityByCartItemId(int quantity,String cartItemId)throws Exception;

    public void insetCartItem(int quantity,String bid,String uid)throws Exception;

    public void deleteCartItemByCartItemId(String cartItemId)throws Exception;

    public CartitemVo updateQuantityByCariItemId2(String cartitemid,int quantity)throws Exception;

    public CartitemVo findCartItemByCartItemId(String cartitemid)throws Exception;
}
