package com.github.darains.sustechserver.security;

import com.github.darains.sustechserver.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
//@EnableOAuth2Client
@EnableResourceServer
//@Order(6)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    
    @Autowired
    private UserService userService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        // Configure spring security's authenticationManager with custom
        // user details service
        auth.userDetailsService(this.userService);
    }
    @Override
    
    @Bean // share AuthenticationManager for web and oauth
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //任何访问都必须授权
            .anyRequest().fullyAuthenticated()
            .antMatchers("/", "/home","/index").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            //登陆成功后的处理，因为是API的形式所以不用跳转页面
            .successHandler(new AuthSuccessHandler())
//            .and().addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            
            //登陆失败后的处理
            .failureHandler(new SimpleUrlAuthenticationFailureHandler())
            .loginPage("/login").permitAll()
            
            .and().logout().permitAll();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
    }
    
    
    
}
