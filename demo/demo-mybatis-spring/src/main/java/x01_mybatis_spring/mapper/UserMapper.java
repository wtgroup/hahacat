package x01_mybatis_spring.mapper;

import x01_mybatis_spring.bean.User;

public interface UserMapper {


    public User findOne(User user);

}
