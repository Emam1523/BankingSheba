// java
package com.emam.bankingsheba.controller;

import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.service.TransactionService;
import com.emam.bankingsheba.service.WalletService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.lang.reflect.Method;

public class Dashboard {
    @FXML private Label welcomeLabel;
    @FXML private Label balanceLabel;
    @FXML private Button addButton;
    @FXML private Button withdrawButton;
    @FXML private Button historyButton;
    @FXML private Button logoutButton;

    private User user;
    private final WalletService walletService = new WalletService();
    private final TransactionService transactionService = new TransactionService();

    @FXML public void initialize() { }

    @FXML
    public void onAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emam/fxml/addMoney.fxml"));
            Parent parent = loader.load();
            Object ctrl = loader.getController();

            invokeIfExists(ctrl, "setContext", new Class<?>[]{User.class, Dashboard.class}, user, this);
            addButton.getScene().setRoot(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onWithdraw() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emam/fxml/withdraw.fxml"));
            Parent p = loader.load();
            Object ctrl = loader.getController();
            invokeIfExists(ctrl, "setContext", new Class<?>[]{User.class, Dashboard.class}, user, this);
            withdrawButton.getScene().setRoot(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emam/fxml/transactionHistory.fxml"));
            Parent p = loader.load();
            Object ctrl = loader.getController();
            invokeIfExists(ctrl, "setUser", new Class<?>[]{User.class}, user);
            historyButton.getScene().setRoot(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLogout() {
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/com/emam/fxml/login.fxml"));
            logoutButton.getScene().setRoot(p);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void refreshUI() {
        if (user != null) {
            welcomeLabel.setText("Welcome, " + user.getUsername());
            balanceLabel.setText(String.format("Balance: %.2f", user.getBalance()));
        }
    }

    public void setUser(User user) {
        this.user = user;
        refreshUI();
    }

    private void invokeIfExists(Object controller, String methodName, Class<?>[] paramTypes, Object... args) {
        if (controller == null) return;
        try {
            Method m = controller.getClass().getMethod(methodName, paramTypes);
            m.invoke(controller, args);
        } catch (NoSuchMethodException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
