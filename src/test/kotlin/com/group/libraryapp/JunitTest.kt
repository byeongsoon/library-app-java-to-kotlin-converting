package com.group.libraryapp

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JunitTest {

    // Koltin 에서는 @BeforeAll, @AfterAll은 @KvmStatic을 붙여주어야 하고, companion object 안에 작성하여야 한다.
    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            println("모든 테스트 시작 전")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            println("모든 테스트 종료 후")
        }
    }

    @BeforeEach
    fun beforeEach() {
        println("각 테스트 시작 전")
    }

    @AfterEach
    fun afterEach() {
        println("각 테스트 종료 후")
    }

    @Test
    fun test1() {
        println("테스트 1")
    }

    @Test
    fun test2()  {
        println("테스트 2")
    }

}