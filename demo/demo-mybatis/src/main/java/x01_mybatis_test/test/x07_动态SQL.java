package x01_mybatis_test.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import x01_mybatis_test.bean.Orders;
import x01_mybatis_test.bean.QueryVo;
import x01_mybatis_test.bean.User;
import x01_mybatis_test.mapper.OrdersMapper;
import x01_mybatis_test.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-14-22:07
 */
public class x07_动态SQL {

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
     * where标签
     */
    @Test
    public void fun01() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User u = new User();
//        u.setId(32);
        u.setUsername("虞姬");
        User u_db = mapper.findByIdAndUsername(u);
        System.out.println(u_db);

    }

    /**
     * sql片段
     */
    @Test
    public void fun02() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User u = new User();
//        u.setId(32);
        u.setUsername("虞姬");
        User u_db = mapper.findByIdAndUsername(u);
        System.out.println(u_db);

    }

    /**
     * foreach
     */
    @Test
    public void fun03() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        ArrayList<Integer> idlist = new ArrayList<Integer>();
        idlist.add(24);
        idlist.add(25);
        idlist.add(26);

        QueryVo vo = new QueryVo();
        vo.setIdlist(idlist);
        List<User> users = mapper.findByVoIdlist(vo);

        for (User u : users) {
            System.out.println(u);
        }
    }


    @Test
    public void fun04() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        Integer[] idarray = new Integer[]{24, 25, 26};
        QueryVo vo = new QueryVo();
        vo.setIdarray(idarray);
        List<User> users = mapper.findByVoIdarray(vo);

        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void fun05() {
        SqlSession sqlSession = sessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

//        ArrayList<Integer> idlist = new ArrayList<>();
//        idlist.add(24);
//        idlist.add(25);
//        idlist.add(26);

//        List<User> users = mapper.findByIdlist(idlist);

        Integer[] idarray = new Integer[]{24, 25, 26};

        List<User> users = mapper.findByIdarray(idarray);

        for (User u : users) {
            System.out.println(u);
        }
    }


}
