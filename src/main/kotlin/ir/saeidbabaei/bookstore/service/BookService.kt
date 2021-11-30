package ir.saeidbabaei.bookstore.service


import ir.saeidbabaei.bookstore.exception.BookNotFoundException
import ir.saeidbabaei.bookstore.repository.BookRepository
import ir.saeidbabaei.bookstore.model.Book
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * @author Saeid Babaei
 * Service for interactions with book domain object
 */
@Service
class BookService(private val bookRepository: BookRepository) {
	
    /**
     * Get all books list.
     *
     * @return the list
     */
    fun getAllBooks(): List<Book> = bookRepository.findAll()

    /**
     * Gets books by id.
     *
     * @param bookId the book id
     * @return the book by id
     * @throws BookNotFoundException the book not found exception
     */
    fun getBooksById(bookId: UUID): Book = bookRepository.findById(bookId)
            .orElseThrow { BookNotFoundException(HttpStatus.NOT_FOUND, "No matching book was found") }

    /**
     * Create book.
     *
     * @param book the book
     * @return the book
     */
    fun createBook(book: Book): Book = bookRepository.save(book)

    /**
     * Update book.
     *
     * @param bookId the book id
     * @param book the book details
     * @return the book
     * @throws BookNotFoundException the book not found exception
     */
    fun updateBookById(bookId: UUID, book: Book): Book {
        return if (bookRepository.existsById(bookId)) {
            bookRepository.save(
                    Book(
                            bookId = book.bookId,
                            title = book.title,
                            author = book.author,
                            price = book.price,
                            active = book.active,
                    )
            )
        } else throw BookNotFoundException(HttpStatus.NOT_FOUND, "No matching book was found")
    }

    /**
     * Delete book.
     *
     * @param bookId the book id
     * @return the map
     * @throws BookNotFoundException the book not found exception
     */
    fun deleteBooksById(bookId: UUID) {
        return if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId)
        } else throw BookNotFoundException(HttpStatus.NOT_FOUND, "No matching book was found")
    }
		
}