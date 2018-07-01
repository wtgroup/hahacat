package com.wtgroup.demo.demospringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-21-23:44
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "/postman",method = RequestMethod.GET)
    public String postMan(String name) {
        System.out.println(name);
        return "ok";
    }


    @RequestMapping("/test")
    public String testCtrl() {
        log.info("testCtrl is running ....");

        return "hell spring boot, devtools, </br> 按 ctrl+shift+F9. </br><h2>不要devtools呢?</h2>";
    }
    
    @RequestMapping(value = {"/rest/{name}.you/{age}"})
    public String restCtrl(@PathVariable String name, @PathVariable int age){
        System.out.println(name);
        System.out.println(age);
        return "success";
    }
    

}
