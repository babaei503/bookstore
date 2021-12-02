package ir.saeidbabaei.bookstore.repository

import ir.saeidbabaei.bookstore.model.Book
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

@Repository
interface BookRepository : JpaRepository<Book, UUID> {
	
	 /**
     * Get active books list.
     *
     * @return the list
     */
	fun findByActiveTrue() : List<Book>
	
}