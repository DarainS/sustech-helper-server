package com.github.darains.sustech.student.server.dto.homework;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class CourseHomework implements Serializable{
    private static final long serialVersionUID = -7333553936330905968L;
    String courseName;
    List<Homework> courseHomework;
}
