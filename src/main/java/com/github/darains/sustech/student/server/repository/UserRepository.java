package com.github.darains.sustech.student.server.repository;

import com.github.darains.sustech.student.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("userRepository")
public interface UserRepository{
    User getUserByUserid(@Param("userid") String id);
    int insertUser(User user);
}
