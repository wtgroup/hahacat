package com.wtgroup.blog.controller;

import com.wtgroup.blog.annotation.Security;
import com.wtgroup.blog.controller.vo.TestVo;
import com.wtgroup.blog.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-24-22:39
 */
@Controller
public class HelloController {

    @RequestMapping(value={"index"})    //index.html也会拦截到
    public String home(Model model){
        model.addAttribute("user", "赵子龙");

        //测试thymeleaf
        User user = new User();
        user.setName("李白_leaf");
        model.addAttribute("user", user);

        return "index";
    }


    @RequestMapping(value = "validator")
    @ResponseBody
    //!必须要有@Valid, 否则校验无效!
    public String testValidator(@Valid TestVo testVo, BindingResult result){
        //$前端传递过来的参数, 封装给testVo, 其字段上校验的结果信息会在
        // BindingResult中$
        if (result.hasErrors()) {
            //获取错误信息, 返回至页面显示
            return result.getAllErrors().get(0).getDefaultMessage();
        }
        return "success";
    }



    /**
     * 测试异常配置
     */
    @RequestMapping("exception")
    public void testException(){
        int i = 1 / 0;
        return;
    }

    /**
     * 测试权限控制拦截器
     */
    @RequestMapping("security")
    @Security
    @ResponseBody
    public String testSecurity(){

        return "security";
    }
}
