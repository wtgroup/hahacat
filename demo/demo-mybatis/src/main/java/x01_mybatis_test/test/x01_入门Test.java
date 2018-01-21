package x01_mybatis_test.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import x01_mybatis_test.bean.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-14-14:55
 */
public class x01_入门Test {

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


    /**
     * 根据id查询
     */
    @Test
    public void fun01() {
        //打开会话
        SqlSession sqlSession = sessionFactory.openSession();
        //借助会话执行SQL
        //1.定位sql语句映射; 2.注入的参数
        Object user = sqlSession.selectOne("findUserById", 1);
        System.out.println(user);

        //关闭会话
        sqlSession.close();
    }

    /**
     * 根据姓名模糊查询
     */
    @Test
    public void fun02() {
        SqlSession sqlSession = sessionFactory.openSession();
        List<Object> list = sqlSession.selectList("findUserUsername", "三");
        System.out.println(list);

        sqlSession.close();

    }


    /**
     * 新增用户
     */
    @Test
    public void fun03() {
        SqlSession sqlSession = sessionFactory.openSession();
        User user = new User();
        user.setUsername("王昭君");
        user.setSex("女");
        user.setBirthday(new Date());   //自动转换日期类型
        int i = sqlSession.insert("addUser", user); //返回影响了几行

        sqlSession.commit();

        sqlSession.close();

    }

    /**
     * 新增用户并且返回id
     */
    @Test
    public void fun04() {
        SqlSession sqlSession = sessionFactory.openSession();
        User user = new User();
        user.setUsername("项羽4");
        user.setSex("男");
        user.setBirthday(new Date());   //自动转换日期类型
        int i = sqlSession.insert("addUser1", user); //返回影响了几行
        sqlSession.commit();


        sqlSession.close();

        //查看id值
        System.out.println(user.getId());
    }

    /**
     * 更新用户
     */
    @Test
    public void fun05() {
        SqlSession sqlSession = sessionFactory.openSession();
        User user = new User();
        user.setUsername("虞姬");
        user.setSex("女");
        user.setBirthday(new Date());   //自动转换日期类型
        user.setId(32);

        int i = sqlSession.insert("updateUser", user); //返回影响了几行
        sqlSession.commit();

        sqlSession.close();
    }

}
