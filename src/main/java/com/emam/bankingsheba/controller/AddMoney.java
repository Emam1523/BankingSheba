package com.emam.bankingsheba.controller;

import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.service.WalletService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddMoney {
    @FXML private TextField amount;
    @FXML private Button addButton;
    @FXML private Button backButton;
    @FXML private Label messageLabel;

    private User user;
    private Dashboard dashboard;
    private final WalletService walletService = new WalletService();

    public void setContext(User user, Dashboard dashboard) {
        this.user = user;
        this.dashboard = dashboard;
    }

    @FXML
    public void onConfirm() {
        try {
            double value = Double.parseDouble(amount.getText().trim());
            boolean success = walletService.deposit(user, value);
            if (success) {
                messageLabel.setText("Amount added successfully");
                if (dashboard != null) dashboard.refreshUI();
            } else {
                messageLabel.setText("Failed to add amount");
            }
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid amount");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error processing request");
        }
    }

    @FXML
    public void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/emam/fxml/dashBoard.fxml"));
            Parent parent = loader.load();
            Dashboard ctrl = loader.getController();
            if (ctrl != null && user != null) {
                ctrl.setUser(user);
            }
            backButton.getScene().setRoot(parent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Parent login = FXMLLoader.load(getClass().getResource("/com/emam/fxml/login.fxml"));
                backButton.getScene().setRoot(login);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
