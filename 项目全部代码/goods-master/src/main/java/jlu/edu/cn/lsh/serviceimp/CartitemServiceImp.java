package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.mapper.CartitemMapper;
import jlu.edu.cn.lsh.po.*;
import jlu.edu.cn.lsh.service.CartitemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartitemServiceImp implements CartitemService {
    @Autowired
    private CartitemMapper cartitemMapper;

    @Override
    public List<CartitemVo> myCart(String uid) throws Exception {
        List<CartitemVo> cartitemVos = cartitemMapper.findCartByUid(uid);
        for (int i = 0;i<cartitemVos.size();i++){
            BigDecimal b1 = cartitemVos.get(i).getCurrprice();
            BigDecimal b2 = new BigDecimal(cartitemVos.get(i).getQuantity()+"");
            cartitemVos.get(i).setSubtotal(b1.multiply(b2));
        }
        return cartitemVos;
    }

    @Override
    public CartitemVo findByUidAndBid(String bid, String uid) throws Exception {
        Map<String,String> map = new HashMap<String,String>();
        map.put("bid",bid);
        map.put("uid",uid);
        CartitemVo cartitemVo = cartitemMapper.findByUidAndBid(map);
        return cartitemVo;
    }

    @Override
    public void updateQuantityByCartItemId(int quantity, String cartItemId) throws Exception {
        Cartitem cartitem = cartitemMapper.selectByPrimaryKey(cartItemId);
        cartitem.setQuantity(quantity);
        cartitemMapper.updateByPrimaryKeySelective(cartitem);
    }

    @Override
    public void insetCartItem(int quantity, String bid, String uid) throws Exception {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("\\-","");
        String cartitemid = uuid.substring(0,32);
        Cartitem cartitem = new Cartitem();
        cartitem.setCartitemid(cartitemid);
        cartitem.setQuantity(quantity);
        cartitem.setBid(bid);
        cartitem.setUid(uid);
        cartitemMapper.insertSelective(cartitem);
    }

    @Override
    public void deleteCartItemByCartItemId(String cartItemId) throws Exception {
        cartitemMapper.deleteByPrimaryKey(cartItemId);
    }

    @Override
    public CartitemVo updateQuantityByCariItemId2(String cartitemid, int quantity) throws Exception {
        Cartitem cartitem = cartitemMapper.selectByPrimaryKey(cartitemid);
        cartitem.setQuantity(quantity);
        cartitemMapper.updateByPrimaryKeySelective(cartitem);

        CartitemVo cartitemVo = cartitemMapper.findByCartItemId(cartitemid);

        BigDecimal b1 =new BigDecimal(cartitemVo.getCurrprice()+"");
        BigDecimal b2 = new BigDecimal(cartitemVo.getQuantity()+"");
        BigDecimal b3 = b1.multiply(b2);
        cartitemVo.setSubtotal(b3);

        return cartitemVo;
    }

    @Override
    public CartitemVo findCartItemByCartItemId(String cartitemid) throws Exception {

        CartitemVo cartitemVo = cartitemMapper.findByCartItemId(cartitemid);
        return cartitemVo;
    }
}
