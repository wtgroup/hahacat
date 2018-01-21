package x01_mybatis_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import x01_mybatis_spring.bean.User;
import x01_mybatis_spring.dao.UserDao;
import x01_mybatis_spring.mapper.UserMapper;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-15-22:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class x01Test {

    //==原始dao开发==//
    @Autowired
    private UserDao userDao;

    @Test
    public void fun01() {
        userDao.fun01();
    }

    //==mapper动态代理开发==//
    @Autowired
    private UserMapper userMapper;
    @Test
    public void fun02() {
        User user = new User();
        user.setId(10);
        User one = userMapper.findOne(user);
        System.out.println(one);

    }

    //==mapper动态代理开发-增强版==//
    @Test
    public void fun03() {
//       略
    }

}
