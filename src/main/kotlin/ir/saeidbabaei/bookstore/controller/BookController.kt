package ir.saeidbabaei.bookstore.controller

import ir.saeidbabaei.bookstore.model.Book
import ir.saeidbabaei.bookstore.service.BookService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import java.util.UUID
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Controller for REST API endpoints
 */
@RequestMapping("/api")
@RestController
class BookController(private val bookService: BookService) {
	
	@GetMapping("/books")
    fun getAllBooks(): List<Book> = bookService.getAllBooks()

    @GetMapping("/books/{uuid}")
    fun getBooksById(@PathVariable("uuid") bookId: UUID): Book =
            bookService.getBooksById(bookId)

    @PostMapping("/books")
    fun createBook(@RequestBody payload: Book): Book = bookService.createBook(payload)

    @PutMapping("/books/{uuid}")
    fun updateBookById(@PathVariable("uuid") bookId: UUID, @RequestBody payload: Book): Book =
            bookService.updateBookById(bookId, payload)

    @DeleteMapping("/books/{uuid}")
    fun deleteBooksById(@PathVariable("uuid") bookId: UUID): Unit =
            bookService.deleteBooksById(bookId)
	
}