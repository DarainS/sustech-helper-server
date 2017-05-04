package com.github.darains.sustech.student.server;

import com.github.darains.sustech.student.server.entity.User;
import com.github.darains.sustech.student.server.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserRepository.class)
public class ApplicationTest{

    @Autowired
    UserRepository userRepository;
    
//	@Test
	public void contextLoads() {
	    
	}

	@Test
    public void getUserByUserId(){
        User user=userRepository.getUserByUserid("shawn");
        Assert.assertNotNull(user);
    }
}
