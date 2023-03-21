package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest

import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val sut: BookService,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
){

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    fun saveBookTest() {
        val request = BookRequest("미움받을 용기")

        sut.saveBook(request)

        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("미움받을 용기")
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다.")
    fun loanBookTest() {
        val savedUser = userRepository.save(User("장병순", null))
        bookRepository.save(Book("미움받을 용기"))
        val request = BookLoanRequest("장병순","미움받을 용기")

        sut.loanBook(request)

        val loanBook = userLoanHistoryRepository.findAll()
        assertThat(loanBook).hasSize(1)
        assertThat(loanBook[0].bookName).isEqualTo("미움받을 용기")
        assertThat(loanBook[0].user.id).isEqualTo(savedUser.id)
        assertThat(loanBook[0].isReturn).isFalse
    }

    @Test
    @DisplayName("책이 진작 대출되어 있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest() {
        val savedUser = userRepository.save(User("장병순", null))
        bookRepository.save(Book("미움받을 용기"))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "미움받을 용기", false))
        val request = BookLoanRequest("장병순","미움받을 용기")

        // Act & Assert
        val message = assertThrows<IllegalArgumentException> {
            sut.loanBook(request)
        }.apply {
            assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
        }
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다.")
    fun returnBookTest() {
        val savedUser = userRepository.save(User("장병순", null))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "미움받을 용기", false))
        val request = BookReturnRequest("장병순","미움받을 용기")

        sut.returnBook(request)

        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isTrue
    }

}