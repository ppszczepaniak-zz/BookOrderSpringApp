package com.example.BookOrderSpringApp.repositories;

import com.example.BookOrderSpringApp.models.Order;

import java.util.List;

public interface OrderStorage {


    Order getOrder(long orderId);

    List<Order> getAllOrders();  //secondary to implement

    long addOrder(Order order); //should return OrderId + should change two tables: orders & order_items

    /**
     * to add values in orders
     * ```
     * INSERT INTO orders (order_id, order_date,customer_id) VALUES (NEXTVAL('sequence_orders'), CURRENT_TIMESTAMP, 1);
     * ```
     * >CURRENT_TIMESTAMP == now()
     */


    //you will encounter this problem with JSON and ObjectMapper:
    /*

{
         "klientID":1
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

    void clearTableOrders();

    //TODO add Delete order feature
}
