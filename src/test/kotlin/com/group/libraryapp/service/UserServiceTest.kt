package com.group.libraryapp.service

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.service.user.UserService

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val sut: UserService,
) {

    @Test
    fun saveUserTest() {
        val request = UserCreateRequest("장병순", null)

        sut.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("장병순")
        assertThat((results[0].age)).isNull()
    }


}