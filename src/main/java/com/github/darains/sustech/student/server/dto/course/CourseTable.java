package com.github.darains.sustech.student.server.dto.course;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class CourseTable{
    private String studentid;
    private Set<Course> courses;
}
