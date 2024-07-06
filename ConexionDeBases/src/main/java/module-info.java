module com.example.conexiondebases {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.conexiondebases to javafx.fxml;
    exports com.example.conexiondebases;
}