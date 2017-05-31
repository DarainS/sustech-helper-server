package com.github.darains.sustech.student.server.controller;

import com.github.darains.sustech.student.server.dto.grade.StudentAllTermGrade;
import com.github.darains.sustech.student.server.service.EducationalSystemService;
import com.github.darains.sustech.student.server.dto.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EducationalController{
    
    @Autowired
    EducationalSystemService educationalService;
    
    @GetMapping("/user/course")
    public HttpResult getCourseTable(@RequestParam(defaultValue = "false") boolean refresh){
        HttpResult result=new HttpResult();
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        try{
            result
                .setResult(educationalService.getCachedStudentCourseTable(principal.getUsername(), principal.getPassword()))
                .setCode(200);
        }
        catch(Exception e){
            log.warn(e.toString());
            result.setCode(402)
                .setMsg(HttpResult.UNKNOWN_ERROR);
        }
        return result;
    }
    
    @GetMapping("/user/grade/all")
    public HttpResult allStudentGrades(@RequestParam(defaultValue = "false") boolean refresh){
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        HttpResult result=new HttpResult();
        
        try{
            StudentAllTermGrade grade;
            if (refresh){
                grade=educationalService.getRefreshedStudentAllTermGrade(principal.getUsername(), principal.getPassword());
            }else {
                grade=educationalService.getCachedStudentAllTermGrade(principal.getUsername(), principal.getPassword());
            }
            grade.setStudentid(principal.getUsername());
            result
                .setResult(grade)
                .setCode(200);
        }
        catch(Exception e){
            log.warn(e.getMessage());
            e.printStackTrace();
            result.setCode(402)
                .setMsg(e.getMessage());
        }
        return result;
    }
    
}
