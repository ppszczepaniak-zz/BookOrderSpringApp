package com.example.BookOrderSpringApp.controller;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.services.BookService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll") //http://localhost:8080/book/getAll
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @GetMapping("/get") ////http://localhost:8080/book/get
    public Book getBook(@Param("bookId") long bookId) {
        return bookService.get(bookId);
    }

    @PostMapping("/add") //http://localhost:8080/book/add
    @ResponseStatus(HttpStatus.CREATED) //201
    public Book addBook(@RequestBody Book book) {
        return bookService.add(book);
    }

}
