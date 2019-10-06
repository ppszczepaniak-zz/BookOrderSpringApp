package com.example.BookOrderSpringApp.models;

public class Book {
    private long bookId;
    private String title;

    public Book(long bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
