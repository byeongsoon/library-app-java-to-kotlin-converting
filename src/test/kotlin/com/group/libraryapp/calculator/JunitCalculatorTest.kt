package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JunitCalculatorTest {

    @Test
    fun addTest() {
        val sut = Calculator(5)

        sut.add(3)

        assertThat(sut.number).isEqualTo(8)
    }

    @Test
    fun minusTest() {
        val sut = Calculator(5)

        sut.minus(3)

        assertThat(sut.number).isEqualTo(2)
    }

    @Test
    fun multiplyTest() {
        val sut = Calculator(5)

        sut.multiply(3)

        assertThat(sut.number).isEqualTo(15)
    }

    @Test
    fun divideTest() {
        val sut = Calculator(5)

        sut.divide(2)

        assertThat(sut.number).isEqualTo(2)
    }

    @Test
    fun divideExceptionTest() {
        val sut = Calculator(5)

        // Act % Assert
        // scope function 으로 사용가능!
        val message = assertThrows<IllegalArgumentException> {
            sut.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
        }

//        val message = assertThrows<IllegalArgumentException> {
//            sut.divide(0)
//        }.message
//        assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
    }

}