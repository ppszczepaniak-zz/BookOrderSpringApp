package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Customer;
import com.example.BookOrderSpringApp.repositories.CustomerStorage;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerStorageImpl implements CustomerStorage {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bookorder"; //create it first
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASS = "postgres";  //or postgress
    private static List<Customer> customerList = new ArrayList<>();

    static {  //loading Driver class so it works on older Java or JDBC (blok statyczny, odpali sie na starcie)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Server can't find postgresql Driver class:\n" + e);
        }
    }

    private Connection initializeDataBaseConnection() { //otwiera polaczenie
        try {
            return DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection:\n" + e);
            throw new RuntimeException("Server can't initialize database connection"); //Runtime rzucony po to zeby tu przerwal dzialanie programu
        }
    }

    private void closeDatabaseResources(Statement statement, Connection connection) { //zamyka polaczenie i statement
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error during closing database resources:\n" + e);
            throw new RuntimeException("Error during closing database resources"); //Runtime rzucony po to zeby tu przerwal dzialanie programu
        }
    }


    @Override
    public Customer getCustomer(long customerId) {
        final String sqlSelectOneCustomer = "SELECT * from books WHERE customer_id = ?;";
        Connection connection = initializeDataBaseConnection();
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
            closeDatabaseResources(preparedStatement, connection);
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        customerList.clear(); //czyszcze liste, bo inaczej kazde wywolanie getAllBooks dopisywaloby wszystkie do listy
        final String sqlSelectAllCustomers = "SELECT * from customers;";

        Connection connection = initializeDataBaseConnection();
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
            closeDatabaseResources(statement, connection);
        }
        return customerList;
    }

    @Override
    public long addCustomer(Customer customer) {
        long customerId = 0;
        final String sqlInsertCustomer = "INSERT INTO customers(" +
                "customer_id, name)" +
                "VALUES (NEXTVAL('sequence_customers'),?) RETURNING customer_id;"; //customerId will be passed to CustomerController

        Connection connection = initializeDataBaseConnection(); //odpalamy połączenie
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlInsertCustomer);
            preparedStatement.setString(1, customer.getName());  //parameterIndex: numeruje znaki zapytania
            preparedStatement.execute(); //odpalam statement poprzez execute, a nie executeUpdate() bo ten drugi nie spodziewa sie zadnych zwrotow z RETURNING
            ResultSet resultSet = preparedStatement.getResultSet(); //odbieram wyniki z returning

            if (resultSet.next()) { //jesli jest jakis  + przesuwa o jeden kursor, zebym mogl odczytac
                customerId = resultSet.getLong(1); //to wez z niego wartosc
            }
            return customerId;

        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally { //zamykam statement i connection
            closeDatabaseResources(preparedStatement, connection);
        }
    }

    @Override
    public void clearTableCustomers() {
        final String sqlClearDB = "DELETE from customers"; //removes all from table
        Connection connection = initializeDataBaseConnection(); //odpalamy połączenie
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeQuery(sqlClearDB);
        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");
        } finally {
            closeDatabaseResources(statement, connection);
        }
    }
}

