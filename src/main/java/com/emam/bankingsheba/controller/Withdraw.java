package com.emam.bankingsheba.controller;

import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.service.WalletService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

public class Withdraw {
    @FXML private TextField amount;
    @FXML private Button confirmButton;
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
            boolean success = walletService.withdraw(user, value);
            if (success) {
                messageLabel.setText("Amount withdrawn successfully");
                if (dashboard != null) dashboard.refreshUI();
            } else {
                messageLabel.setText("Failed to withdraw amount");
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
        }
    }
}

