package com.github.darains.sustech.student.server.dto.grade;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TermGrade implements Serializable{
    private String courseTerm;
    private List<Grade> gradeList=new LinkedList<>();
}
