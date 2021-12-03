package ir.saeidbabaei.bookstore.exception

import java.util.Date

data class ApiError(val time: Date, val message: String, val details: List<String>, val description: String)