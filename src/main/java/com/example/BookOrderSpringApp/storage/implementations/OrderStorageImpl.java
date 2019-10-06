package com.example.BookOrderSpringApp.storage.implementations;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.storage.OrderStorage;

import java.util.List;

public class OrderStorageImpl implements OrderStorage { //TODO fill these
    @Override
    public Order getOrder(long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public long addOrder(Order order) {
        return 0;
    }

    @Override
    public void clearTableOrders() {

    }
}
