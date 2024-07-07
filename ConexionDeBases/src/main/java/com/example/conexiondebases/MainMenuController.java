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
    private Stage primaryStage;

    @FXML
    private Button reporteAButton;

    @FXML
    private Button reporteBButton;

    @FXML
    private Button reporteCButton;

    @FXML
    private Button reporteDButton;

    @FXML
    private Button regresarButton;

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        reporteAButton.setOnAction(e -> showReporteA());
        reporteBButton.setOnAction(e -> showReporteB());
        reporteCButton.setOnAction(e -> showReporteC());
        reporteDButton.setOnAction(e -> showReporteD());
    }

    private void showReporteA() {
        showWindow("listar-compras-por-cliente.fxml", "Reporte A");
    }

    private void showReporteB() {
        showWindow("total-gastado-por-cliente.fxml", "Reporte B");
    }

    private void showReporteC() {
        showWindow("listar-tarjetas-por-cliente.fxml", "Reporte C");
    }

    private void showReporteD() {
        showWindow("clientes-por-facilitador-tarjeta.fxml", "Reporte D");
    }

    private void showWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar la ventana del menú principal
            if (primaryStage != null) {
                primaryStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
