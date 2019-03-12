package jlu.edu.cn.lsh.po;

import java.util.List;

public class OrderCustom extends Order {
    private List<Orderitem> orderitemList;

    public List<Orderitem> getOrderitemList() {
        return orderitemList;
    }

    public void setOrderitemList(List<Orderitem> orderitemList) {
        this.orderitemList = orderitemList;
    }

    @Override
    public String toString() {
        return "OrderCustom{" +
                "orderitemList=" + orderitemList +
                '}';
    }
}
