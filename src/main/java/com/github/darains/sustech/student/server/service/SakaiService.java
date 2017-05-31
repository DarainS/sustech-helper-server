package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.dto.homework.SakaiHomework;
import com.github.darains.sustech.student.server.casclient.SakaiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SakaiService{
    
    @Autowired
    private SakaiClient sakaiClient;
    
    @Cacheable(value = "homework",  key = "'homework_'+#p0")
    public SakaiHomework getCachedSakaiHomework(String userid,String password){
        return getAllHomeworks(userid, password);
    }
    
    @CachePut(value = "homework", key = "'homework_'+#p0")
    public SakaiHomework getRefreshedSakaiHomework(String userid,String password){
        return getAllHomeworks(userid, password);
    }
    
    private SakaiHomework getAllHomeworks(String userid, String password){
        SakaiHomework sakaiHomework=new SakaiHomework();
        sakaiClient.casLogin(userid,password);
        sakaiHomework.setCourseHomeworks(sakaiClient.allHomeworks())
            .setCookie(sakaiClient.getCookie())
            .setStudentid(userid);
        return sakaiHomework;
    }
    
}
