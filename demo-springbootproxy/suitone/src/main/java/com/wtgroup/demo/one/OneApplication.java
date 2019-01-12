package com.wtgroup.demo.one;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/18 3:08
 */
@SpringBootApplication
public class OneApplication {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication sa = new SpringApplication(OneApplication.class);
        sa.setBannerMode(Banner.Mode.OFF);
        sa.run(args);
    }
}
