package com.example.conexiondebases; //00104923 declaración del paquete

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController { //00104923 declaración de la clase MainMenuController
    private Conexion conexion; //00104923 declaración de la variable conexion

    @FXML
    private Button cliente; //00104923 declaración del botón cliente

    @FXML
    private Button administrador; //00104923 declaración del botón administrador

    @FXML
    private void administradorOpciones() { //00104923 método para manejar opciones de administrador
        try {
            Stage stage = (Stage) administrador.getScene().getWindow(); //00104923 obtiene la ventana actual
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/conexiondebases/inicio-sesion.fxml")); //00104923 carga el archivo FXML
            Parent root = loader.load(); //00104923 carga el contenido del archivo FXML
            stage.setScene(new Scene(root)); //00104923 establece la nueva escena
            stage.centerOnScreen(); //00104923 centra la ventana en la pantalla
            stage.show(); //00104923 muestra la ventana
        } catch (IOException e) { //00104923 manejo de excepciones IO
            e.printStackTrace(); //00104923 imprime el stack trace de la excepción
        }
    }

    @FXML
    private void clienteOpciones() { //00104923 método para manejar opciones de cliente
        try {
            Stage stage = (Stage) cliente.getScene().getWindow(); //00104923 obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("ingreso-datos-cliente.fxml")); //00104923 carga el archivo FXML
            stage.setScene(new Scene(root)); //00104923 establece la nueva escena
            stage.centerOnScreen(); //00104923 centra la ventana en la pantalla
            stage.show(); //00104923 muestra la ventana
        } catch (IOException e) { //00104923 manejo de excepciones IO
            e.printStackTrace(); //00104923 imprime el stack trace de la excepción
        }
    }

    public void setConexion(Conexion conexion) { //00104923 método para establecer la conexión
        this.conexion = conexion; //00104923 asigna la variable conexion
    }
}
