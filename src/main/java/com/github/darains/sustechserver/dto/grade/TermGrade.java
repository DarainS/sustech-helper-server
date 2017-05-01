package com.github.darains.sustechserver.entity.dto.grade;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class TermGrade{
    private String courseTerm;
    private List<Grade> gradeList=new LinkedList<>();
}
