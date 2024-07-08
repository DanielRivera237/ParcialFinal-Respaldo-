package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    private Button regresarButton; //00104923 variable boton regresar a ventana del menu opciones administrador

    public void setConexion(Conexion conexion) { //00104923 método setConexion
        this.conexion = conexion; //00104923 asignación de la variable conexion
    } //00104923 fin método setConexion

    public void setPrimaryStage(Stage primaryStage) { //00104923 método setPrimaryStage
        this.primaryStage = primaryStage; //00104923 asignación de la variable primaryStage
    } //00104923 fin método setPrimaryStage

    @FXML
    public void showReporteA() throws IOException { //00104923 inicialización metodo mostrar ventana reporte A
        mostrarVentana("/com/example/conexiondebases/ReporteA.fxml", "Reporte A", 1); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteA

    @FXML
    private void showReporteB() throws IOException { //00104923 inicialización metodo mostrar ventana reporte B
        mostrarVentana("/com/example/conexiondebases/ReporteB.fxml", "Reporte B", 2); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteB

    @FXML
    private void showReporteC() throws IOException { //00104923 inicialización metodo mostrar ventana reporte C
        mostrarVentana( "/com/example/conexiondebases/ReporteC.fxml","Reporte C", 3); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteC

    @FXML
    private void showReporteD() throws IOException { //00104923 inicialización metodo mostrar ventana reporte D
        mostrarVentana("/com/example/conexiondebases/ReporteD.fxml", "Reporte D", 4); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteD

    private void mostrarVentana(String fxmlFile, String title, int type) throws IOException { //00104923 inicialización método showWindow()
        Stage stage = null; //00104923 variable tipo Stage
        Parent root = null; //00104923 variable tipo Parent
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();
        stage = (Stage) ((type == 1) ? reporteAButton.getScene().getWindow() :
                (type == 2) ? reporteBButton.getScene().getWindow() :
                        (type == 3) ? reporteCButton.getScene().getWindow() :
                                reporteDButton.getScene().getWindow());
        if (type == 4) {
            ReporteDController controller = loader.getController();
            if (controller != null) {
                controller.setConexion(conexion);
            }
        }
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();

        // Cerrar la ventana del menú principal
        if (primaryStage != null) { //00104923 condición para cerrar primaryStage
            primaryStage.close(); //00104923 cerrar primaryStage
        } //00104923 fin if
    } //00104923 fin método showWindow

    private void cargarStageFinal(Parent root, Stage stage, String fxmlFile) throws IOException { //00048722 inicialización método cargarStageFinal()
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile)); //00048722 cargar FXML y asignar a loader
        root = loader.load(); //00048722 cargar el root con el contenido del FXML
        stage.setScene(new Scene(root)); //00048722 establecer una nueva escena en el stage con el root cargado
    }
}
