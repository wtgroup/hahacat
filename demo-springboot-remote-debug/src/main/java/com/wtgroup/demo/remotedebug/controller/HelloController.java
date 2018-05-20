package com.wtgroup.demo.remotedebug.controller;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-18-0:39
 */
//@Controller
public class HelloController {

//    @RequestMapping("/")
    public String test() {
        System.out.println("进入controller");

        System.out.println("正在执行controller...");

        return "debug OK?";
    }

}
