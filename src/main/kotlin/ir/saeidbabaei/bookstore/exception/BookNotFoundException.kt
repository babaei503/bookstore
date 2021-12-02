package ir.saeidbabaei.bookstore.exception

class BookNotFoundException(val title: String, val reason: String) : Exception()