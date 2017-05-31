package com.github.darains.sustech.student.server.dto.homework;

import com.github.darains.sustech.student.server.entity.CacheKey;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class SakaiHomework implements CacheKey{
    String studentid;
    String cookie;
    Set<CourseHomework> courseHomeworks;
    
    @Override
    public String cacheKey(){
        assert studentid!=null;
        return "sakai_homework_"+studentid;
    }
}
