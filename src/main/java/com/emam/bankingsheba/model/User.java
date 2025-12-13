package com.emam.bankingsheba.model;

public class User {
    private int id;
    private String username;
    private String password;
    private double balance;

    public User(int id, String username, String password, double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }
    public User(String username, String password, double balance) {
        this(0, username, password, balance);
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setId(int id) {
        this.id = id;
    }
}
