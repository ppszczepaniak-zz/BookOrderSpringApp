package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.repositories.OrderStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
