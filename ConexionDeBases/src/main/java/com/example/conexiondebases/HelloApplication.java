package com.example.conexiondebases;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Conexion conexion = new Conexion("labo13", "walle", "LABO", "localhost", "3306");
        if (!conexion.connect()) {
            System.err.println("Error en la conexión de la base de datos");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MainMenuController controller = fxmlLoader.getController();
        controller.setConexion(conexion);

        stage.setTitle("Banco Central de Nlogonia (BCN)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
