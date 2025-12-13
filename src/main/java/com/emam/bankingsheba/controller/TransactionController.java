package com.emam.bankingsheba.controller;

import com.emam.bankingsheba.model.Transaction;
import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.service.TransactionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionController {
    @FXML private TableView<Transaction> table;
    @FXML private TableColumn<Transaction, Integer> idColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> dateColumn;
    @FXML private Button backButton;

    private User user;
    private final TransactionService transactionService = new TransactionService();

    public void setUser(User user) {
        this.user = user;
        load();
    }

    @SuppressWarnings("unchecked")
    private void load() {
        List<Transaction> list = Collections.emptyList();
        if (user == null) {
            table.setItems(FXCollections.observableArrayList(list));
            return;
        }
        try {
            Method[] methods = transactionService.getClass().getMethods();
            for (Method m : methods) {
                if (List.class.isAssignableFrom(m.getReturnType()) && m.getParameterCount() == 1) {
                    try {
                        Object res = m.invoke(transactionService, user.getId());
                        if (res instanceof List) {
                            list = (List<Transaction>) res;
                            break;
                        }
                    } catch (IllegalArgumentException iae) {
                        try {
                            Object res = m.invoke(transactionService, Integer.valueOf(user.getId()));
                            if (res instanceof List) {
                                list = (List<Transaction>) res;
                                break;
                            }
                        } catch (Exception ignored) { }
                    } catch (Exception ignored) { }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null) list = new ArrayList<>();

        table.setItems(FXCollections.observableArrayList(list));
        idColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());
        typeColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getType()));
        amountColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleDoubleProperty(c.getValue().getAmount()).asObject());
        dateColumn.setCellValueFactory(c -> {
            Transaction tx = c.getValue();
            String dateStr = "";
            if (tx != null) {
                try {
                    Method dateMethod = null;
                    String[] candidates = {"getCreatedAt", "getCreated", "getTimestamp", "getDate", "getCreatedAtTime"};
                    for (String name : candidates) {
                        try {
                            dateMethod = tx.getClass().getMethod(name);
                            break;
                        } catch (NoSuchMethodException ignored) { }
                    }
                    if (dateMethod != null) {
                        Object val = dateMethod.invoke(tx);
                        if (val != null) dateStr = val.toString();
                    } else {
                        dateStr = tx.toString();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    dateStr = tx.toString();
                }
            }
            return new javafx.beans.property.SimpleStringProperty(dateStr);
        });
    }

    @FXML
    public void onBack() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/com/emam/fxml/dashBoard.fxml"));
            backButton.getScene().setRoot(p);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
