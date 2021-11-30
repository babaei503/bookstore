package ir.saeidbabaei.bookstore.exception

import org.springframework.http.HttpStatus

class BookNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()