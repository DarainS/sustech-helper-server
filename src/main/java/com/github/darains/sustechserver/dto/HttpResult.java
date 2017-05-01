package com.github.darains.sustechserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;



@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HttpResult{
    private int statusCode;
    private HttpStatus httpStatus;
    private Object result;
    private String desc;
    public static final String SUCCESS="success";
    public static final String UNKNOWN_ERROR ="unknown error";
    
    public void setHttpStatus(HttpStatus status){
        this.httpStatus=status;
        this.statusCode=status.value();
        if (status.equals(HttpStatus.OK)){
            this.desc=SUCCESS;
        }
    }
}
