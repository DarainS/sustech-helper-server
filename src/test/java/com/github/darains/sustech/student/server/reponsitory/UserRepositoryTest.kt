package com.github.darains.sustech.student.server.reponsitory

import com.github.darains.sustech.student.server.repository.UserRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository:UserRepository

    @Test
    fun test(){
        var user= userRepository.getUserByUserid("11310388")
        Assert.assertEquals(user.password,"dengakak")
    }

}