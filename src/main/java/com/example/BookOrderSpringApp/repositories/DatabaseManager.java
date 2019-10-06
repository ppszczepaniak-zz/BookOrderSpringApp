package com.example.BookOrderSpringApp.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bookorder"; //create it first
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASS = "password";  //or postgress

    static {  //loading Driver class so it works on older Java or JDBC (blok statyczny, odpali sie na starcie)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Server can't find postgresql Driver class:\n" + e);
        }
    }

    public Connection initializeDataBaseConnection() { //otwiera polaczenie
        try {
            return DriverManager.getConnection(JDBC_URL, DATABASE_USER, DATABASE_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection:\n" + e);
            throw new RuntimeException("Server can't initialize database connection"); //Runtime rzucony po to zeby tu przerwal dzialanie programu
        }
    }

    public void closeDatabaseResources(Statement statement, Connection connection) { //zamyka polaczenie i statement
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
}
