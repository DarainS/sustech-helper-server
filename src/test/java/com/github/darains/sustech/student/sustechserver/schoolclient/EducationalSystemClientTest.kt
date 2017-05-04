package com.github.darains.sustech.student.sustechserver.schoolclient

import com.github.darains.sustech.student.server.config.TestConfig
import com.github.darains.sustech.student.server.schoolclient.EducationalSystemClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EducationalSystemClientTest {

    @Autowired
    lateinit var client: EducationalSystemClient

    @Autowired
    lateinit var config: TestConfig

    @Before
    fun before() {
        client.casLogin(config.username,config.password)
    }

    @Test
    fun studentAllTermGradeTest(){
        var grade=client.crawlStudentAllTermGrades()
        grade.termGradeList.forEach {
            println(it.courseTerm)
            it.gradeList.forEach {
                println(it.toString())
            }
            println()
        }
    }

}

