package com.github.darains.sustechserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Result{
    private int statusCode;
    private HttpStatus httpStatus;
    private Object result;
    private String description;
    public void setHttpStatus(HttpStatus status){
        this.httpStatus=status;
        this.statusCode=status.value();
    }
}
