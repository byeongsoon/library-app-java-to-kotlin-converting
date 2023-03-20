package com.group.libraryapp.service

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.service.user.UserService

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val sut: UserService,
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("사용자 저장이 정상 동작한다.")
    fun saveUserTest() {
        val request = UserCreateRequest("장병순", null)

        sut.saveUser(request)

        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("장병순")
        assertThat((results[0].age)).isNull()
    }

    @Test
    @DisplayName("사용자 조회가 정상 동작한다.")
    fun getUsersTest() {
        userRepository.saveAll(listOf(
            User("A", 20),
            User("B",null)
        ))

        val results = sut.getUsers()

        assertThat(results).hasSize(2) // [UserResponse(), UserResponse()]
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B") // ["A", "B"]
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @Test
    @DisplayName("사용자 수정이 정상 동작한다.")
    fun updateUserNameTest() {
        val savedUser = userRepository.save(User("장병순", null))
        val request = UserUpdateRequest(savedUser.id, "B")

        sut.updateUserName(request)

        val result = userRepository.findAll()
        assertThat(request.name).isEqualTo("B")
    }

    @Test
    @DisplayName("사용자 삭제가 정상 동작한다.")
    fun deleteUserTest() {
        val savedUser = userRepository.save(User("장병순", 30))

        sut.deleteUser("장병순")

        assertThat(userRepository.findAll()).isEmpty()
    }

}