package com.wtgroup.service;

import com.wtgroup.bean.Orders;
import com.wtgroup.bean.OrdersCustom;
import com.wtgroup.bean.QueryVo;
import com.wtgroup.mapper.OrdersCustomMapper;
import com.wtgroup.mapper.OrdersMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-17-17:19
 */
@Service
public class OrdersServiceImpl implements OrdersService {
    //注入mapper查询数据库
    @Resource
    private OrdersCustomMapper ordersCustomMapper;
    @Resource
    private OrdersMapper ordersMapper;

    public List<OrdersCustom> queryOrdersByVo(QueryVo vo) {
        List<OrdersCustom> ordersCustoms = ordersCustomMapper.queryOrdersByVo(vo);
        return ordersCustoms;
    }

    public OrdersCustom queryOrdersById(Integer id) {

        //可以直接借助逆向工程生成的Example查询
        Orders orders = ordersMapper.selectByPrimaryKey(id);

        //orders需要转换封装为ordersCustom
        OrdersCustom ordersCustom = new OrdersCustom();
        //!!!
        BeanUtils.copyProperties(orders,ordersCustom);


        return ordersCustom;
    }


    public void update(OrdersCustom ordersCustom) {
        Orders orders = new Orders();
//        BeanUtils.copyProperties(ordersCustom,orders);
//        ordersMapper.updateByPrimaryKey(orders);
        ordersMapper.updateByPrimaryKey(ordersCustom);  //利用多态
    }

    public void updateInBatch(QueryVo vo) {
        ordersCustomMapper.updateInBatchAndSelectiveById(vo.getOrdersList());
    }

    public OrdersCustom queryOrdersCustomById(Integer id) {
        OrdersCustom ordersCustom = ordersCustomMapper.selectByPrimaryKey(id);

        return ordersCustom;
    }
}
