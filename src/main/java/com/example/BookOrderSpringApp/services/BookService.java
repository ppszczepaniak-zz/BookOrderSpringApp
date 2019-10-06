package com.example.BookOrderSpringApp.services;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.storage.implementations.BookStorageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookStorageImpl bookStorage;

    public BookService(BookStorageImpl bookStorage) {
        this.bookStorage = bookStorage;
    }


    public List<Book> getAll() {
        return bookStorage.getAllBooks();
    }

    public Book add(Book book) {
        return bookStorage.addBook(book);
    }
}
