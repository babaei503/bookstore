package ir.saeidbabaei.bookstore.exception

class DuplicateBookException(val title: String, val reason: String) : Exception()