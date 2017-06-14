package com.github.darains.sustech.student.server.dto.course;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable{
    /**
     * 周几上课
     */
    int weekly;
    
    /**
     * 第几节课
     */
    int section;
    
    String courseName;
    String teachers;
    String classRoom;
}
