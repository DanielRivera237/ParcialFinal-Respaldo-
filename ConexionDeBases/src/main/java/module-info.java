module com.example.conexiondebases {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.conexiondebases to javafx.fxml;
    exports com.example.conexiondebases;

    exports com.example.conexiondebases.Controllers;
    opens com.example.conexiondebases.Controllers to javafx.fxml;

    opens com.example.conexiondebases.Clases_auxiliares.Reportes to javafx.fxml;
}