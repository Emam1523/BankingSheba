module com.emam.bankingsheba {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    // Open packages for JavaFX reflection (FXML controller injection)
    opens com.emam.bankingsheba to javafx.fxml;
    opens com.emam.bankingsheba.controller to javafx.fxml;
    opens com.emam.bankingsheba.model to javafx.base;

    exports com.emam.bankingsheba;
}