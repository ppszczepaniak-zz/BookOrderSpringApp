package com.example.BookOrderSpringApp.controller;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.services.CustomerService;
import com.example.BookOrderSpringApp.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private CustomerService customerService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add") //http://localhost:8080/order/add
    @ResponseStatus(HttpStatus.CREATED) //201
    public Order addOrder(@RequestBody Order order) {
//
//        long customerID = 0;  //<-przekazac z orderStorage
//
//        Customer customer = customerService.get(customerID);
//        //TODO exception gdy nie ma potrzebny? think, chyba nie


        return orderService.add(order);
    }

}
