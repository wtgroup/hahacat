package com.wtgroup.demo.demospringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试aop
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-21:32
 */
@RestController
public class AopController {

    @RequestMapping(value = {"/aop"})
    public String aop(){
        System.out.println("进入controller中aop()");
        return "aop returning";
    }
}
