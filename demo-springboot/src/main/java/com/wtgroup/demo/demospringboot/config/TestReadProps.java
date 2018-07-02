package com.wtgroup.demo.demospringboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 测试读取配置文件
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-21-23:14
 */
@Component
public class TestReadProps {
    //--方式一--
    @Value("${test.name}")
    private String name;
    @Value(("${test.age}"))
    private int agexxx;     //

    //--方式二--
    @Resource
    private Environment environment;

    @Bean
    public Object testConf() {
        System.out.println(name);
        System.out.println(agexxx);

        System.out.println("---------------");
        System.out.println(environment.getProperty("test.skill"));
        System.out.println(environment.getProperty("test.speak"));


        return null;
    }



}
