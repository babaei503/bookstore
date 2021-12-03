package ir.saeidbabaei.bookstore.exception

class NoActiveBookException(val title: String, val reason: String) : Exception()