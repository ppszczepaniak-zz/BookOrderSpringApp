package com.example.BookOrderSpringApp.storage.implementations;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.storage.BookStorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookStorageImpl implements BookStorage {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bookorder"; //create it first
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASS = "password";  //or postgress
    private static List<Book> bookList = new ArrayList<>();

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
    public long addBook(Book book) {
        long bookId = 0;
        final String sqlInsertBook = "INSERT INTO books(" +
                "book_id, title)" +
                "VALUES (NEXTVAL('sequence_books'),?) RETURNING book_id;"; //1) dodaje NEXTVAL('sequence_books') zeby autonumerowal
        // po mojej wlasnej sekw. ktora stworzylem w POSTGRES
        // 2) RETURNING zwraca wartosc z book_id,
        // po to zebym mogl ja przekazac do BookControllera

        Connection connection = initializeDataBaseConnection(); //odpalamy połączenie
        PreparedStatement preparedStatement = null;

        try {  //przygotowuje Statement (prepared statement)
            preparedStatement = connection.prepareStatement(sqlInsertBook);
            preparedStatement.setString(1, book.getTitle());  //parameterIndex: numeruje znaki zapytania!
            preparedStatement.execute(); //odpalam statement poprzez execute, a nie executeUpdate()
            // bo ten drugi nie spodziewa sie zadnych zwrotow z RETURNING

            ResultSet resultSet = preparedStatement.getResultSet(); //odbieram wyniki z returning
            if (resultSet.next()) { //jesli jest jakis  + przesuwa o jeden kursor, zebym mogl odczytac
                bookId = resultSet.getLong(1); //to wez z niego wartosc
            }

            return bookId; //zwraca bookId

        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally { //zamykam statement i connection
            closeDatabaseResources(preparedStatement, connection);
        }
    }

    @Override
    public Book getBook(long bookId) {
        final String sqlSelectAllBook = "SELECT * from books WHERE book_id = ?;";
        Connection connection = initializeDataBaseConnection(); //odpalamy połączenie
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectAllBook);
            preparedStatement.setLong(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {  //jesli jest taka ksiazka w bazie to zwroc, else null (na koncu)
                Book book = new Book(); //tworze nowa ksiazke, jesli jest kolejny rekord w bazie
                book.setBookId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));

                return book;
            }
        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");
        } finally {//zamykam statement i connection
            closeDatabaseResources(preparedStatement, connection);
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        bookList.clear(); //czyszcze liste, bo inaczej kazde wywolanie getAllBooks dopisywaloby wszystkie do listy
        final String sqlSelectAllBook = "SELECT * from books;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;

        try {
            statement = connection.createStatement(); //statement a nie preparedStatement bo proste zapytanie bez parametrow
            ResultSet resultSet = statement.executeQuery(sqlSelectAllBook); //odbieram z bazy wg zapytania (cala tabela books)

            while (resultSet.next()) {  //next idzie na kolejny wiersz i zwraca true, jeśli jest
                Book book = new Book(); //tworze nowa ksiazke, jesli jest kolejny rekord w bazie

                book.setBookId(resultSet.getLong("book_id"));
                book.setTitle(resultSet.getString("title"));

                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally {
            closeDatabaseResources(statement, connection);
        }
        return bookList;
    }

    @Override
    public void clearTableBooks() {
        final String sqlClearDB = "DELETE from books"; //removes all from table
        Connection connection = initializeDataBaseConnection(); //odpalamy połączenie
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeQuery(sqlClearDB);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDatabaseResources(statement, connection);
        }
    }

}
