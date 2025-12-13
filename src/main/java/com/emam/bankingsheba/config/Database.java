package com.emam.bankingsheba.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final Path DB_PATH = Paths.get(System.getProperty("user.home"), ".bankingsheba", "database.db");

    public static Connection getConnection() {
        try {
            Files.createDirectories(DB_PATH.getParent());
            String url = "jdbc:sqlite:" + DB_PATH.toAbsolutePath();
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println("[ERROR] Unable to open SQLite database at " + DB_PATH.toAbsolutePath());
            throw new RuntimeException("Failed to connect to SQLite", e);
        }
    }
}