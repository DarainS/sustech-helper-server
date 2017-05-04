package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.dto.homework.SakaiHomework;
import com.github.darains.sustech.student.server.casclient.SakaiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SakaiService{
    
    @Autowired
    private SakaiClient sakaiClient;
    
    
    public SakaiHomework getAllHomeworks(String userid, String password){
        SakaiHomework sakaiHomework=new SakaiHomework();
    
        sakaiClient.casLogin(userid,password);
        
        sakaiHomework.setCourseHomeworks(sakaiClient.allHomeworks())
            .setCookie(sakaiClient.getCookie())
            .setStudentid(userid);
        
        return sakaiHomework;
    }
    
}
