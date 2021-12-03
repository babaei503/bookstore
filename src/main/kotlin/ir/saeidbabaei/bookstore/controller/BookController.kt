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
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.util.ObjectUtils
import org.springframework.http.HttpHeaders
import org.slf4j.LoggerFactory
import ir.saeidbabaei.bookstore.exception.BookNotFoundException
import ir.saeidbabaei.bookstore.exception.DuplicateBookException
import javax.validation.Valid
import org.springframework.validation.annotation.Validated

/**
 * Controller for REST API endpoints
 */
@RequestMapping("/api")
@RestController
class BookController(private val bookService: BookService) {

	private val Logger = LoggerFactory.getLogger(javaClass)
		
	@GetMapping("/books")
    fun getAllActiveBooks(): ResponseEntity<List<Book>> {	
		
		val activeBooks = bookService.getAllActiveBooks()
		
	    return ResponseEntity<List<Book>>(activeBooks, HttpStatus.OK)
		
	}

    @GetMapping("/books/{uuid}")
    fun getBookById(@PathVariable("uuid") bookId: UUID): ResponseEntity<Book> {
		
    	return ResponseEntity<Book>(bookService.getBookById(bookId), HttpStatus.OK)			
		
	}	

    @PostMapping("/book")
	fun createBook(@Valid @RequestBody payload: Book, uri: UriComponentsBuilder): ResponseEntity<Book> {
	
		val newBook = bookService.createBook(payload)
			
	    val headers = HttpHeaders()
	    headers.setLocation(uri.path("/api/books/{uuid}").buildAndExpand(newBook.bookId).toUri());
		
	    return ResponseEntity<Book>(newBook,  headers, HttpStatus.CREATED)			
	}	

    @PutMapping("/book/{uuid}")
	fun updateBookById(@PathVariable("uuid") bookId: UUID, @Valid @RequestBody payload: Book): ResponseEntity<Book> {
		
	    return ResponseEntity<Book>(bookService.updateBookById(bookId, payload), HttpStatus.OK)						
		
	}	
	
}