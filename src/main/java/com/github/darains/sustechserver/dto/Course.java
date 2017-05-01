package com.github.darains.sustechserver.entity.dto;

import lombok.Data;

@Data
public class Course{
    int weekly;
    int section;
    String courseName;
    String teachers;
    String classRoom;
}
