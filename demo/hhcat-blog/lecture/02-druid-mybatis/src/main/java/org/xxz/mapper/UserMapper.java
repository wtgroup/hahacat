package org.xxz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.xxz.domain.User;

@Mapper
public interface UserMapper {
    
    User findUser(Long id);

    int saveUser(User user);

    int updateUser(User user);
    
    int deleteUser(Long id);

    List<User> findAll();
    
}
