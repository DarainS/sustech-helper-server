package com.github.darains.sustech.student.server.api

import com.github.darains.sustech.student.server.dto.homework.SakaiHomework
import com.github.darains.sustech.student.server.schoolclient.SakaiClient
import com.github.darains.sustech.student.server.service.SakaiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.validation.constraints.NotNull

public class SakaiApi {

    @Autowired
    lateinit var sakaiClient: SakaiClient

    @PostMapping
    fun studentAllHomeworks(@RequestParam @NotNull  username:String, @RequestParam @NotNull password:String ):SakaiHomework{
        sakaiClient.casLogin(username,password)
        sakaiClient.

    }

}