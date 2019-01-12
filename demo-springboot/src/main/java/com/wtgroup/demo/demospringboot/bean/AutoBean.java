package com.wtgroup.demo.demospringboot.bean;

import org.springframework.stereotype.Component;

/**
 * 用于自动注册的
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/30 20:22
 */
@Component
public class AutoBean {

    public void print() {
        System.out.println("AutoBean::");
    }
}
