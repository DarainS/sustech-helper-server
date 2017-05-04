package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.entity.UserInfo;
import com.github.darains.sustech.student.server.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService{
    
    @PreAuthorize("hasRole('ADMIN') || principal.username.equals(#userid)")
    User getUserByUserid(String userid);
    
    UserInfo getUserInfo(String userid, String password);
    
    
}
