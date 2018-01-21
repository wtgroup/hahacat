package com.wtgroup.comb.mapper;

import com.wtgroup.comb.pojo.Customer;
import com.wtgroup.comb.pojo.vo.QueryVo;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-19-15:12
 */
public interface CustomerMapper {

    public List<Customer> queryCustomersByVo(QueryVo vo);


    Long queryCountByVo(QueryVo queryVo);
}
