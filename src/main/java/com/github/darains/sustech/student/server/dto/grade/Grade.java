package com.github.darains.sustech.student.server.dto.grade;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class Grade implements Serializable{
    private String courseid;
    private String courseName;
    private String grade;
    private double credit;
    private String courseAttribute;
}
