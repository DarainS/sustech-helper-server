package com.github.darains.sustech.student.server.schoolclient

import com.github.darains.sustech.student.server.config.SpringTestConfig
import com.github.darains.sustech.student.server.casclient.SakaiClient
import com.github.darains.sustech.student.server.service.SakaiService
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@Slf4j
class SakaiClientTest {

    @Autowired
    lateinit var client: SakaiClient

    @Autowired
    lateinit var service: SakaiService

    @Autowired
    lateinit var configSpring: SpringTestConfig

    @Before
    fun before() {
        client.casLogin(configSpring.username, configSpring.password)
    }

    @Test
    fun resolveHomeworkTest() {
        var i=0
        while (i<20){
            var t1=System.currentTimeMillis()

            if (i%2==1) {
                var h = service.getCachedSakaiHomework("11310388", "dengakak")
            }
            else{
                var h = service.getRefreshedSakaiHomework("11310388", "dengakak")
            }

            var t2 = System.currentTimeMillis()

            log.println("No.$i cost ${t2-t1} time")

            i++
        }


    }

}
