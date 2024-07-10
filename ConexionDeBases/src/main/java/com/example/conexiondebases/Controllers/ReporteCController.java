package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.Conexion;
import com.example.conexiondebases.Clases_auxiliares.Reportes.ColumnaC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteCController {// 00083223 declaracion de la clase ReporteCController
    private Conexion conexion = Conexion.getInstancia("root", "tu_contraseña", "gestionbcn", "localhost", "3306");
    @FXML
    private TextField idClienteField; //00083223 captura texto para ingresar el id del cliente
    @FXML
    private Button generarReporteButton; //00083223 botón para generar el reporte
    @FXML
    private TableView<ColumnaC> outPut; //00083223 tabla para mostrar el resultado del reporte
    @FXML
    private TableColumn<ColumnaC, String> ColumCredito; //00083223 columna para mostrar las tarjetas de crédito
    @FXML
    private TableColumn<ColumnaC, String> ColumnDebito; //00083223 columna para mostrar las tarjetas de débito
    @FXML
    private TableColumn<ColumnaC, String> ColumnID; //00083223 columna para mostrar el ID del cliente
    @FXML
    private Button regresarButton; //00083223 botón para regresar al menú anterior

    @FXML
    public void initialize() {// 00083223 inicializa el metodo initialize()
        generarReporteButton.setOnAction(e -> generarReporte()); //00083223 esto sirve como configuracion para la acción del botón para generar el reporte

        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idCliente")); //00083223 configura la columna id
        ColumCredito.setCellValueFactory(new PropertyValueFactory<>("tarjetaCredito")); //00083223 configura la columna de crédito
        ColumnDebito.setCellValueFactory(new PropertyValueFactory<>("tarjetaDebito")); //00083223 configura la columna de débito
    }// 00083223fin del metodo initialize()

    private void generarReporte() {
        if(conexion.connect()){
            String idCliente = idClienteField.getText();

            if (idCliente == null || idCliente.trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR AL INGRESAR DATOS");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, complete todos los campos");
                alert.showAndWait();
                return;
            }

            ObservableList<ColumnaC> reporteC = FXCollections.observableArrayList();
            try {
                String query = "SELECT c.id_cliente AS IDCliente, " +
                        "MAX(CASE WHEN t.tipo = 'Débito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaDebito, " +
                        "MAX(CASE WHEN t.tipo = 'Crédito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaCredito " +
                        "FROM Cliente c " +
                        "LEFT JOIN Tarjeta t ON c.id_cliente = t.id_cliente " +
                        "WHERE c.id_cliente = ? " +
                        "GROUP BY c.id_cliente";

                PreparedStatement pstmt = conexion.getConnection().prepareStatement(query);
                pstmt.setString(1, idCliente);

                ResultSet rs = pstmt.executeQuery();

                if (!rs.isBeforeFirst()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ID NO ENCONTRADO");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró un cliente con el ID proporcionado.");
                    alert.showAndWait();
                    return;
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String timestamp = now.format(formatter);

                // Cambiar la ruta del archivo
                File archivo = new File("ConexionDeBases/Reportes/Reporte_C_" + timestamp + ".txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

                while (rs.next()) {
                    String id = rs.getString("IDCliente");
                    String numeroCredito = rs.getString("TarjetaCredito");
                    String numeroDebito = rs.getString("TarjetaDebito");

                    String linea = "ID Cliente: " + id + ", Tarjeta de crédito: " + (numeroCredito != null ? numeroCredito : "N/A") + ", Tarjeta de débito: " + (numeroDebito != null ? numeroDebito : "N/A");

                    writer.write(linea);
                    writer.newLine();

                    reporteC.add(new ColumnaC(id, numeroCredito, numeroDebito));
                }

                writer.close();
                rs.close();
                pstmt.close();
                conexion.getConnection().close();

                outPut.setItems(reporteC);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reporte Generado");
                alert.setHeaderText(null);
                alert.setContentText("El archivo TXT ha sido creado con éxito.");
                alert.showAndWait();

            } catch (Exception e) {
                System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage());
            }
        }
    }


    @FXML
    public void regresarButton() {//00083223 incio del metodo regresarButton()
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00083223 obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/opciones-administrador.fxml")); //00083223 Carga la vista del menú principal
            stage.setScene(new Scene(root)); //00083223 aqui establece la escena del menú principal en la ventana
            stage.centerOnScreen(); //00083223 esto centra la ventana en la pantalla
            stage.show(); //00083223 muestra la ventana
        } catch (IOException e) { //00083223 captura de excepción IOException
            e.printStackTrace(); //00083223 imprime la traza de la excepción si ocurre un error al cargar la vista
        }//00083223 fin del bloque catch
    }// 00083223 fin del metodo regresarButton()
}//00083223 fin de la clase ReporteCController

