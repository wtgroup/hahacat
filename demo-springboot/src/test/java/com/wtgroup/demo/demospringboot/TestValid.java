package com.wtgroup.demo.demospringboot;

import com.wtgroup.demo.demospringboot.bean.vo.Status;
import org.junit.Test;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-23:52
 */
public class TestValid {
    @Test
    public void fun2(){
        System.out.println(Status.SUCCESS.abstractFun());
        System.out.println(Status.ERROR.abstractFun());
    }

    @Test
    public void fun1(){
        foo(null);
    }

    private void foo(@Valid @NotNull String param) {
        System.out.println(param);
    }
}
