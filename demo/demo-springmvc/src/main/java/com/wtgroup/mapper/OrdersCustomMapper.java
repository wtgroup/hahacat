package com.wtgroup.mapper;

import com.wtgroup.bean.Orders;
import com.wtgroup.bean.OrdersCustom;
import com.wtgroup.bean.QueryVo;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-17-17:00
 */
public interface OrdersCustomMapper {

    public List<OrdersCustom> queryOrdersByVo(QueryVo vo);

    public void updateInBatchAndSelectiveById(List<OrdersCustom> ordersList);

    public OrdersCustom selectByPrimaryKey(Integer id);
}
