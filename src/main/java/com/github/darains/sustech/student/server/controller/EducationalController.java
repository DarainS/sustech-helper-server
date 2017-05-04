package com.github.darains.sustech.student.server.controller;

import com.github.darains.sustech.student.server.service.EducationalSystemService;
import com.github.darains.sustech.student.server.dto.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class EducationalController{
    
    @Autowired
    EducationalSystemService educationalService;
    
    @GetMapping("/user/course")
    public HttpResult getCourseTable(){
        HttpResult result=new HttpResult();
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        try{
            result
                .setResult(educationalService.resolveCourseTable(principal.getUsername(), principal.getPassword()))
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
    public HttpResult allStudentGrades(){
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal();
        HttpResult result=new HttpResult();
        try{
            result
                .setResult(educationalService.resolveStudentAllTermGrade(principal.getUsername(), principal.getPassword()))
                .setCode(200);
        }
        catch(Exception e){
            log.warn(e.getMessage());
            e.printStackTrace();
            result.setCode(402)
                .setMsg(HttpResult.UNKNOWN_ERROR);
        }
        return result;
    }
    
}
