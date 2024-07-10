package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.Conexion;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AdminOptionsController {
    private Conexion conexion; //00104923 declaración de la variable Conexion
    private Stage primaryStage; //00104923 declaración de la variable Stage

    @FXML
    private Button reporteAButton; //00104923 variable boton reporte A

    @FXML
    private Button reporteBButton; //00104923 variable boton reporte B

    @FXML
    private Button reporteCButton; //00104923 variable boton reporte C

    @FXML
    private Button reporteDButton; //00104923 variable boton reporte D

    @FXML
    private Button volverButton;

    @FXML
    private Button regresarButton; //00104923 variable boton regresar a ventana del menu opciones administrador

    public void setConexion(Conexion conexion) { //00104923 método setConexion
        this.conexion = conexion; //00104923 asignación de la variable conexion
    }

    public void setPrimaryStage(Stage primaryStage) { //00104923 método setPrimaryStage
        this.primaryStage = primaryStage; //00104923 asignación de la variable primaryStage
    }

    @FXML
    public void showReporteA() throws IOException { //00104923 inicialización metodo mostrar ventana reporte A
        mostrarVentana("/com/example/conexiondebases/ReporteA.fxml", "Reporte A", 1); //00104923 inserto los parametros que necesita el método showWindow()
    }

    @FXML
    private void showReporteB() throws IOException { //00104923 inicialización metodo mostrar ventana reporte B
        mostrarVentana("/com/example/conexiondebases/ReporteB.fxml", "Reporte B", 2); //00104923 inserto los parametros que necesita el método showWindow()
    }

    @FXML
    private void showReporteC() throws IOException { //00104923 inicialización metodo mostrar ventana reporte C
        mostrarVentana( "/com/example/conexiondebases/ReporteC.fxml","Reporte C", 3); //00104923 inserto los parametros que necesita el método showWindow()
    }

    @FXML
    private void showReporteD() throws IOException { //00104923 inicialización metodo mostrar ventana reporte D
        mostrarVentana("/com/example/conexiondebases/ReporteD.fxml", "Reporte D", 4); //00104923 inserto los parametros que necesita el método showWindow()
    }

    private void mostrarVentana(String fxmlFile, String title, int type) throws IOException { //00104923 inicialización metodo showWindow()
        Stage stage = null; //00104923 variable tipo Stage
        Parent root = null; //00104923 variable tipo Parent
        switch (type) { //00104923 condición a evaluar del switch
            case 1: //00104923 caso 1
                stage = (Stage) reporteAButton.getScene().getWindow(); //00104923 asignar Stage para caso 1
                cargarStageFinal(root, stage, fxmlFile); //00104923 llamar método finalShow para caso 1
                break; //00104923 romper caso 1
            case 2: //00104923 caso 2
                stage = (Stage) reporteBButton.getScene().getWindow(); //00104923 asignar Stage para caso 2
                cargarStageFinal(root, stage, fxmlFile); //00104923 llamar método finalShow para caso 2
                break; //00104923 romper caso 2
            case 3: //00104923 caso 3
                stage = (Stage) reporteCButton.getScene().getWindow(); //00104923 asignar Stage para caso 3
                cargarStageFinal(root, stage, fxmlFile); //00104923 llamar método finalShow para caso 3
                break; //00104923 romper caso 3
            case 4: //00104923 caso 4
                stage = (Stage) reporteDButton.getScene().getWindow(); //00104923 asignar Stage para caso 4
                cargarStageFinal(root, stage, fxmlFile); //00104923 llamar método finalShow para caso 4
                break; //00104923 romper caso 4
        }
        // Cerrar la ventana del menú principal
        if (primaryStage != null) { //00104923 condición para cerrar primaryStage
            primaryStage.close(); //00104923 cerrar primaryStage
        }
    }

    private void cargarStageFinal(Parent root, Stage stage, String fxmlFile) throws IOException { //00104923 inicialización método finalShow()
        root = FXMLLoader.load(getClass().getResource(fxmlFile)); //00104923 cargar FXML y asignar a root
        Scene nuevaEscena = new Scene(root); //00104923 crear nueva escena con root
        stage.setScene(nuevaEscena); //00104923 establecer nueva escena en el stage
        stage.centerOnScreen();
        stage.show(); //00104923 mostrar stage

        // Usar un PauseTransition para mover el enfoque después de que la escena se haya cargado
        PauseTransition pause = new PauseTransition(Duration.millis(10));
        pause.setOnFinished(event -> nuevaEscena.getRoot().requestFocus());
        pause.play();
    } //00104923 fin método finalShow

    @FXML
    private void volver(){
        try {
            Stage stage = (Stage) volverButton.getScene().getWindow(); //00104923 Obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00104923 Carga el archivo FXML del menú principal
            stage.setScene(new Scene(root)); //00104923 Establece la nueva escena
            stage.centerOnScreen(); //00104923 Centra la ventana en la pantalla
            stage.show(); //00104923 Muestra la ventana
        } catch (IOException e) { //00104923 Maneja excepciones IO
            e.printStackTrace(); //00104923 Imprime el stack trace de la excepción
        }
    }

}
