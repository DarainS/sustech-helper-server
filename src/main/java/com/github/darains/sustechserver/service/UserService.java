package com.github.darains.sustechserver.service;

import com.github.darains.sustechserver.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UserService{
    
    @PreAuthorize("hasRole('ADMIN') || principal.username.equals(#userid)")
    User getUserByUserid(String userid);
    
}
