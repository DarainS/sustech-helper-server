package com.github.darains.sustechserver.dto;

import lombok.Data;

@Data
public class Course{
    int weekly;
    int section;
    String courseName;
    String teachers;
    String classRoom;
}
