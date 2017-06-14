package com.github.darains.sustech.student.server.repository;

import com.github.darains.sustech.student.server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("userRepository")
public interface UserRepository{
    /**
     *
     * @param id 用户的SID
     * @return 返回用户的信息:User类
     */
    User getUserByUserid(@Param("userid") String id);
    
    /**
     *
     * @param user
     * @return 尝试插入user到数据库
     */
    int insertUser(User user);
}
