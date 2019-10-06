package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Customer;
import com.example.BookOrderSpringApp.repositories.CustomerStorage;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerStorageImpl extends AbstractStorage implements CustomerStorage {

    private static List<Customer> customerList = new ArrayList<>();

    @Override
    public Customer getCustomer(long customerId) {
        final String sqlSelectOneCustomer = "SELECT * from books WHERE customer_id = ?;";
        Connection connection = databaseManager.initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectOneCustomer);
            preparedStatement.setLong(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setName(resultSet.getString("name"));
                customer.setCustomerId(resultSet.getLong("customer_id"));
                return customer;
            }

        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally { //zamykam statement i connection
            databaseManager.closeDatabaseResources(preparedStatement, connection);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        customerList.clear(); //czyszcze liste, bo inaczej kazde wywolanie getAllBooks dopisywaloby wszystkie do listy
        final String sqlSelectAllCustomers = "SELECT * from customers;";

        Connection connection = databaseManager.initializeDataBaseConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement(); //statement a nie preparedStatement bo proste zapytanie bez parametrow
            ResultSet resultSet = statement.executeQuery(sqlSelectAllCustomers); //odbieram z bazy wg zapytania (cala tabela books)

            while (resultSet.next()) {  //next idzie na kolejny wiersz i zwraca true, jeśli jest
                Customer customer = new Customer(); //create new customer, if there's another record in db

                customer.setCustomerId(resultSet.getLong("customer_id"));
                customer.setName(resultSet.getString("name"));

                customerList.add(customer);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally {
            databaseManager.closeDatabaseResources(statement, connection);
        }
        return customerList;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        long customerId = 0;
        final String sqlInsertCustomer = "INSERT INTO customers(" +
                "customer_id, name)" +
                "VALUES (NEXTVAL('sequence_customers'),?) RETURNING customer_id;"; //customerId will be passed to CustomerController

        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertCustomer);
            preparedStatement.setString(1, customer.getName());  //parameterIndex: numeruje znaki zapytania
            preparedStatement.execute(); //odpalam statement poprzez execute, a nie executeUpdate() bo ten drugi nie spodziewa sie zadnych zwrotow z RETURNING
            ResultSet resultSet = preparedStatement.getResultSet(); //odbieram wyniki z returning

            if (resultSet.next()) { //jesli jest jakis  + przesuwa o jeden kursor, zebym mogl odczytac
                customerId = resultSet.getLong(1); //to wez z niego wartosc
            }
            customer.setCustomerId(customerId);
            return customer;

        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally { //zamykam statement i connection
            databaseManager.closeDatabaseResources(preparedStatement, connection);
        }
    }

    @Override
    public void clearTableCustomers() {
        final String sqlClearDB = "DELETE from customers; " + "ALTER SEQUENCE sequence_customers RESTART;"; //removes all from table & restarts the sequence
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

