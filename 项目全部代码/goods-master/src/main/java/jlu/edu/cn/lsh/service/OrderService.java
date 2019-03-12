package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.po.Order;
import jlu.edu.cn.lsh.po.OrderCustom;
import jlu.edu.cn.lsh.po.Orderitem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    //通过uid查询order信息及数量
    public List<OrderCustom> findOrderByUid(int pc, String uid)throws Exception;
    public int findOrderCountByUid(String uid)throws Exception;

    //根据oid查询orderitem信息
    public List<Orderitem> findOrderItemsByOid(String oid)throws Exception;

    //插入信息
    public OrderCustom insertOrder(BigDecimal total, String address, String uid)throws Exception;
    public Orderitem insertOrderitem(int quantity,BigDecimal subtol,String bid,String bname,BigDecimal currprice,String imageb,String oid)throws Exception;

    //根据oid查询ordercustom信息
    public OrderCustom findOrderByOid(String oid)throws Exception;

    //根据oid查询、修改状态
    public int findStatusByOid(String oid)throws Exception;
    public void updateStatusByOid(String oid,int status)throws Exception;

    //查询所有订单
    public List<OrderCustom> findAll(int pc)throws Exception;
    public int findAllOrderCount()throws Exception;

    //根据状态查询所有订单
    public List<OrderCustom> findOrdersByStatus(int pc,int status)throws Exception;
    public int findCountByStatus(int status)throws Exception;

}
