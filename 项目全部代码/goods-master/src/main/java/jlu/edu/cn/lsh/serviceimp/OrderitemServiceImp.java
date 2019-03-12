package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.mapper.OrderitemMapper;
import jlu.edu.cn.lsh.po.Orderitem;
import jlu.edu.cn.lsh.po.OrderitemExample;
import jlu.edu.cn.lsh.service.OrderitemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderitemServiceImp implements OrderitemService {
    @Autowired
    private OrderitemMapper orderitemMapper;


}
