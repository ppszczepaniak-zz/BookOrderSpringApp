package com.example.BookOrderSpringApp.models;


public class OrderItem {
    private long orderItemId;
    private long orderId;
    private long bookId;
    private long amount;

    public OrderItem() {
    }

    public OrderItem(long orderItemId, long orderId, long bookId, long amount) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.amount = amount;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
