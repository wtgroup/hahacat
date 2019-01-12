package com.wtgroup.demo.demospringboot.bean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManualBeanTest {
    @Autowired
    private ManualBean manualBean;

    @Test
    public void fun1(){
       manualBean.print();
    }

}