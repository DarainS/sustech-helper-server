package com.github.darains.sustech.student.server.api

import com.github.darains.sustech.student.server.dto.HttpResult
import com.github.darains.sustech.student.server.service.EducationalSystemService
import com.github.darains.sustech.student.server.service.SakaiService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotNull

@RestController("/api")
class SakaiApi {

    @Autowired
    lateinit var sakaiService: SakaiService

    @Autowired
    lateinit var eduService:EducationalSystemService

    @PostMapping("/sakai/homework")
    fun studentAllHomeworks(@RequestParam @NotNull  username:String, @RequestParam @NotNull password:String ):HttpResult{
        var s=sakaiService.getCachedSakaiHomework(username,password)
        var result=HttpResult.success(s)
        return result
    }

    @PostMapping("/user/grade")
    fun allGrades(@RequestParam @NotNull userid:String,@RequestParam @NotNull password: String): HttpResult {
        var grade=eduService.getCachedStudentAllTermGrade(userid, password)
        return HttpResult.success(grade)
    }

    @PostMapping("/user/courseTable")
    fun courseTable(@RequestParam @NotNull userid:String,@RequestParam @NotNull password: String): HttpResult {
        var table=eduService.getCachedStudentCourseTable(userid, password)
        return HttpResult.success(table)
    }

}