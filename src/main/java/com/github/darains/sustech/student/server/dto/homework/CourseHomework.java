package com.github.darains.sustech.student.server.dto.homework;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CourseHomework{
    String courseName;
    List<Homework> courseHomework;
}
