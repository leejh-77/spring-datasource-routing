package com.example.route.service

import com.example.route.model.Book
import com.example.route.model.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createBook(title: String, author: String): Long {
        val book = Book(title, author)
        val ret = this.bookRepository.save(book)
        return ret.id!!
    }

    @Transactional(readOnly = true)
    fun getBooks(): List<Book> {
        return this.bookRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findBookById(id: Long): Book? {
        return this.bookRepository.findById(id)
            .orElse(null)
    }
}