package com.example.route.api

import com.example.route.api.request.BookCreateRequest
import com.example.route.api.response.BookResponse
import com.example.route.model.Book
import com.example.route.service.BookService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {
    @GetMapping
    fun getBookList(): ResponseEntity<List<BookResponse>>  {
        return this.bookService.getBooks().map(BookResponse::from).let {
            ResponseEntity.ok(it)
        }
    }
    @PostMapping
    fun createBook(@RequestBody request: BookCreateRequest): ResponseEntity<Any> {
        return this.bookService.createBook(request.title, request.author).let {
            ResponseEntity.ok(it)
        }
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: String): ResponseEntity<BookResponse> {
        return this.bookService.findBookById(id.toLong())?.let {
            ResponseEntity.ok(BookResponse.from(it))
        } ?: ResponseEntity.notFound().build()
    }
}