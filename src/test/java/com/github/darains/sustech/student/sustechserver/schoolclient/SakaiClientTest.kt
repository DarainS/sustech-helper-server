package com.github.darains.sustech.student.sustechserver.schoolclient

import com.github.darains.sustech.student.server.config.TestConfig
import com.github.darains.sustech.student.server.dto.homework.CourseHomework
import com.github.darains.sustech.student.server.schoolclient.SakaiClient
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class SakaiClientTest {

    @Autowired
    lateinit var client: SakaiClient

    @Autowired
    lateinit var config: TestConfig

    @Before
    fun before() {
        client.casLogin(config.username,config.password)
    }

    @Test
    fun resolveHomeworkTest() {
        val set = client.resolveSiteUrls()
        println(set)
        for (s1 in set) {
            val ls = client.resolveHomeworks(s1._2() as String)
            val c = CourseHomework()
            c.courseHomework = ls
            c.courseName = s1._1() as String
            println(c)
        }
    }

}
