package com.example.BookOrderSpringApp.controller;

import com.example.BookOrderSpringApp.models.Customer;
import com.example.BookOrderSpringApp.services.CustomerService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll") //http://localhost:8080/customer/getAll
    public List<Customer> getCustomers() {
        return customerService.getAll();
    }

    @GetMapping("/get") ////http://localhost:8080/customer/get
    public Customer getCustomer(@Param("customerId") long customerId) {
        return customerService.get(customerId);
    }

    @PostMapping("/add") //http://localhost:8080/customer/add
    @ResponseStatus(HttpStatus.CREATED) //201
    public Customer addBook(@RequestBody Customer customer) {
        return customerService.add(customer);
    }

}
