package ir.saeidbabaei.bookstore.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import javax.validation.constraints.NotEmpty
import io.swagger.annotations.ApiModelProperty


/**
 * Represents the database entity for storing the books details.
 * 
 */
@Entity
@Table(name = "book")
class Book(
	
        @Id
		@GeneratedValue( generator = "uuid2" )
		@GenericGenerator( name = "uuid2", strategy = "uuid2" )	
		@Column(name = "bookId", columnDefinition = "BINARY(16)")
		@ApiModelProperty(readOnly = true)
        val bookId: UUID?,
		
        @Column(name = "title", nullable = false)
		@field:Size(min = 3, max = 100, message = "The length of title must be between 3 and 100 characters.")
		@field:NotEmpty(message = "title must not be null or empty")				
        val title: String?,
		
        @Column(name = "author", nullable = true)
        val author: String?,
		
        @Column(name = "price", nullable = false)
        @field:NotNull(message = "price must not be null or empty")
        val price: BigDecimal?,
		
        @Column(name = "active", nullable = false)
        @field:NotNull(message = "active must not be null or empty")		
        val active: Boolean?
		
)