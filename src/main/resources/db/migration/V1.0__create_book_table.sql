    Create Table IF NOT EXISTS book (
        book_id binary(16) not null,
        active bit not null,
        author varchar(255),
        price decimal(19,2) not null,
        title varchar(255) not null,
        primary key (book_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=UTF8;