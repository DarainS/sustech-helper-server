package com.github.darains.sustechserver.dto.homework;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class SakaiHomework{
    String cookie;
    Set<CourseHomework> homework;
}
