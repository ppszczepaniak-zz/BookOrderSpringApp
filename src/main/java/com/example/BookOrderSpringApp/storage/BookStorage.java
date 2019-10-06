package com.example.BookOrderSpringApp.storage;

import com.example.BookOrderSpringApp.models.*;
import java.util.List;

public interface BookStorage {
    Book getBook(long bookId);

    List<Book> getAllBooks();

    long addBook(Book book); //shoud return bookId

    void clearTableBooks();

    //TODO add Delete book feature
}
