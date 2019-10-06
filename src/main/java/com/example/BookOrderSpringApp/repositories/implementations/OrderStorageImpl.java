package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.repositories.OrderStorage;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class OrderStorageImpl extends AbstractStorage implements OrderStorage { //TODO fill these
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
    }  //TODO change to return Order

    @Override
    public void clearTableOrders() {
        final String sqlClearDB = "DELETE from orders; " + "ALTER SEQUENCE sequence_orders RESTART;"; //removes all from table & restarts the sequence
        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(sqlClearDB);
        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");
        } finally {
            databaseManager.closeDatabaseResources(statement, connection);
        }
    }
}
