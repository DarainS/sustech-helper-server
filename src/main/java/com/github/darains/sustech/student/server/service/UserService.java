package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.entity.User;
import com.github.darains.sustech.student.server.entity.UserInfo;

public interface UserService{
    
    //@PreAuthorize("hasRole('ADMIN') || principal.username.equals(#userid)")
    User getUserByUserid(String userid);
    
    UserInfo getUserInfo(String userid, String password);
    
    
}
