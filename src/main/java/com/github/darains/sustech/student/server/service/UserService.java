package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.entity.User;
import com.github.darains.sustech.student.server.entity.UserInfo;

public interface UserService{
    
    /**
     *
     * @param userid
     * @return 返回用户信息
     */
    User getUserByUserid(String userid);
    
    /**
     *
     * @param userid
     * @param password
     * @return 返回爬取的Userinfo类
     */
    UserInfo getUserInfo(String userid, String password);
    
    
}
