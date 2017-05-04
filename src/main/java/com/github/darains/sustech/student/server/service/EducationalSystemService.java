package com.github.darains.sustech.student.server.service;

import com.github.darains.sustech.student.server.dto.Course;
import com.github.darains.sustech.student.server.dto.grade.StudentAllTermGrade;
import com.github.darains.sustech.student.server.schoolclient.EducationalSystemClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EducationalSystemService{
    
    @Autowired
    EducationalSystemClient educationalSystemClient;
    
    public Set<Course> resolveCourseTable(String userid, String password){
        String cookie=educationalSystemClient.casLogin(userid,password);
        return educationalSystemClient.crawlCourseTable(cookie);
    }
    
    public StudentAllTermGrade resolveStudentAllTermGrade(String userid,String password){
        String cookie=educationalSystemClient.casLogin(userid,password);
        return educationalSystemClient.crawlStudentAllTermGrades(cookie);
    }
    
}
