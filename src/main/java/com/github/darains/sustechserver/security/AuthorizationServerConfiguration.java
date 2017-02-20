package com.github.darains.sustechserver.security;

import com.github.darains.sustechserver.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Xiaoyue Xiao
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
                .withClient("client")
                .secret("app")
                .authorizedGrantTypes("password", "refresh_token")
            .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                .scopes("read", "write");
        
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore)
            .authenticationManager(authenticationManager)
            .userDetailsService(userService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true); // support refresh token
        tokenServices.setTokenStore(tokenStore); // use in-memory token store
        return tokenServices;
    }
    

    /** * 资源服务器 * @author penghaozhong * */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{

//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources.resourceId("app").stateless(false);
//        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //无须授权即可访问的页面
                .antMatchers("/", "/home","/index","/login").permitAll()
    
                //任何访问都必须授权
                .anyRequest().fullyAuthenticated()
                .anyRequest().authenticated();
            
                //.formLogin()
                //登陆成功后的处理，因为是API的形式所以不用跳转页面
                //.successHandler(new AuthSuccessHandler())
//            .and().addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //登陆失败后的处理
                //.failureHandler(new SimpleUrlAuthenticationFailureHandler())
                //.loginPage("/login").permitAll()
        
                
                //.and().logout().permitAll();
        }

    }
    
}
