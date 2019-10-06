package storage.implementations;

import models.Order;
import storage.OrderStorage;

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
