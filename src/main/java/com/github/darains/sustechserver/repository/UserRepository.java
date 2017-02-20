package com.github.darains.sustechserver.repository;

import com.github.darains.sustechserver.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository{
    User getUserByUserId(@Param("userid") String id);
    int insertUser(User user);
}
