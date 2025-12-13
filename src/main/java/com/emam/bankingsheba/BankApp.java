package com.emam.bankingsheba;

import com.emam.bankingsheba.config.DBInit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("[INFO] Starting Banking Sheba application...");

            DBInit.init();

            System.out.println("[INFO] Loading login.fxml...");
            var fxmlUrl = getClass().getResource("/com/emam/fxml/login.fxml");
            if (fxmlUrl == null) {
                throw new IllegalStateException("login.fxml not found in resources at /com/emam/fxml/login.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Scene scene = new Scene(root);

            var cssUrl = getClass().getResource("/css/style.css");
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("[INFO] Stylesheet applied: " + cssUrl);
            } else {
                System.out.println("[WARN] Stylesheet not found at /css/style.css");
            }

            stage.setTitle("Banking Sheba");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            System.out.println("[INFO] UI shown. You should see the Login window now.");

        } catch (Exception e) {
            System.err.println("[ERROR] Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
