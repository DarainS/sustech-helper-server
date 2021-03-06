package com.github.darains.sustech.student.server.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserInfo{
    private String userid;
    private String name;
    private String accessToken;
}
