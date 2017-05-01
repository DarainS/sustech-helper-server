package com.github.darains.sustechserver.controller;

import com.github.darains.sustechserver.dto.HttpResult;
import com.github.darains.sustechserver.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Slf4j
@RestController
public class LoginController{
    
    @Autowired
    UserServiceImpl userService;
    
    @PostMapping(value = "/login")
    public Object login(@RequestParam @NotNull String username, @RequestParam @NotNull String password){
        log.info("login: {}:{}",username,password);
        HttpResult r=new HttpResult();
        if (userService.checkPassword(username,password)){
            r.setMsg("success");
        }
        else {
            r.setMsg("用户名或密码错误！");
        }
        return r;
    }
    
    @GetMapping("/userinfo/{username}")
    @PreAuthorize("principal.username.equals(#username)")
    public HttpResult userInfo(@PathVariable String username){
//        userService.getUserInfo()
        HttpResult r=new HttpResult();
        r.setResult(userService.getUserByUserid(username));
        return r;
    }
    
}
