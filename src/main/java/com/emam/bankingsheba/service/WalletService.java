package com.emam.bankingsheba.service;

import com.emam.bankingsheba.model.Wallet;
import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.model.Transaction;
import java.time.LocalDateTime;

public class WalletService {
    private final UserService userService = new UserService();
    private final TransactionService transactionService = new TransactionService();

    public Wallet getWallet(User user) {
        return new Wallet(user);
    }

    public boolean deposit(User user, double amount) {
        if (amount <= 0) {
            return false;
        }
        Wallet wallet = getWallet(user);
        wallet.deposit(amount);

        // Update DB balance
        boolean update = userService.updateUserBalance(user.getId(), user.getBalance());
        if (update) {
            Transaction transaction = new Transaction(user.getId(), amount, "DEPOSIT", LocalDateTime.now());
            transactionService.create(transaction);
            return true;
        }
        return false;
    }

    public boolean withdraw(User user, double amount) {
        if (amount <= 0) return false;
        Wallet w = getWallet(user);
        boolean ok = w.withdraw(amount);
        if (!ok) return false;

        boolean updated = userService.updateUserBalance(user.getId(), user.getBalance());
        if (updated) {
            Transaction transaction = new Transaction(user.getId(), amount, "WITHDRAW", LocalDateTime.now());
            transactionService.create(transaction);
            return true;
        }
        return false;
    }
}
