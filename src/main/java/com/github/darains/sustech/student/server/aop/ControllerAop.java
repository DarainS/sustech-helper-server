package com.github.darains.sustech.student.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class ControllerAop{
    
    @Pointcut("execution(public * com.github.darains.sustech.student.server.controller..*.*(..))")
    public void mm(){
    
    }
    
    @Before("mm()")
    public void before(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        try{
        UserDetails principal= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            log.info("user:{} ip:{} access {}",principal.getUsername(),request.getRemoteAddr(),request.getRequestURL().toString());
    
        }
        
        catch (ClassCastException e){
        
        }
    
        
    }
}
