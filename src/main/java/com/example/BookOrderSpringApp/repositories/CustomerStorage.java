package com.example.BookOrderSpringApp.repositories;

import com.example.BookOrderSpringApp.models.Customer;

import java.util.List;

public interface CustomerStorage {
    Customer getCustomer(long customerId);

    List<Customer> getAllCustomers();

    Customer addCustomer(Customer customer); //shoud return customerId

    void clearTableCustomers();

    //TODO Delete customer feature
}
