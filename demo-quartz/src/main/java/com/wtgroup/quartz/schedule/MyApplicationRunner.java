package com.wtgroup.quartz.schedule;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 调度任务类
 *
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @date 2018/5/1 23:54
 */
@Component
//@Order(value = 1)     //指定顺序
public class MyApplicationRunner implements ApplicationRunner {

    //开机启动的方法

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        System.out.println("开机启动的方法OK....");
    }
}
