package com.example.conexiondebases;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    private Conexion conexion;

    @FXML
    private Button reporteAButton;

    @FXML
    private Button reporteBButton;

    @FXML
    private Button reporteCButton;

    @FXML
    private Button reporteDButton;

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    @FXML
    public void initialize() {
        reporteAButton.setOnAction(e -> showReporteA());
        reporteBButton.setOnAction(e -> showReporteB());
        reporteCButton.setOnAction(e -> showReporteC());
        reporteDButton.setOnAction(e -> showReporteD());
    }

    private void showReporteA() {
        showWindow("reporte-a.fxml", "Reporte A");
    }

    private void showReporteB() {
        showWindow("reporte-b.fxml", "Reporte B");
    }

    private void showReporteC() {
        showWindow("reporte-c.fxml", "Reporte C");
    }

    private void showReporteD() {
        showWindow("reporte-d.fxml", "Reporte D");
    }

    private void showWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
