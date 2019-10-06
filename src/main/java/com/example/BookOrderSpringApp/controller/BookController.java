package com.example.BookOrderSpringApp.controller;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.services.BookService;
import com.example.BookOrderSpringApp.storage.BookStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

private BookService bookService;

    public BookController(BookStorage bookStorage) {
        this.bookService = bookService;
    }

    @GetMapping("/getAll") //http://localhost:8080/book/getAll
    public List<Book> getBooks(){
        return bookService.getAll();
    }



}
