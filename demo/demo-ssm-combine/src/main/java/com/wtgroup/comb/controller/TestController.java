package com.wtgroup.comb.controller;

import com.wtgroup.comb.test.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-18-23:46
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/fun.action")
    public void fun(User user){
        System.out.println(user);
    }
}
