package ir.saeidbabaei.bookstore.exception

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.http.ResponseEntity
import java.util.Date
import org.springframework.http.HttpStatus
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.http.converter.HttpMessageNotReadableException

@ControllerAdvice
class RestResponseEntityExceptionHandler {

	private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [(NoActiveBookException::class)])
    fun handleNoActiveBookException(ex: NoActiveBookException, request: WebRequest): ResponseEntity<ApiError> {
        
		val errors = mutableListOf<String>()		
		errors.add(ex.reason)
		
		val apiError = ApiError(Date(),
                ex.title,
                errors,
                request.getDescription(false)
        )
		
		logger.info(apiError.toString())
		
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
					
    @ExceptionHandler(value = [(BookNotFoundException::class)])
    fun handleBookNotFoundException(ex: BookNotFoundException, request: WebRequest): ResponseEntity<ApiError> {
        
		val errors = mutableListOf<String>()		
		errors.add(ex.reason)
		
		val apiError = ApiError(Date(),
                ex.title,
                errors,
                request.getDescription(false)
        )
		
		logger.info(apiError.toString())
		
        return ResponseEntity(apiError, HttpStatus.NOT_FOUND)
    }
	
    @ExceptionHandler(value = [(DuplicateBookException::class)])
    fun handleDuplicateBookException(ex: DuplicateBookException, request: WebRequest): ResponseEntity<ApiError> {

		val errors = mutableListOf<String>()		
		errors.add(ex.reason)
		        
		val apiError = ApiError(Date(),
                ex.title,
                errors,
                request.getDescription(false)
        )
		
		logger.info(apiError.toString())
		
        return ResponseEntity(apiError, HttpStatus.CONFLICT)
    }

	@ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ApiError> {

        val errors = mutableListOf<String>()
        ex.bindingResult.fieldErrors.forEach { errors.add("${it.field}: ${it.defaultMessage}") }
        ex.bindingResult.globalErrors.forEach { errors.add("${it.objectName}: ${it.defaultMessage}") }
		
		val apiError = ApiError(Date(),
				                "Validation Error",
				                errors,
				                request.getDescription(false))
		
		logger.info(apiError.toString())
		
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

	@ExceptionHandler(value = [(HttpMessageNotReadableException::class)])
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException, request: WebRequest): ResponseEntity<ApiError> {

        val errors = mutableListOf<String>()
		errors.add("Error in request")
		
		val apiError = ApiError(Date(),
				                "Request Error",
				                errors,
				                request.getDescription(false))
		
		logger.info(apiError.toString() + ex.message)
		
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }		
	
		
}