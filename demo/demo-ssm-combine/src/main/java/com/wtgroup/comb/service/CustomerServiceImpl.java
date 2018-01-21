package com.wtgroup.comb.service;

import com.wtgroup.comb.mapper.CustomerMapper;
import com.wtgroup.comb.pojo.Customer;
import com.wtgroup.comb.pojo.vo.QueryVo;
import com.wtgroup.utils.paginationtag.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-19-16:09
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    public QueryVo queryPageByVo(QueryVo queryVo) {
        //查询总行数
        Long count = customerMapper.queryCountByVo(queryVo);

        Page<Customer> page = new Page<Customer>(queryVo.getCurrentPage(), queryVo.getPageSize(), count);

        queryVo.setPage(page);

        //查询分页数据
        List<Customer> customers = customerMapper.queryCustomersByVo(queryVo);
        page.setRows(customers);
        return queryVo;
    }
}
