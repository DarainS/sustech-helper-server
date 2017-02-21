package com.github.darains.sustechserver.controller;

import com.github.darains.sustechserver.entity.Result;
import com.github.darains.sustechserver.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class LoginController{
    
    @Autowired
    UserServiceImpl userService;
    
    @PostMapping(value = "/login")
    public Object login(@RequestParam @NotNull String userid, @RequestParam @NotNull String password){
        Result r=new Result();
        if (userService.checkPassword(userid,password)){
            r.setHttpStatus(HttpStatus.OK);
            r.setDescription("success");
        }
        else {
            r.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            r.setDescription("用户名或密码错误！");
        }
        return r;
    }
    
    @GetMapping("/userinfo/{userid}")
//    @PreAuthorize("principal.username.equals(#userid)")
    public Result userInfo(@PathVariable String userid){
//        userService.getUserInfo()
        Result r=new Result();
        r.setResult(userService.getUserByUserid(userid));
        return r;
    }
    
}
