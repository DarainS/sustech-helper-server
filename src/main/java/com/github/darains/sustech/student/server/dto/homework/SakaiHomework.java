package com.github.darains.sustech.student.server.dto.homework;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class SakaiHomework{
    String studentid;
    String cookie;
    Set<CourseHomework> homework;
}
