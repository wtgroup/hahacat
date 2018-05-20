package com.wtgroup.demo.remotedebug.service;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-18-1:10
 */
public class TestAppRemoteDebug {

    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("test service .... ");

            }
        }, 100L, 3000L);
    }


}
