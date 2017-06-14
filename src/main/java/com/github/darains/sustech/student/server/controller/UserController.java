package com.github.darains.sustech.student.server.controller;

import com.github.darains.sustech.student.server.dto.homework.SakaiHomework;
import com.github.darains.sustech.student.server.service.UserService;
import com.github.darains.sustech.student.server.dto.HttpResult;
import com.github.darains.sustech.student.server.service.SakaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController{
    
    @Autowired
    private UserService userService;
    @Autowired
    private SakaiService sakaiService;
    
    /**
     *
     * @return 返回学生的个人信息,如学号,姓名等
     */
    @GetMapping("/user/info")
    public HttpResult userInfo(){
        HttpResult result=new HttpResult();
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            result.setResult(userService.getUserInfo(principal.getUsername(), principal.getPassword()));
        }
        catch(Exception e){
            log.warn(e.toString());
            result.setCode(402);
            result.setMsg(HttpResult.UNKNOWN_ERROR);
        }
        return result;
    }
    
    /**
     *
     * @param refresh 是否需要刷新缓存
     * @return 返回学生在sakai系统的所有加入站点的作业信息,按课程排列
     */
    @GetMapping("/sakai/homework")
    public HttpResult sakaiHomeworkds(@RequestParam(defaultValue = "false")String refresh){
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        HttpResult result=new HttpResult();
        SakaiHomework homework;
        
        if (("false".equals(refresh))){
            homework=sakaiService.getCachedSakaiHomework(principal.getUsername(),principal.getPassword());
        }else {
            homework=sakaiService.getRefreshedSakaiHomework(principal.getUsername(),principal.getPassword());
        }
    
        result.setResult(homework);
        
        return result;
    }
    
}
