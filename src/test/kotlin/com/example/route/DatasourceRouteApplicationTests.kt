package com.example.route

import com.example.route.api.request.BookCreateRequest
import com.example.route.api.response.BookResponse
import com.example.route.service.BookService
import org.junit.ClassRule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.Testcontainers
import org.testcontainers.containers.DockerComposeContainer

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(DBExtension::class)
class DatasourceRouteApplicationTests @Autowired constructor(
    private val service: BookService
){

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Test
    fun `Create and get book`() {
        for (i in 0 until 10) {
            val id = this.createBook("title", "author")
            val response = restTemplate.getForEntity("/api/books/$id", BookResponse::class.java)

            if (response.statusCode != HttpStatus.OK) {
                println("Book not found")
            } else {
                println("Book found")
                assertEquals(id, response.body!!.id)
            }
        }
    }

    private fun createBook(title: String, author: String): Long {
        val request = BookCreateRequest(title, author)
        val response = restTemplate.postForEntity("/api/books", request, String::class.java)
        return response.body!!.toLong()
    }

    @Test
    fun `Create and read book`() {
        val id = this.service.createBook("title-1", "author-1")

        logger.info("Created book $id")

        val book = this.service.findBookById(id)
        if (book == null) {
            println("Book not found $id")
        } else {
            println("Book found $id")
        }
    }
}
