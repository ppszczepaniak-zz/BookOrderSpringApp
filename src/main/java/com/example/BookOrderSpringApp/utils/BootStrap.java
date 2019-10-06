package com.example.BookOrderSpringApp.utils;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.models.Customer;
import com.example.BookOrderSpringApp.repositories.implementations.BookStorageImpl;
import com.example.BookOrderSpringApp.repositories.implementations.CustomerStorageImpl;
import com.example.BookOrderSpringApp.repositories.implementations.OrderStorageImpl;
import org.springframework.stereotype.Component;

@Component
public class BootStrap {

    private BookStorageImpl bookStorage;
    private CustomerStorageImpl customerStorage;
    private OrderStorageImpl orderStorage;

    public BootStrap(BookStorageImpl bookStorage, CustomerStorageImpl customerStorage, OrderStorageImpl orderStorage) {
        this.bookStorage = bookStorage;
        this.customerStorage = customerStorage;
        this.orderStorage = orderStorage;
    }

    public void initializeDatabase(){
        bookStorage.clearTableBooks(); //clears current database table
        customerStorage.clearTableCustomers();

        Book book1 = new Book(1,"The Witcher");
        Book book2 = new Book(1,"Lord Of The Rings"); //bookId doesn't matter - is given by database
        Book book3 = new Book(1,"Solaris");

        bookStorage.addBook(book1);
        bookStorage.addBook(book2);
        bookStorage.addBook(book3);

        Customer customer1 = new Customer("John Smith",1);
        Customer customer2 = new Customer("Thomas Anderson",1);

        customerStorage.addCustomer(customer1);
        customerStorage.addCustomer(customer2);

    }
}
