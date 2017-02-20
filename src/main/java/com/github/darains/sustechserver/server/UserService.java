package com.github.darains.sustechserver.server;

import com.github.darains.sustechserver.entity.CustomUserDetails;
import com.github.darains.sustechserver.entity.User;
import com.github.darains.sustechserver.repository.UserRepository;
import com.github.darains.sustechserver.schoolcas.CasClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
@Slf4j
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CasClient casClient;
    
    public boolean checkPassword(String id,String password){
        boolean check = casClient.checkPasswordBySakai(id,password);
        if (check){
            tryInsertUser(new User().setUserid(id).setPassword(password));
        }
        return check;
    }
    
    public boolean tryInsertUser(User user){
        User that=userRepository.getUserByUserId(user.getUserid());
        if (that==null){
            userRepository.insertUser(user);
            return true;
        }
        return false;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserId(userid);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user '" + userid + "'");
        }
        
        // Not involve authorities, so pass null to authorities
        return new CustomUserDetails(user, true, true, true, true, null);
    }
    
}
