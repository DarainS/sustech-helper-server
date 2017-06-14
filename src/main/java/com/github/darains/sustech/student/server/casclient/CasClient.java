package com.github.darains.sustech.student.server.casclient;

public interface CasClient{
    
    /*
       * @result 对cas系统，返回其登陆后的cookie系统，格式一般为JSEESION=XXXXXXXXX
     */
    String casLogin(String username,String password);
    
    //boolean casLogin(String username,String password);
    
}
