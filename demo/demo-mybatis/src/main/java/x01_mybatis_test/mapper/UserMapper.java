package x01_mybatis_test.mapper;

import x01_mybatis_test.bean.QueryVo;
import x01_mybatis_test.bean.User;

import java.util.List;

/**
 * 用于动态创建mapper代理的接口, 相当于之前dao.
 * 区别是无需实现类.
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-14-20:48
 */
public interface UserMapper {

//    public User findUserById(Integer id);

    public User findUserById(User user);

    public User findUserById_alias(User user);

    public User findUser(User user);

    public User findUserByVo(QueryVo vo);

    public Integer countUser();

    public User findByIdAndUsername(User user);


    //根据多个id查
    public List<User> findByVoIdlist(QueryVo vo);
    public List<User> findByVoIdarray(QueryVo vo);
    public List<User> findByIdlist(List<Integer> idlist);
    public List<User> findByIdarray(Integer[] idarray);



}
