package com.github.darains.sustechserver.dto.grade;


import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class StudentAllTermGrade{
    private String studentid;
    private List<TermGrade> termGradeList=new LinkedList<>();
}
