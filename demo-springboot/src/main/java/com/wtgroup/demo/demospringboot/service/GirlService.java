package com.wtgroup.demo.demospringboot.service;

import com.wtgroup.demo.demospringboot.bean.Girl;
import org.springframework.stereotype.Service;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-23:31
 */
@Service
public class GirlService {

    public Girl getAge(Girl girl) throws Exception {
        Girl g = queryGirl(girl);
        //if (g.getAge()<=14) {
            //throw new Exception("小学生, 哼!");
        //}

        return g;
    }

    private Girl queryGirl(Girl girl) {
        girl.setMsg(girl.toString());
        return girl;
    }
}
