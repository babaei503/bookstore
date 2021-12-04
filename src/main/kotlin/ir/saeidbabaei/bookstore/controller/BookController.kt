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
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy.Content
import ir.saeidbabaei.bookstore.exception.ApiError
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Controller for REST API endpoints
 */
@RequestMapping("/api")
@RestController
class BookController(private val bookService: BookService) {

	private val Logger = LoggerFactory.getLogger(javaClass)

	@ApiOperation(value = "View a list of active books")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "OK", response = Book::class),
            ApiResponse(code = 204, message = "There is no active book", response = Void::class)
        )
    )			
	@GetMapping("/books")
    fun getAllActiveBooks(): ResponseEntity<List<Book>> {	
		
		val activeBooks = bookService.getAllActiveBooks()
		
	    return ResponseEntity<List<Book>>(activeBooks, HttpStatus.OK)
		
	}

	@ApiOperation(value = "Search a book with an ID")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "OK", response = Book::class),
            ApiResponse(code = 404, message = "No matching book was found with bookId", response = ApiError::class)
        )
    )	
    @GetMapping("/books/{uuid}")
    fun getBookById(@PathVariable("uuid") bookId: UUID): ResponseEntity<Book> {
		
    	return ResponseEntity<Book>(bookService.getBookById(bookId), HttpStatus.OK)			
		
	}	

	@ApiOperation(value = "Add a book")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 201, message = "Created", response = Book::class),
            ApiResponse(code = 409, message = "Book did't save - Duplicate book", response = ApiError::class),
            ApiResponse(code = 400, message = "Validation Error", response = ApiError::class),
			ApiResponse(code = 400, message = "Request Error", response = ApiError::class),		
        )
    )		
    @PostMapping("/book")
	@ResponseStatus( HttpStatus.CREATED )
	fun createBook(@Valid @RequestBody book: Book, uri: UriComponentsBuilder): ResponseEntity<Book> {
	
		val newBook = bookService.createBook(book)
			
	    val headers = HttpHeaders()
	    headers.setLocation(uri.path("/api/books/{uuid}").buildAndExpand(newBook.bookId).toUri());
		
	    return ResponseEntity<Book>(newBook,  headers, HttpStatus.CREATED)			
	}	

	@ApiOperation(value = "Update a book")
    @ApiResponses(
        value = arrayOf(
            ApiResponse(code = 200, message = "OK", response = Book::class),
            ApiResponse(code = 404, message = "No matching book was found with bookId", response = ApiError::class),			
            ApiResponse(code = 409, message = "Book did't save - Duplicate book", response = ApiError::class),
            ApiResponse(code = 400, message = "Validation Error", response = ApiError::class),
			ApiResponse(code = 400, message = "Request Error", response = ApiError::class),		
        )
    )
    @PutMapping("/book/{uuid}")
	fun updateBookById(@PathVariable("uuid") bookId: UUID, @Valid @RequestBody book: Book): ResponseEntity<Book> {
		
	    return ResponseEntity<Book>(bookService.updateBookById(bookId, book), HttpStatus.OK)						
		
	}	
	
}