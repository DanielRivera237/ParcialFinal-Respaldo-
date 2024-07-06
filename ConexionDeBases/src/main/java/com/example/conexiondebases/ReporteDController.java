package com.example.conexiondebases;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class ReporteDController {
    @FXML
    private TextField facilitadorField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());
    }

    private void generarReporte() {
        outputArea.appendText("Generando Reporte D para facilitador: " + facilitadorField.getText() + "\n");
    }
}
