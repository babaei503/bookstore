package ir.saeidbabaei.bookstore.service


import ir.saeidbabaei.bookstore.exception.BookNotFoundException
import ir.saeidbabaei.bookstore.repository.BookRepository
import ir.saeidbabaei.bookstore.model.Book
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriComponentsBuilder
import org.slf4j.LoggerFactory
import java.util.Optional

/**
 * @author Saeid Babaei
 * Service for interactions with book domain object
 */
@Service
class BookService(private val bookRepository: BookRepository) {
	
	private val Logger = LoggerFactory.getLogger(javaClass)
	
    /**
     * Get active books list.
     *
     * @return the list
     */
    fun getAllActiveBooks(): List<Book> {

		Logger.info("Getting active books");
			
		val books = bookRepository.findByActiveTrue()
		
		Logger.info("Active books: {}", books.size);	
		
	    return books
	} 


    /**
     * Gets books by id.
     *
     * @param bookId the book id
     * @return the book
     */
    fun getBookById(bookId: UUID): Book {

		Logger.info("Getting book with id: {}" , bookId);
				
		return  bookRepository.findById(bookId)
				.orElseThrow { BookNotFoundException("NOT_FOUND", "No matching book was found") }			
			
	} 

    /**
     * Create book.
     *
     * @param book the book details
     * @return the book
     */
    fun createBook(book: Book): Book {
		
		Logger.info("Saving book: {}", book.title);
		
		return bookRepository.save(book)
		
	}

    /**
     * Update book.
     *
     * @param bookId the book id
     * @param book the book details
     * @return the book
     */
    fun updateBookById(bookId: UUID, book: Book): Book {
				
        if (bookRepository.existsById(bookId)) {
			
			Logger.info("Updating book: {}", book.toString());
			
            val updatedBook = bookRepository.save(
                    Book(
                            bookId = bookId,
                            title = book.title,
                            author = book.author,
                            price = book.price,
                            active = book.active,
                    )
            )
			
			Logger.info("Updated book: {}", updatedBook.bookId);
			
			return updatedBook
			
        }
		else
			{
				Logger.info("No matching book id: {}", bookId);
				
				throw BookNotFoundException("NOT_FOUND", "No matching book was found")
			}

    }
		
}