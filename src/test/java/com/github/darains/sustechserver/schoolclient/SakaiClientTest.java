package com.github.darains.sustechserver.schoolclient;

import com.github.darains.sustechserver.dto.homework.CourseHomework;
import com.github.darains.sustechserver.dto.homework.Homework;
import javaslang.Tuple2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SakaiClientTest{
    
    @Autowired
    SakaiClient sakaiClient;
    
    @Before
    public void before(){
        sakaiClient.casLogin("11310388","dengakak");
    }
    
    @Test
    public void resolveHomeworkTest(){
        Set<Tuple2> set= sakaiClient.resolveSiteUrls();
        System.out.println(set);
        for (Tuple2 s1:set){
            List<Homework> ls=sakaiClient.resolveHomeworks((String) s1._2());
            CourseHomework c=new CourseHomework();
            c.setCourseHomework(ls);
            c.setCourseName((String) s1._1());
            System.out.println(c);
        }
    }
    
    
    
}
