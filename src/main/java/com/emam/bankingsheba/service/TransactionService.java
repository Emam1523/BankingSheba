package com.emam.bankingsheba.service;

import com.emam.bankingsheba.config.Database;
import com.emam.bankingsheba.model.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    public boolean create(Transaction transaction){
        String sql = "INSERT INTO transactions (user_id, amount, type, timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, transaction.getUserId());
            statement.setString(3, transaction.getType());
            statement.setTimestamp(4, Timestamp.valueOf(transaction.getTimestamp()));
            statement.setDouble(2, transaction.getAmount());
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Transaction> userList(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("type"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
