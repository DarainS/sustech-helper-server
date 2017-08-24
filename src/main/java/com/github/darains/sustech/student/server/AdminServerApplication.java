package com.github.darains.sustech.student.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
public class AdminServerApplication{
    
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
    
}
