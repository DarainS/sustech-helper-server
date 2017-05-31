package com.github.darains.sustech.student.server.dto.course;

import com.github.darains.sustech.student.server.entity.CacheKey;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class CourseTable implements CacheKey{
    private String studentid;
    private Set<Course> courses;
    
    @Override
    public String cacheKey(){
        return studentid+"_course_table";
    }
}
