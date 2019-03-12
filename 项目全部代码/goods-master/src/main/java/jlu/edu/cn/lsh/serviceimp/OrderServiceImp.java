package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.mapper.OrderMapper;
import jlu.edu.cn.lsh.mapper.OrderitemMapper;
import jlu.edu.cn.lsh.po.*;
import jlu.edu.cn.lsh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderitemMapper orderitemMapper;

    //int ps = PageConstants.Order_Page_Size;//订单每页记录数
      /*  private int pc;//当前页码，页面传过来的
    private int tp;//总页数，tr/ps
    private int tr;//总记录数,sql查询
    private int ps;//订单每页记录数，自定义8，可修改
    private String url;//第一次查询时的条件，地址*/

    @Override
    public List<OrderCustom> findOrderByUid(int pc, String uid) throws Exception {
        int ps = PageConstants.Order_Page_Size;//订单每页记录数
        int startNumber = (pc-1)*ps;
        OrderVo orderVo = new OrderVo();
        orderVo.setStartNumber(startNumber);
        orderVo.setUid(uid);
        orderVo.setPc(pc);
        orderVo.setPs(ps);
        List<OrderCustom> orderCustoms = orderMapper.findOrderByUid(orderVo);
        return orderCustoms;
    }

    @Override
    public int findOrderCountByUid(String uid) throws Exception {
        OrderExample orderExample = new OrderExample();
        orderExample.setDistinct(false);
        OrderExample.Criteria criteria= orderExample.createCriteria();
        criteria.andUidEqualTo(uid);
        int x = orderMapper.countByExample(orderExample);
        return x;
    }

    @Override
    public List<Orderitem> findOrderItemsByOid(String oid) throws Exception {
        OrderitemExample orderitemExample = new OrderitemExample();
        orderitemExample.setDistinct(false);
        OrderitemExample.Criteria criteria = orderitemExample.createCriteria();
        criteria.andOidEqualTo(oid);
        List<Orderitem> orderitemList = orderitemMapper.selectByExample(orderitemExample);
        return orderitemList;
    }

    @Override
    public OrderCustom insertOrder(BigDecimal total, String address, String uid) throws Exception {
        OrderCustom orderCustom = new OrderCustom();
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("\\-","");
        String oid = uuid.substring(0,32);


        orderCustom.setOid(oid);
        orderCustom.setOrdertime(String.format("%tF %<tT",new Date()));//将当前时间转换成String类型，并且要年月日、时分秒
        orderCustom.setTotal(total);
        orderCustom.setStatus(1);
        orderCustom.setAddress(address);
        orderCustom.setUid(uid);

        orderMapper.insert(orderCustom);
        return orderCustom;
    }

    @Override
    public Orderitem insertOrderitem(int quantity, BigDecimal subtol, String bid, String bname, BigDecimal currprice, String imageb, String oid) throws Exception {
        Orderitem orderitem = new Orderitem();
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("\\-","");
        String orderitemid = uuid.substring(0,32);

        orderitem.setOrderitemid(orderitemid);
        orderitem.setQuantity(quantity);
        orderitem.setSubtotal(subtol);
        orderitem.setBid(bid);
        orderitem.setBname(bname);
        orderitem.setCurrprice(currprice);
        orderitem.setImageb(imageb);
        orderitem.setOid(oid);

        orderitemMapper.insert(orderitem);
        return orderitem;
    }

    @Override
    public OrderCustom findOrderByOid(String oid) throws Exception {
        return orderMapper.findOrderByOid(oid);
    }

    @Override
    public int findStatusByOid(String oid) throws Exception {
        OrderExample orderExample = new OrderExample();
        orderExample.setDistinct(false);
        OrderExample.Criteria criteria= orderExample.createCriteria();
        criteria.andOidEqualTo(oid);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        if (orderList.size()==1){
            return orderList.get(0).getStatus();
        }else {
            return 0;
        }
    }

    @Override
    public void updateStatusByOid(String oid, int status) throws Exception {
        Order order = new Order();
        order.setOid(oid);
        order.setStatus(status);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public List<OrderCustom> findAll(int pc) throws Exception {
        int ps = PageConstants.Order_Page_Size;//订单每页记录数
        int startNumber = (pc-1)*ps;
        OrderVo orderVo = new OrderVo();
        orderVo.setStartNumber(startNumber);
        orderVo.setPc(pc);
        orderVo.setPs(ps);
        List<OrderCustom> orderCustoms = orderMapper.findAll(orderVo);
        return orderCustoms;
    }

    @Override
    public int findAllOrderCount() throws Exception {
        Integer a = orderMapper.findAllOrderCount();
        return a;
    }

    @Override
    public List<OrderCustom> findOrdersByStatus(int pc, int status) throws Exception {
        int ps = PageConstants.Order_Page_Size;//订单每页记录数
        int startNumber = (pc-1)*ps;
        OrderVo orderVo = new OrderVo();
        orderVo.setPs(ps);
        orderVo.setStartNumber(startNumber);
        orderVo.setStatus(status);
        List<OrderCustom> orderCustomList = orderMapper.findOrderByStatus(orderVo);
        return orderCustomList;
    }

    @Override
    public int findCountByStatus(int status) throws Exception {
        Integer a = orderMapper.findCountByStatus(status);
        return a;
    }
}
