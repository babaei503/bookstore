package ir.saeidbabaei.bookstore.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import java.math.BigDecimal

/**
 * Represents the database entity for storing the books details.
 * 
 */
@Entity
@Table(name = "book")
class Book(
	
        @Id
		@GeneratedValue(generator = "UUID")
        @GenericGenerator(
	        name = "UUID",
	        strategy = "org.hibernate.id.UUIDGenerator",
        )
		@Column(name = "bookId", updatable = false, nullable = false)
        val bookId: UUID,
		
        @Column(name = "title", nullable = false)
        val title: String,
		
        @Column(name = "author", nullable = true)
        val author: String,
		
        @Column(name = "price", nullable = false)
        val price: BigDecimal,
		
        @Column(name = "active", nullable = false)
        val active: Boolean
		
)