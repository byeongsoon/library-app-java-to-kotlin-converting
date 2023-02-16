package com.group.libraryapp.calculator

fun main() {
  val calculatorTest = CalculatorTest()

  calculatorTest.addTest()
  calculatorTest.minusTest()
  calculatorTest.multiplyTest()
  calculatorTest.divideTest()
  calculatorTest.divideExceptionTest()
}

class CalculatorTest {

  // 강의에서는 given - when -then 패턴 사용
  // arrange - act - assert (AAA) 패턴과 비슷
  fun addTest() {
    // given
    val calculator = Calculator(5)

    // when
    calculator.add(3)

    // 프로퍼티를 public으로 하던지 / private로 하고 백킹필드를 이용해 처리
    // then
    if (calculator.number != 8) {
      throw IllegalArgumentException()

//     Calculator 클래스를 data 클래스로 만들고
//    val expectedCalculator = Calculator(8)
//    if (calculator != expectedCalculator) {
//      throw IllegalArgumentException()
//    }
    }
  }

  fun minusTest() {
    // arrange
    val sut = Calculator(5)

    // Act
    sut.minus(3)

    // Assert
    if (sut.number != 2) {
      throw IllegalArgumentException()
    }
  }

  fun multiplyTest() {
    val sut = Calculator(5)

    sut.multiply(3)

    if (sut.number != 15) {
      throw IllegalArgumentException()
    }
  }

  fun divideTest() {
    val sut = Calculator(5)

    sut.divide(2)

    if (sut.number != 2) {
      throw IllegalArgumentException()
    }
  }

  fun divideExceptionTest() {
    val sut = Calculator(5)

    try {
      sut.divide(0)
    } catch (e: IllegalArgumentException) {
      if (e.message != "0으로 나눌 수 없습니다.") {
        throw IllegalArgumentException("메시지가 다릅니다.")
      }
      // 테스트 성공!
      return
    } catch (e: Exception) {
      throw IllegalArgumentException()
    }
    throw IllegalArgumentException("기대하는 예외가 발생하지 않았습니다.")
  }

}