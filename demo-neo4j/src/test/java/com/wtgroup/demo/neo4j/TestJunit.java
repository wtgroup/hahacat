package com.wtgroup.demo.neo4j;

import com.wtgroup.demo.neo4j.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-17-17:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestJunit {
    //可以自动注入其他的bean
//    @Autowired
//    private aoto aoto;

    @Test
    public void fun01() {
        System.out.println("spring boot 整合 Junit is OK");
    }
}
