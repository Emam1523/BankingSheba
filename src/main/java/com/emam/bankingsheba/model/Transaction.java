package com.emam.bankingsheba.model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int userId;
    private double amount;
    private String type;
    private LocalDateTime timestamp;

    public Transaction(int id, int userId, double amount, String type, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Transaction(int userId, double amount, String type, LocalDateTime timestamp) {
        this(0, userId, amount, type, timestamp);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
