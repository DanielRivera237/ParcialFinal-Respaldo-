package com.example.conexiondebases.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ReporteAController {
    @FXML
    private TextField idClienteField;
    @FXML
    private DatePicker fechaInicioField;
    @FXML
    private DatePicker fechaFinField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private TextArea outputArea;
    @FXML
    private Button regresarButton;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());
    }

    private void generarReporte() {
        outputArea.appendText("Generando Reporte A para cliente ID: " + idClienteField.getText() + "\n");
    }

    @FXML
    public void regresarButton() {
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
