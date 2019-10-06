package storage;

import models.Book;

import java.util.List;

public interface BookStorage {
    Book getBook(long bookId);

    List<Book> getAllBooks();

    long addBook(Book book); //shoud return bookId

    void clearTableBooks();

    //TODO add Delete book feature
}
