package com.emam.bankingsheba.config;

import com.emam.bankingsheba.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBInit {

    public static void init() {
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    balance REAL DEFAULT 0
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    balance REAL DEFAULT 0,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER,
                    type TEXT,
                    amount REAL,
                    timestamp TEXT DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
            """);

            final String defaultUsername = "emamhassan";
            final String defaultPasswordHash = UserService.hashPassword("1523");

            try (PreparedStatement check = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
                check.setString(1, defaultUsername);
                try (ResultSet rs = check.executeQuery()) {
                    if (!rs.next()) {
                        try (PreparedStatement insert = conn.prepareStatement(
                                "INSERT INTO users (username, password, balance) VALUES (?, ?, 0.0)")) {
                            insert.setString(1, defaultUsername);
                            insert.setString(2, defaultPasswordHash);
                            insert.executeUpdate();
                            System.out.println("[INFO] Default user created: username='" + defaultUsername + "' password='1523'");
                        }
                    } else {
                        System.out.println("[INFO] Default user already exists: " + defaultUsername);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
