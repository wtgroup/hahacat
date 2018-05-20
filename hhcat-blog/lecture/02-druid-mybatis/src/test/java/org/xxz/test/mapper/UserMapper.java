package org.xxz.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.xxz.test.domain.User;

@Mapper
public interface UserMapper {
    
    User findUser(Long id);

}
