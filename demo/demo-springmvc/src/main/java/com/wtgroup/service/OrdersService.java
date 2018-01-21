package com.wtgroup.service;

import com.wtgroup.bean.OrdersCustom;
import com.wtgroup.bean.QueryVo;

import java.util.List;

public interface OrdersService {

    public List<OrdersCustom> queryOrdersByVo(QueryVo vo);

    OrdersCustom queryOrdersById(Integer id);

    void update(OrdersCustom ordersCustom);

    void updateInBatch(QueryVo vo);

    OrdersCustom queryOrdersCustomById(Integer id);
}
