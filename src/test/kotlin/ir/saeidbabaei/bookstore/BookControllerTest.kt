package ir.saeidbabaei.bookstore

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.status
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.mock.mockito.MockBean
import ir.saeidbabaei.bookstore.controller.BookController
import ir.saeidbabaei.bookstore.repository.BookRepository
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ir.saeidbabaei.bookstore.model.Book
import java.math.BigDecimal
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.UUID
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.context.ActiveProfiles

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class BookControllerTest {
	
	@Autowired
    private lateinit var mockMvc: MockMvc	

	@Autowired
	lateinit var bookRepository: BookRepository
	
    @BeforeEach
    fun setup() {
		
		bookRepository.deleteAll()
		
    }

    @Test
    fun `Sending POST to the book endpoint with a valid json creates a new book`() {
		
        val book = Book(UUID(0,0), "Test title", "Test author", BigDecimal(10.0), true)
		
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(book)))
		        .andExpect(MockMvcResultMatchers.status().isCreated())
		        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			    .andExpect(jsonPath("$.title").value(book.title))
    }
	
    @Test
    fun `Sending POST to the book endpoint with an invalid json returns BAD_REQUEST`() {
		
        var book: String = "{ \"bookId\": \""
		book = book.plus(UUID(0,0))
		book = book.plus("\", \"author\": \"Test author\", \"active\": true")
		
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/book")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper().writeValueAsString(book)))
		        .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }	

	@Test
    fun `Sending GET to the books endpoint returns all avtive books`() {

		val book = Book(UUID(0,0), "Title1", "Author1", BigDecimal(10.0), true)
		val book1 = Book(UUID(0,0), "Title2", "Author2", BigDecimal(20.0), false)			
		bookRepository.save(book)
		bookRepository.save(book1)
					
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/books")
                        .accept(MediaType.APPLICATION_JSON))
                        //.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk)
	            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(MockMvcResultMatchers.jsonPath("$").exists())				
		        .andExpect(MockMvcResultMatchers.jsonPath("$[*].bookId").isNotEmpty())
		        .andExpect(MockMvcResultMatchers.jsonPath("$[*].active").value(true))		    
    }

	@Test
    fun `Sending GET to the books endpoint returns NO_CONTENT when there is no active book`() {

		val book1 = Book(UUID(0,0), "Title2", "Author2", BigDecimal(20.0), false)			
		bookRepository.save(book1)
					
        mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/books")
                            .accept(MediaType.APPLICATION_JSON))
                            //.contentType(MediaType.APPLICATION_JSON))
	                .andExpect(MockMvcResultMatchers.status().isNoContent())			
    }
	
	@Test
    fun `Sending PUT to the book endpoint with a valid json updates the book`() {

		val book = Book(UUID(0,0), "Title1", "Author1", BigDecimal(10.0), true)	
		val savedBook = bookRepository.save(book)
			
		val updateBook = Book(UUID(0,0), "Title3", "Author3", BigDecimal(30.0), true)
					
		mockMvc.perform(MockMvcRequestBuilders
			    	      .put("/api/book/{uuid}", savedBook.bookId)
					      .content(jacksonObjectMapper().writeValueAsString(updateBook))
					      .contentType(MediaType.APPLICATION_JSON)
					      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isOk)
			      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title3"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author3"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal(30.0)))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))		
    }
	
	@Test
    fun `Sending PUT to the book endpoint with an invalid bookId returns book NOT_FOUND`() {

		val book = Book(UUID(0,0), "Title1", "Author1", BigDecimal(10.0), true)	
			
		val updateBook = Book(UUID(0,0), "Title3", "Author3", BigDecimal(30.0), true)
					
		mockMvc.perform(MockMvcRequestBuilders
			    	      .put("/api/book/{uuid}", book.bookId)
					      .content(jacksonObjectMapper().writeValueAsString(updateBook))
					      .contentType(MediaType.APPLICATION_JSON)
					      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isNotFound())		
    }	
	
	@Test
    fun `Sending GET to the books endpoint with a valid bookId returns the book`() {

		val book = Book(UUID(0,0), "Title1", "Author1", BigDecimal(10.0), true)	
		val savedBook = bookRepository.save(book)
					
		mockMvc.perform(MockMvcRequestBuilders
			    	      .get("/api/books/{uuid}", savedBook.bookId)
					      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isOk)
			      .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title1"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author1"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("10.0"))
			      .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))		
    }

	@Test
    fun `Sending GET to the books endpoint with an invalid bookId returns book NOT_FOUND`() {

		val book = Book(UUID(0,0), "Title1", "Author1", BigDecimal(10.0), true)	
					
		mockMvc.perform(MockMvcRequestBuilders
			    	      .get("/api/books/{uuid}", book.bookId)
					      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(MockMvcResultMatchers.status().isNotFound())	
    }	
		
}
	
	

