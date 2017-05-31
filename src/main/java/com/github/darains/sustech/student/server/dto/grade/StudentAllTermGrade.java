package com.github.darains.sustech.student.server.dto.grade;


import com.github.darains.sustech.student.server.entity.CacheKey;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Data
@Accessors(chain = true)
public class StudentAllTermGrade implements CacheKey{
    private String studentid;
    private List<TermGrade> termGradeList=new LinkedList<>();
    
    @Override
    public String cacheKey(){
        return studentid+"_all_term_grade";
    }
}
