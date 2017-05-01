package com.github.darains.sustechserver.service;

import com.github.darains.sustechserver.dto.homework.CourseHomework;
import com.github.darains.sustechserver.dto.homework.Homework;
import com.github.darains.sustechserver.dto.homework.SakaiHomework;
import com.github.darains.sustechserver.schoolclient.SakaiClient;
import javaslang.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SakaiService{
    
    @Autowired
    private SakaiClient sakaiClient;
    
    public void checkSakaiLogin(){
        if (true||StringUtils.isBlank(sakaiClient.getCookie())){
            UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("user {} login sakai client",principal.getUsername());
            sakaiClient=new SakaiClient();
            sakaiClient.casLogin(principal.getUsername(),principal.getPassword());
        }
    }
    
    public Set<CourseHomework> allHomeworks(){
        checkSakaiLogin();
        
        Set<Tuple2> set= sakaiClient.resolveSiteUrls();
        
        Set<CourseHomework> result=new HashSet<>();
        
        for (Tuple2 s1:set){
            CourseHomework c=new CourseHomework();
            List<Homework> ls=sakaiClient.resolveHomeworks((String)s1._2());
            c.setCourseName((String)s1._1());
            c.setCourseHomework(ls);
            if (c.getCourseHomework().size()>0){
                result.add(c);
            }
        }
        return result;
    }
    
    public SakaiHomework sakaiHomework(){
        SakaiHomework h=new SakaiHomework();
        h.setHomework(allHomeworks());
        h.setCookie(sakaiClient.getCookie());
        return h;
    }
    
}
