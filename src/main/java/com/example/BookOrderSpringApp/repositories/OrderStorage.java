package com.example.BookOrderSpringApp.repositories;

import com.example.BookOrderSpringApp.models.Order;

import java.util.List;

public interface OrderStorage {

    Order getOrder(long orderId);

    List<Order> getAllOrders();  //secondary to implement

    Order addOrder(Order order); //should return Order + should change two tables: orders & order_items

    void clearTableOrders();

    //TODO add Delete order feature
}
