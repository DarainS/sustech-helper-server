package com.github.darains.sustech.student.server;

import com.github.darains.sustech.student.server.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplicationTest2{
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void ff(){
        Assert.assertNotNull(userRepository.getUserByUserid("11310388"));
    }
}