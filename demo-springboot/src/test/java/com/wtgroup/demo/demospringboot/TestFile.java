package com.wtgroup.demo.demospringboot;

import org.junit.Test;

import java.io.File;

/**
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @date 2018/5/19 23:27
 */
public class TestFile {


    @Test
    public void fun01(){
        String savePath = "C:\\Users\\Nisus Liu\\AppData\\Local\\Temp\\tomcat-docbase.7720670163432587868.8080\\WEB-INF\\upload";
        File file = new File(savePath);

        boolean mkdirs = file.mkdirs();
        System.out.println(mkdirs);

    }

    @Test
    public void fun02(){
        File path=new File("/xxxxxxxxx");
        if (!path.exists()) path = new File("");        //自动是项目根目录
        System.out.println("jar包根目录:" + path.getAbsolutePath());

    }
}
