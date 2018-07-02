package com.wtgroup.demo.demospringboot.bean;


import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-06-30-20:45
 */
public class Girl {

    private String id;

    @NotBlank(message = "姓名不能为空白字符串")
    private String name;
    //两个条件需要同时满足
    @Min(value = 18,message = "低于18周岁禁止入内")
    @Range(min = 20,max = 30,message = "仅限20~30岁")
    private Integer age;
    private String msg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Girl setAge(Integer age) {
        this.age = age;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Girl setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", msg='" + msg + '\'' +
                '}';
    }
}
