/* Before running this script you must 
write an SQL command here to correct 
the duplicate data records  
this is just for Flyway test*/

CREATE UNIQUE INDEX Index_Book_Unique
ON book(title, author, price, active);