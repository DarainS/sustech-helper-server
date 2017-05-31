package com.github.darains.sustech.student.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiaoyue Xiao
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements Serializable,CacheKey {

    private static final long serialVersionUID = 7698862379923111158L;

    private Long id;
    private String userid;
    private String password;
    
    @Override
    public String cacheKey(){
        return userid;
    }
}
