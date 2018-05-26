package com.wtgroup.ohm.example.service;

import com.wtgroup.ohm.example.dao.CustomerEventDao;
import com.wtgroup.ohm.example.pojo.CustomerEvent;

import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-26-23:43
 */
public class CustomerEventService {



    public void testGetByRowKeyPrefix() {
        CustomerEventDao dao = new CustomerEventDao();
        List<CustomerEvent> res = dao.getByRowKeyPrefix("3333", "4444", "5555");
        System.out.println(res.size());
    }


}
