package com.github.darains.sustech.student.server.dto.course;

import lombok.Data;

import java.io.Serializable;

@Data
public class Course implements Serializable{
    int weekly;
    int section;
    String courseName;
    String teachers;
    String classRoom;
}
