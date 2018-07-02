package com.wtgroup.demo.hbase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/10 19:29
 */
@Component
//@PropertySource("classpath:application.properties")
@PropertySource("classpath:person.yml")
@ConfigurationProperties(prefix = "lj")
public class PersonConfig {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public PersonConfig setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public PersonConfig setAge(Integer age) {
        this.age = age;
        return this;
    }
}
