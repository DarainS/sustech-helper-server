package com.github.darains.sustech.student.server.repository;

import com.github.darains.sustech.student.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository{
    User getUserByUserid(@Param("userid") String id);
    int insertUser(User user);
}
