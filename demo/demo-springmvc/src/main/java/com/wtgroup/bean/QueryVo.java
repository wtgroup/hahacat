package com.wtgroup.bean;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-17-16:59
 */
public class QueryVo {

    private OrdersCustom ordersCustom;

    private Integer[] ids;

    //

    private List<OrdersCustom> ordersList;
//    private List<Orders> ordersList;
    public OrdersCustom getOrdersCustom() {
        return ordersCustom;
    }

    public void setOrdersCustom(OrdersCustom ordersCustom) {
        this.ordersCustom = ordersCustom;
    }

    public List<OrdersCustom> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<OrdersCustom> ordersList) {
        this.ordersList = ordersList;
    }
}
