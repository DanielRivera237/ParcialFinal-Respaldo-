package com.example.conexiondebases;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class ReporteBController {
    @FXML
    private TextField idClienteField;
    @FXML
    private TextField mesField;
    @FXML
    private TextField anioField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());
    }

    private void generarReporte() {
        outputArea.appendText("Generando Reporte B para cliente ID: " + idClienteField.getText() + " Mes: " + mesField.getText() + " Año: " + anioField.getText() + "\n");
    }
}
