package x01_mybatis_test.mapper;

import x01_mybatis_test.bean.Orders;
import x01_mybatis_test.bean.QueryVo;
import x01_mybatis_test.bean.User;

import java.util.List;

/**
 * 用于动态创建mapper代理的接口, 相当于之前dao.
 * 区别是无需实现类.
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-14-20:48
 */
public interface OrdersMapper {

    public List<Orders> findAll();

    public List<Orders> findWithUser();

    public List<User> findByUserJoinOrders();
}
