package com.example.conexiondebases.Controllers; //00104923 declaración del paquete

import javafx.fxml.FXML; //00104923 importación de la clase FXML
import javafx.fxml.FXMLLoader; //00104923 importación de la clase FXMLLoader
import javafx.scene.Parent; //00104923 importación de la clase Parent
import javafx.scene.Scene; //00104923 importación de la clase Scene
import javafx.scene.control.Button; //00104923 importación de la clase Button
import javafx.scene.control.TextField; //00104923 importación de la clase TextField
import javafx.scene.control.TextArea; //00104923 importación de la clase TextArea
import javafx.stage.Stage; //00104923 importación de la clase Stage

import java.io.IOException; //00104923 importación de la clase IOException

public class ReporteDController { //00104923 declaración de la clase ReporteDController
    @FXML
    private TextField facilitadorField; //00104923 variable campo de texto facilitador
    @FXML
    private Button generarReporteButton; //00104923 variable botón generar reporte
    @FXML
    private TextArea outputArea; //00104923 variable área de texto de salida
    @FXML
    private Button regresarButton; //00104923 variable botón regresar

    @FXML
    public void initialize() { //00104923 inicialización del método initialize()
        generarReporteButton.setOnAction(e -> generarReporte()); //00104923 establecer acción del botón generar reporte
    } //00104923 fin método initialize

    private void generarReporte() { //00104923 inicialización del método generarReporte
        outputArea.appendText("Generando Reporte D para facilitador: " + facilitadorField.getText() + "\n"); //00104923 generar mensaje de reporte
    } //00104923 fin método generarReporte

    @FXML
    public void regresarButton() { //00104923 inicialización del método regresarButton
        try { //00104923 inicio del bloque try
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00104923 obtener la ventana actual del botón regresar
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00104923 cargar FXML y asignar a root
            stage.setScene(new Scene(root)); //00104923 establecer nueva escena en el stage
            stage.show(); //00104923 mostrar stage
        } catch (IOException e) { //00104923 captura de excepción IOException
            e.printStackTrace(); //00104923 imprimir traza de la excepción
        } //00104923 fin del bloque catch
    } //00104923 fin método regresarButton
} //00104923 fin clase ReporteDController
