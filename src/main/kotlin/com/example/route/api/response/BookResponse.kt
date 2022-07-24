package com.example.route.api.response

import com.example.route.model.Book

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String
) {
    companion object {
        fun from(book: Book) = BookResponse(book.id!!, book.title, book.author)
    }
}