package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.casclient.EducationalSystemClient;
import com.github.darains.sustech.student.server.dto.course.CourseTable;
import com.github.darains.sustech.student.server.dto.grade.StudentAllTermGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class EducationalSystemService{
    
    @Autowired
    EducationalSystemClient educationalSystemClient;
    
    //course table
    
    /**
     * 从缓存读取数据
     * @param userid
     * @param password
     * @return 学生的课表信息
     */
    @Cacheable(value = "courseTable",key = "'courseTable_'+#p0")
    public CourseTable getCachedStudentCourseTable(String userid, String password){
        return getStudentCourseTable(userid, password);
    }
    
    /**
     * 爬取数据并存入缓存
     * @param userid
     * @param password
     * @return 学生的课表信息
     */
    @CachePut(value = "courseTable",key = "'courseTable_'+#p0")
    public CourseTable getRefreshedStudentCourseTable(String userid, String password){
        return getStudentCourseTable(userid, password);
    }
    
    public CourseTable getStudentCourseTable(String userid,String password,boolean refresh){
        if (refresh){
            return getRefreshedStudentCourseTable(userid, password);
        }
        
        return getCachedStudentCourseTable(userid, password);
    }
    
    private CourseTable getStudentCourseTable(String userid, String password){
        String cookie=educationalSystemClient.casLogin(userid,password);
        return new CourseTable().setStudentid(userid).setCourses(educationalSystemClient.crawlCourseTable(cookie));
    }
    
    
    
    //grade
    
    @Cacheable(value = "grade",key = "'grade_'+#p0")
    public StudentAllTermGrade getCachedStudentAllTermGrade(String userid, String password){
        return getStudentAllTermGrade(userid, password);
    }
    
    @CachePut(value = "grade",key = "'grade_'+#p0")
    public StudentAllTermGrade getRefreshedStudentAllTermGrade(String userid, String password){
        return getStudentAllTermGrade(userid, password);
    }
    
    private StudentAllTermGrade getStudentAllTermGrade(String userid, String password){
        String cookie=educationalSystemClient.casLogin(userid,password);
        return educationalSystemClient.crawlStudentAllTermGrades(cookie);
    }
    
    
}
