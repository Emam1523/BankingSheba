// java
package com.emam.bankingsheba.controller;

import com.emam.bankingsheba.model.User;
import com.emam.bankingsheba.service.UserService;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.LoadException;

public class Login {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Label messageLabel;

    private final UserService userService = new UserService();

    @FXML
    private void login(ActionEvent event) {
        String username = this.username.getText().trim();
        String password = this.password.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password");
            return;
        }
        User user = userService.findUserName(username);
        if (user == null || !userService.verifyUser(username, password)) {
            messageLabel.setText("Invalid username or password");
            return;
        }
        try {
            java.net.URL fxmlUrl = getClass().getResource("/com/emam/fxml/dashBoard.fxml");
            if (fxmlUrl == null) {
                String msg = "dashBoard.fxml not found at /com/emam/fxml/dashBoard.fxml. Make sure `src/main/resources/com/emam/fxml/dashBoard.fxml` exists.";
                System.err.println(msg);
                messageLabel.setText("Failed to open dashboard: FXML not found");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load(); // LoadException may be thrown here
            com.emam.bankingsheba.controller.Dashboard controller = loader.getController();
            if (controller != null) controller.setUser(user);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setResizable(false);

        } catch (Exception e) {
            // print full stacktrace to console (important)
            e.printStackTrace();

            // find root cause message
            Throwable root = e;
            while (root.getCause() != null) root = root.getCause();
            String causeMsg = root.getMessage() != null ? root.getMessage() : root.getClass().getSimpleName();

            // show concise error to user
            messageLabel.setText("Failed to open dashboard: " + e.getClass().getSimpleName());
            messageLabel.setText(causeMsg);

            // helpful hint for common problems
            System.err.println("Check: `dashboard.fxml` path/case, `fx:controller` value, and any exceptions in Dashboard.initialize()");
        }
    }

    @FXML
    public void register(ActionEvent event) {
        String username = this.username.getText().trim();
        String password = this.password.getText().trim();
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password");
            return;
        }
        boolean created = userService.createUser(username, password);
        if (!created) {
            messageLabel.setText("Registration failed (username may exist)");
            return;
        }
        messageLabel.setText("Registration successful. You can now log in.");
    }
}
