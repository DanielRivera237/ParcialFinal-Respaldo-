package com.example.conexiondebases; //00104923 declaración del paquete

import javafx.fxml.FXML; //00104923 importación de la clase FXML
import javafx.fxml.FXMLLoader; //00104923 importación de la clase FXMLLoader
import javafx.scene.Parent; //00104923 importación de la clase Parent
import javafx.scene.Scene; //00104923 importación de la clase Scene
import javafx.scene.control.Button; //00104923 importación de la clase Button
import javafx.stage.Stage; //00104923 importación de la clase Stage
import java.io.IOException; //00104923 importación de la clase IOException


public class MainMenuController { //00104923 declaración de la clase MainMenuController
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
        mostrarVentana("ReporteA.fxml", "Reporte A", 1); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteA

    @FXML
    private void showReporteB() throws IOException { //00104923 inicialización metodo mostrar ventana reporte B
        mostrarVentana("ReporteB.fxml", "Reporte B", 2); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteB

    @FXML
    private void showReporteC() throws IOException { //00104923 inicialización metodo mostrar ventana reporte C
        mostrarVentana("ReporteC.fxml", "Reporte C", 3); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteC

    @FXML
    private void showReporteD() throws IOException { //00104923 inicialización metodo mostrar ventana reporte D
        mostrarVentana("ReporteD.fxml", "Reporte D", 4); //00104923 inserto los parametros que necesita el método showWindow()
    } //00104923 fin método showReporteD

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
        } //00104923 fin switch
        // Cerrar la ventana del menú principal
        if (primaryStage != null) { //00104923 condición para cerrar primaryStage
            primaryStage.close(); //00104923 cerrar primaryStage
        } //00104923 fin if
    } //00104923 fin método showWindow

    private void cargarStageFinal(Parent root, Stage stage, String fxmlFile) throws IOException { //00104923 inicialización método finalShow()
        root = FXMLLoader.load(getClass().getResource(fxmlFile)); //00104923 cargar FXML y asignar a root
        stage.setScene(new Scene(root)); //00104923 establecer nueva escena en el stage
        stage.show(); //00104923 mostrar stage
    } //00104923 fin método finalShow
} //00104923 fin clase MainMenuController
