package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.casclient.EducationalSystemClient;
import com.github.darains.sustech.student.server.dto.course.CourseTable;
import com.github.darains.sustech.student.server.dto.grade.StudentAllTermGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationalSystemService{
    
    @Autowired
    EducationalSystemClient educationalSystemClient;
    
    public CourseTable getStudentCourseTable(String userid, String password){
        
        String cookie=educationalSystemClient.casLogin(userid,password);
        
        return new CourseTable().setStudentid(userid).setCourses(educationalSystemClient.crawlCourseTable(cookie));
    }
    
    public StudentAllTermGrade resolveStudentAllTermGrade(String userid,String password){
        String cookie=educationalSystemClient.casLogin(userid,password);
        return educationalSystemClient.crawlStudentAllTermGrades(cookie);
    }
    
}
