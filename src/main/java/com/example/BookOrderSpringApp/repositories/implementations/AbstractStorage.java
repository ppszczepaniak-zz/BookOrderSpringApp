package com.example.BookOrderSpringApp.repositories.implementations;

import com.example.BookOrderSpringApp.repositories.DatabaseManager;

public abstract class AbstractStorage {
    protected DatabaseManager databaseManager = new DatabaseManager();

    public AbstractStorage() {
        this.databaseManager = databaseManager;
    }
}
