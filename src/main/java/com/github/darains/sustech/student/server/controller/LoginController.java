package com.github.darains.sustech.student.server.controller;

import com.github.darains.sustech.student.server.dto.HttpResult;
import com.github.darains.sustech.student.server.service.UserServiceImpl;
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
    
    /**
     *
     * @param username 用户的SID
     * @param password 用户的SID的登录密码
     * @return 返回用户登录成功与否
     */
    @PostMapping(value = "/login")
    public Object login(@RequestParam @NotNull String username, @RequestParam @NotNull String password){
        log.info("login: {}",username);
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
        String s;
        HttpResult r=new HttpResult();
        r.setResult(userService.getUserByUserid(username));
        return r;
    }
    
}
