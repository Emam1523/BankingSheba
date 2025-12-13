package com.emam.bankingsheba.model;

public class BaseAccount {
    protected User user;
    public BaseAccount(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void deposit(double amount) {
        return;
    }

    public boolean withdraw(double amount) {
        return false;
    }
}
