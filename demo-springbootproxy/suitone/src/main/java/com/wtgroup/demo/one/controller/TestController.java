package com.wtgroup.demo.one.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/18 3:09
 */
@Controller
public class TestController {

    @RequestMapping("/one/test")
    @ResponseBody
    public String test() {
        System.out.println("/one/test");
        return "OK...";
    }

}
