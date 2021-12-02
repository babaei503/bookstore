# Restful API application with Kotlin and Spring boot

This project is about creating a Restful API application with Kotlin and Spring boot with the following API:

```
To create a book => Post: book
To get a book => Get : books/{UUID}
To update a book => PUT : book/{UUID}
```

###### Note:
- If no UUID is defined, returns all active books 
- Inputs and outputs are in JSON format
- Json Object looks like this:

```
{
	"bookId": UUID,
	"title": STRING,
	"author": STRING,
	"price": BigDecimal,
	"active": Boolian
}
```

The book object will be saved in / retrieved from the MySQL bookdb.
 
To create the table, the Flyway migration tool is used.

The project contains tests running on the H2 database.

 