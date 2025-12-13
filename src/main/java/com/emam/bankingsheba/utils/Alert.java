package com.emam.bankingsheba.utils;

import javafx.scene.control.Alert.AlertType;

public class Alert {
    public static void info(String title, String message) {
        show(AlertType.INFORMATION, title, message);
    }

    public static void warn(String title, String message) {
        show(AlertType.WARNING, title, message);
    }

    public static void error(String title, String message) {
        show(AlertType.ERROR, title, message);
    }

    private static void show(AlertType type, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle(title == null ? "" : title);
        alert.setHeaderText(null);
        alert.setContentText(message == null ? "" : message);
        alert.showAndWait();
    }
}
