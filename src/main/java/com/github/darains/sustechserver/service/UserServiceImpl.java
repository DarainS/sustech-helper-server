package com.github.darains.sustechserver.service;

import com.github.darains.sustechserver.entity.CustomUserDetails;
import com.github.darains.sustechserver.entity.User;
import com.github.darains.sustechserver.entity.UserInfo;
import com.github.darains.sustechserver.repository.UserRepository;
import com.github.darains.sustechserver.schoolclient.EducationalSystemClient;
import com.github.darains.sustechserver.schoolclient.SakaiClient;
import com.github.darains.sustechserver.schoolclient.StudentInfoClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService,UserService{
    @Autowired
    private EducationalSystemClient educationalSystemClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SakaiClient sakaiClient;
    @Autowired
    private StudentInfoClient studentInfoClient;
    
    @PreAuthorize("principal.username.equals(#userid)")
    public User getUserByUserid(String userid){
        return userRepository.getUserByUserid(userid);
    }
    
    public UserInfo getUserInfo(String userid,String password){
        String ticket=studentInfoClient.checkPassword(userid,password);
        if (StringUtils.isBlank(ticket)){
            return null;
        }
        return studentInfoClient.generateUserInfo(ticket);
    }
    
    public boolean checkPassword(String id,String password){
        User user=userRepository.getUserByUserid(id);
        if (user.getPassword().equals(password)){
            return true;
        }
        String ticket = educationalSystemClient.casLogin(id,password);
        if (StringUtils.isBlank(ticket)){
            tryInsertUser(new User().setUserid(id).setPassword(password));
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean tryInsertUser(User user){
        User that=userRepository.getUserByUserid(user.getUserid());
        if (that==null){
            userRepository.insertUser(user);
            return true;
        }
        return false;
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserid(userid);
        
        if (user == null) {
            throw new UsernameNotFoundException("Could not find the user '" + userid + "'");
        }
        
        
        // Not involve authorities, so pass null to authorities
        return new CustomUserDetails(user, true, true, true, true, null);
    }
    
}
