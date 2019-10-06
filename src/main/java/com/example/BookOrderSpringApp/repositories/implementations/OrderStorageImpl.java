package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.models.Order;
import com.example.BookOrderSpringApp.models.OrderItem;
import com.example.BookOrderSpringApp.repositories.OrderStorage;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderStorageImpl extends AbstractStorage implements OrderStorage { //TODO fill these

    private static List<Order> orderList = new ArrayList<>();
    private static List<OrderItem> orderItemList = new ArrayList<>();

    @Override
    public Order getOrder(long orderId) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order addOrder(Order order) {

        long orderId = 0;
        long customerId = 0;

        final String sqlInsertOrder = "INSERT INTO orders(order_id, order_date, customer_id)" +
                "VALUES (NEXTVAL('sequence_orders'), CURRENT_TIMESTAMP, ?) RETURNING order_id, customer_id;" +
                //zmieniam orders table
                //1) dodaje NEXTVAL('sequence_orders') zeby autonumerowal po mojej wlasnej sekw. ktora stworzylem w POSTGRES
                // 2) RETURNING zwraca wartosc z customer_id, po to zebym mogl ja przekazac do OrderControllera
                //CURRENT_TIMESTAMP == now()
                "INSERT INTO order_items(order_item_id, order_id, book_id, amount)" +
                "VALUES (NEXTVAL('sequence_order_items'), ?, ?, ?) RETURNING book_id;";
                //zmieniam order_items table

        Connection connection = databaseManager.initializeDataBaseConnection(); //odpalamy połączenie
        PreparedStatement preparedStatement = null;



        //you will encounter this problem with JSON and ObjectMapper:
    /*

{
         "customer_id":1
         "zamowienie":
        [
            {
            "bookID":1,
            "amount":2
            },
            {
            "bookID":2,
            "amount":4
            },
            {
            "bookID":3,
            "amount":10
            }
        ]
}

this JSON comes from frontEnd
and this should be converted with addOrder() to update tables orders & order_items
BUT
ObjectMapper (which creates objects from JSON) won't have full information about objects here (e.g. for client: only clientID, no client name)
therefore you may solve this in such a way (SOMEHOW, figure this out):
1) create DTO object using ObjectMapper  //data transfer object
2) use DTO to create real object
     */


        try {  //przygotowuje Statement (prepared statement)
            preparedStatement = connection.prepareStatement(sqlInsertOrder);
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














        return 0;
    }


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
