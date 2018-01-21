package x01_mybatis_spring;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import x01_mybatis_spring.bean.UserExample;
import x01_mybatis_spring.mapper.UserMapper;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-15-22:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class x01Test {



//    @Autowired
//    private ApplicationContext ac;

    //==mapper动态代理开发-增强版==//
    @Test
    public void fun01() {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapper mapp = ac.getBean(UserMapper.class);
        UserExample e = new UserExample();

        int i = mapp.countByExample(e);
        System.out.println(i);

    }

}
