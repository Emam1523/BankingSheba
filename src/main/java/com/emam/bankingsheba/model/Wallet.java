package com.emam.bankingsheba.model;

public class Wallet extends BaseAccount {
    public Wallet(User user) {
        super(user);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            double newBalance = user.getBalance() + amount;
            user.setBalance(newBalance);
        }
    }
    @Override
    public boolean withdraw(double amount) {
        if(amount > 0 && user.getBalance()-amount>=0){
            double newBalance = user.getBalance() - amount;
            user.setBalance(newBalance);
            return true;
        }
        return false;
    }
}