package com.github.darains.sustech.student.server.dto.homework;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Homework implements Serializable{
    private  String homeworkName;
    private  String stat;
    private  LocalDateTime beginDate;
    private LocalDateTime endDate;

    private String url;
    
    public boolean isOvertime(){
        if (endDate==null){
            return true;
        }
        return endDate.isBefore(LocalDateTime.now());
    }
    
    public boolean notOvertime(){
        return !isOvertime();
    }
    
//    @Override
//    public String toString(){
//        if (this.date==null)
//            return this.homeworkName+"  "+ this.stat;
//        else return  String.format("%-26s%-23s%-16s",homeworkName,stat,date);
//    }
    
}