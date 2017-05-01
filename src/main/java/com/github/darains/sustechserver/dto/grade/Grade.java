package com.github.darains.sustechserver.dto.grade;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class Grade{
    private String courseid;
    private String courseName;
    private String grade;
    private double credit;
    private String courseAttribute;
}
