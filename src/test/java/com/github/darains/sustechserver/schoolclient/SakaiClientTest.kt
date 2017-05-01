package com.github.darains.sustechserver.schoolclient

import com.github.darains.sustechserver.dto.homework.CourseHomework
import com.github.darains.sustechserver.dto.homework.Homework
import javaslang.Tuple2
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
    internal var client: SakaiClient? = null

    @Before
    fun before() {
        client!!.casLogin("11310388", "dengakak")
    }

    @Test
    fun resolveHomeworkTest() {
        val set = client!!.resolveSiteUrls()
        println(set)
        for (s1 in set) {
            val ls = client!!.resolveHomeworks(s1._2() as String)
            val c = CourseHomework()
            c.courseHomework = ls
            c.courseName = s1._1() as String
            println(c)
        }
    }


}
