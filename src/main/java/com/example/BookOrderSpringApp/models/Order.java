package com.example.BookOrderSpringApp.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long orderId;
    private LocalDateTime orderDate;
    private Customer customer;
    private List<OrderItem> orderItemList;  //because order contains list of order_items

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public Order() {
    }

    public Order(long orderId, LocalDateTime orderDate, Customer customer, List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.orderItemList = orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
