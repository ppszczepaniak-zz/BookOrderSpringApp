package com.example.BookOrderSpringApp.services;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.repositories.OrderStorage;
import org.springframework.stereotype.Service;


@Service
public class OrderService {
    private OrderStorage orderStorage;


    public OrderService(OrderStorage orderStorage) {
        this.orderStorage = orderStorage;
    }

    public Order add(Order order) {
        return orderStorage.addOrder(order);
    }

}
