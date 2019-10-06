package com.example.BookOrderSpringApp.services;

import com.example.BookOrderSpringApp.models.Customer;
import com.example.BookOrderSpringApp.repositories.implementations.CustomerStorageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerStorageImpl customerStorage;

    public CustomerService(CustomerStorageImpl customerStorage) {
        this.customerStorage = customerStorage;
    }

    public List<Customer> getAll() {
        return customerStorage.getAllCustomers();
    }

    public Customer add(Customer customer) {
        return customerStorage.addCustomer(customer);
    }

    public Customer get(long customerId) {
        return customerStorage.getCustomer(customerId);
    }
}
