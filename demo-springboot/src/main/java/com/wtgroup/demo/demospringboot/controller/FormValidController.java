package com.wtgroup.demo.demospringboot.controller;

import com.wtgroup.demo.demospringboot.bean.Girl;
import com.wtgroup.demo.demospringboot.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**测试表单验证功能
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-20:44
 */
@RestController
public class FormValidController {



    @PostMapping(value = "/addGirl")
    public Girl addGirl(@Valid Girl girl, BindingResult bindingResult) {       //对Girl进行验证 / 验证结果存于BindingResult中
        //判断是否验证通过
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            System.out.println(fieldError);
            String errorMsg = fieldError.getDefaultMessage();
            System.out.println(errorMsg);
            return girl.setMsg(errorMsg);
        }


        return girl;
    }
}
