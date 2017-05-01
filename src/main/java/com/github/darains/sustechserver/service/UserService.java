package com.github.darains.sustechserver.service;

import com.github.darains.sustechserver.entity.User;
import com.github.darains.sustechserver.entity.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService{
    
    @PreAuthorize("hasRole('ADMIN') || principal.username.equals(#userid)")
    User getUserByUserid(String userid);
    
    UserInfo getUserInfo(String userid, String password);
    
    
}
