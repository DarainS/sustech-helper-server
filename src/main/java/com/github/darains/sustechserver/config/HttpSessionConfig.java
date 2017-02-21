package com.github.darains.sustechserver.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

//    @Bean
//    public LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory();
//    }

////    @Bean
////    public HttpSessionStrategy httpSessionStrategy() {
////        return new HeaderHttpSessionStrategy();
////    }
//
//
}
