package com.example.conexiondebases; //00104923 declaración del paquete


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController { //00104923 declaración de la clase MainMenuController
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
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void clienteOpciones(){
        try {
            Stage stage = (Stage) cliente.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ingreso-datos-cliente.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }
} //00104923 fin clase MainMenuController

