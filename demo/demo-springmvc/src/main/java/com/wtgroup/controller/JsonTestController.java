package com.wtgroup.controller;

import com.wtgroup.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * json数据传输测试
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-18-15:33
 */
@Controller
public class JsonTestController {
    
    /**
     * json
     */
    @RequestMapping("/ajaxJson.action")
    public @ResponseBody User ajaxJson(@RequestBody User user){
        System.out.println(user);
        user.setUsername("刘和骏");
        return user;
    }


    /**
     * key-value
     */
    @RequestMapping("/ajaxKeyValue.action")
    @ResponseBody
    public User ajaxKeyValue(User user){
        System.out.println(user);
        user.setUsername("kv_刘和骏");
        return user;
    }
}
