package x01_mybatis_spring.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-15-22:45
 */
@Repository("userDao")
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {


    public void fun01() {
        System.out.println("正在获取sqlSession");
        System.out.println(this.getSqlSession());
    }
}
