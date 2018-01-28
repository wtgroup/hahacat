package org.xxz.test;

import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.xxz.test.domain.User;
import org.xxz.test.mapper.UserMapper;

public class MybatisTest {
    
    public static void main(String[] args) throws Exception {
        String configPath = "test/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(configPath);
        Properties properties = new Properties();
        properties.put("driver", "com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://127.0.0.1:3306/mybatis_test");
        properties.put("username", "root");
        properties.put("password", "root");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.findUser(1L);
        System.out.println(user);
        session.close();
    }

}
