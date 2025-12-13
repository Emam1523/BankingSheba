package com.emam.bankingsheba.service;

import com.emam.bankingsheba.config.Database;
import com.emam.bankingsheba.model.User;

import java.security.MessageDigest;
import java.sql.*;
import java.nio.charset.StandardCharsets;

public class UserService {
    public static String hashPassword(String password) {
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte x : b) sb.append(String.format("%02x", x));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public User findUserName(String username){
        String sql ="SELECT * FROM users WHERE username = ?";
        try(Connection connection = Database.getConnection(); 
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean createUser(String username, String password){
        String sql = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?)";
        try(Connection connection = Database.getConnection(); 
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, hashPassword(password));
            ps.setDouble(3, 0.0);
            int affectedRows = ps.executeUpdate();
            if(affectedRows == 0){
                return false;
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            return generatedKeys.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean verifyUser(String username, String password){
        if (username == null || password == null) {
            return false;
        }
        User user = findUserName(username);
        if (user == null) return false;
        String hashed = hashPassword(password);
        return hashed.equals(user.getPassword());
    }

    public boolean updateUserBalance(int userId, double newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
