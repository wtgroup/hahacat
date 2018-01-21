package x01_mybatis_test.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import x01_mybatis_test.bean.QueryVo;
import x01_mybatis_test.bean.User;
import x01_mybatis_test.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-14-22:07
 */
public class x05_输出简单数据类型 {

    private SqlSessionFactory sessionFactory;
    @Before
    public void init() throws IOException {
        //创建会话窗口构造器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        //加载配置文件创建sql会话工厂
        InputStream cfg = Resources.getResourceAsStream("SqlMapConfig.xml");
        sessionFactory = sqlSessionFactoryBuilder.build(cfg);
        //!ibatis在创建好会话工厂后自动将流关闭了!

        //完成初始化, 最终要从工厂里那会话, 执行SQL

    }


    @Test
    public void fun01() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Integer c = mapper.countUser();
        System.out.println(c);

    }


}
