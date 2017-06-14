package com.github.darains.sustech.student.server.schoolclient

import com.github.darains.sustech.student.server.casclient.SakaiClient
import com.github.darains.sustech.student.server.config.SpringTestConfig
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


/**
 * SakaiClient的测试类
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@Slf4j
class SakaiClientTest {

    @Autowired
    lateinit var client: SakaiClient

    @Autowired
    lateinit var service: SakaiService

    @Autowired
    lateinit var config: SpringTestConfig

    @Before
    fun before() {
        client.casLogin(config.username, config.password)
        Assert.assertTrue(client.cookie.isNotEmpty())
    }

    @Test
    fun resolveHomeworkTest() {
        var i=0
        while (i<20){
            var t1=System.currentTimeMillis()

            if (i%2==1) {
                var h = service.getCachedSakaiHomework(config.username,config.password)
            }
            else{
                var h = service.getRefreshedSakaiHomework(config.username,config.password)
            }

            var t2 = System.currentTimeMillis()

            log.println("No.$i cost ${t2-t1} time")

            i++
        }


    }

}
