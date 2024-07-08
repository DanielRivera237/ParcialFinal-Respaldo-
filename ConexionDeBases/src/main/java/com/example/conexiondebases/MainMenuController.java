package com.example.conexiondebases;

import com.example.conexiondebases.Controllers.AdminOptionsController;
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
    private Button cliente;
    @FXML
    private Button administrador;

    @FXML
    private void administradorOpciones() {
        try {
            Stage stage = (Stage) administrador.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/conexiondebases/opciones-administrador.fxml"));
            Parent root = loader.load();

            AdminOptionsController adminController = loader.getController();
            adminController.setConexion(conexion); // Pasar la conexión

            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }
}
