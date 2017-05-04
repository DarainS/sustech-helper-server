package com.github.darains.sustech.student.server.schoolclient

import com.github.darains.sustech.student.server.config.SpringTestConfig
import com.github.darains.sustech.student.server.casclient.SakaiClient
import org.junit.Assert
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
    lateinit var configSpring: SpringTestConfig

    @Before
    fun before() {
        client.casLogin(configSpring.username, configSpring.password)
    }

    @Test
    fun resolveHomeworkTest() {
        Assert.assertEquals("1","1")
    }

}
