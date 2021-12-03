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
import java.sql.SQLIntegrityConstraintViolationException
import ir.saeidbabaei.bookstore.exception.DuplicateBookException
import org.springframework.dao.DataIntegrityViolationException

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
	 * @throw BookNotFoundException	 
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
	 * @throw DuplicateBookException
	 * @throw BookNotFoundException	 
     */
    fun createBook(book: Book): Book {
		
		Logger.info("Saving book: {}", book.title);
		
		try
		{
			val newBook = bookRepository.save(book)
			
			Logger.info("Book saved - bookId: {}", newBook.bookId)
			
			return newBook
			
		}
		catch (e: DataIntegrityViolationException) {
			
			Logger.info("Book did't save - Duplicate book", book.title)
			
			throw DuplicateBookException("CONFLICT", "Duplicate book")		
		}
		
	}

    /**
     * Update book.
     *
     * @param bookId the book id
     * @param book the book details
     * @return the book
	 * @throw DuplicateBookException
	 * @throw BookNotFoundException
     */
    fun updateBookById(bookId: UUID, book: Book): Book {
				
        if (bookRepository.existsById(bookId)) {
			
			Logger.info("Updating book: {}", book.toString());
											
			try
			{
	            val updatedBook = bookRepository.save(
	                    Book(
	                            bookId = bookId,
	                            title = book.title,
	                            author = book.author,
	                            price = book.price,
	                            active = book.active,
	                    )
	            )
				
	            Logger.info("Book Updated: {}", updatedBook.bookId);
				
				return updatedBook
				
			}
			catch (e: DataIntegrityViolationException) {
				
				Logger.info("Book did't save - Duplicate book", book.title)
				
				throw DuplicateBookException("CONFLICT", "Duplicate book")		
			}		
			
        }
		else
			{
				Logger.info("No matching book id: {}", bookId);
				
				throw BookNotFoundException("NOT_FOUND", "No matching book was found")
			}

    }
		
}