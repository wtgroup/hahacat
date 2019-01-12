package com.wtgroup.demo.sbproxy;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/18 1:27
 */
@SpringBootApplication
public class MasterApplication {
    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);
        SpringApplication sa = new SpringApplication(MasterApplication.class);
        sa.setBannerMode(Banner.Mode.OFF);
        sa.run(args);
    }
}
