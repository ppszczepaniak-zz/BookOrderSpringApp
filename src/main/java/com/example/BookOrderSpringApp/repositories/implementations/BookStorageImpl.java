package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Book;
import com.example.BookOrderSpringApp.repositories.BookStorage;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookStorageImpl extends AbstractStorage implements BookStorage {

    private static List<Book> bookList = new ArrayList<>();

    @Override
    public Book addBook(Book book) {
        long bookId = 0;
        final String sqlInsertBook = "INSERT INTO books(" +
                "book_id, title)" +
                "VALUES (NEXTVAL('sequence_books'),?) RETURNING book_id;"; //1) dodaje NEXTVAL('sequence_books') zeby autonumerowal
        // po mojej wlasnej sekw. ktora stworzylem w POSTGRES
        // 2) RETURNING zwraca wartosc z book_id,
        // po to zebym mogl ja przekazac do BookControllera

        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
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

            book.setBookId(bookId); //nadaje odczytany ID ksiazce ktora dodaje
            return book; //zwraca bookId

        } catch (SQLException e) {
            System.err.println("Error during invoking SQL query:\n" + e.getMessage());
            throw new RuntimeException("Error during invoking SQL query");

        } finally { //zamykam statement i connection
            databaseManager.closeDatabaseResources(preparedStatement, connection);
        }
    }


    @Override
    public Book getBook(long bookId) {
        final String sqlSelectAllBook = "SELECT * from books WHERE book_id = ?;";
        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
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
            databaseManager.closeDatabaseResources(preparedStatement, connection);
        }
        return null;
    }

    @Override
    public List<Book> getAllBooks() {
        bookList.clear(); //czyszcze liste, bo inaczej kazde wywolanie getAllBooks dopisywaloby wszystkie do listy
        final String sqlSelectAllBook = "SELECT * from books;";

        Connection connection = databaseManager.initializeDataBaseConnection();
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
            databaseManager.closeDatabaseResources(statement, connection);
        }
        return bookList;
    }

    @Override
    public void clearTableBooks() {
        final String sqlClearDB = "DELETE from books; " +
                "ALTER SEQUENCE sequence_books RESTART;"; //removes all from table and restarts the sequence
        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(sqlClearDB);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseManager.closeDatabaseResources(statement, connection);
        }
    }


}
