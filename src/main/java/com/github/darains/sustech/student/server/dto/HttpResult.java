package com.github.darains.sustech.student.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;



@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HttpResult{
    private int code;
    private Object result;
    private String msg;
    public static final String SUCCESS="success";
    public static final String UNKNOWN_ERROR ="unknown error";
    
    //public HttpResult setCode
    
    public static HttpResult success(Object o){
        return new HttpResult().setMsg(SUCCESS).setCode(HttpStatus.OK.value())
            .setResult(o);
    }
    
    
    
}
