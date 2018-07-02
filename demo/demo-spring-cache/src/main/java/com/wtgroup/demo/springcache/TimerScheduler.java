package com.wtgroup.demo.springcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/7 16:27
 */
@Component
public class TimerScheduler {
    @Autowired
    private AccountService accountService;
    /**
     * 每隔2秒执行一次
     */
//    @Scheduled(fixedRate = 1000*2)
//    public void print(){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
//        System.out.println("timer : "+format.format(new Date()));
//    }

    @Scheduled(fixedRate = 1000*2)
    public void evictCache(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        System.out.println("timer : "+format.format(new Date()));
        //定时清空缓存
        accountService.evictCache(new Account("somebody"));

    }

}
