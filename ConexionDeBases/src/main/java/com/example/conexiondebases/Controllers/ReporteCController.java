package com.example.conexiondebases.Controllers;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteCController {

    @FXML
    private TextField idClienteField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private TableView<ColumnaC> outPut;
    @FXML
    private TableColumn<ColumnaC, String> ColumCredito;
    @FXML
    private TableColumn<ColumnaC, String> ColumnDebito;
    @FXML
    private TableColumn<ColumnaC, String> ColumnID;
    @FXML
    private Button regresarButton;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());

        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        ColumCredito.setCellValueFactory(new PropertyValueFactory<>("tarjetaCredito"));
        ColumnDebito.setCellValueFactory(new PropertyValueFactory<>("tarjetaDebito"));
    }

    private void generarReporte() {
        String idCliente = idClienteField.getText();

        if (idCliente == null || idCliente.trim().isEmpty()) {
            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR AL INGRESAR DATOS");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos");

            alert.showAndWait();
            return;
        }

        ObservableList<ColumnaC> reporteC = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bancocentralnlogonia", "dsandmin", "greninja1207");
            String query = "SELECT c.id_cliente AS IDCliente, " +
                    "MAX(CASE WHEN t.tipo = 'Débito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaDebito, " +
                    "MAX(CASE WHEN t.tipo = 'Crédito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaCredito " +
                    "FROM Cliente c " +
                    "LEFT JOIN Tarjeta t ON c.id_cliente = t.id_cliente " +
                    "WHERE c.id_cliente = ? " +
                    "GROUP BY c.id_cliente";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, idCliente);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                // Mostrar alerta si el ID de cliente no se encuentra
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ID NO ENCONTRADO");
                alert.setHeaderText(null);
                alert.setContentText("No se encontró un cliente con el ID proporcionado.");

                alert.showAndWait();
                return;
            }

            // Obtener la fecha y hora actual para nombrar el archivo
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = now.format(formatter);

            // Crear un archivo TXT para guardar los resultados
            File archivo = new File("C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes\\Reporte_C_"+timestamp + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            while (rs.next()) {
                String id = rs.getString("IDCliente");
                String numeroCredito = rs.getString("TarjetaCredito");
                String numeroDebito = rs.getString("TarjetaDebito");

                // Crear una línea con todos los datos concatenados
                String linea = "ID Cliente: " + id +
                        ", Tarjeta de crédito: " + (numeroCredito != null ? numeroCredito : "N/A") +
                        ", Tarjeta de débito: " + (numeroDebito != null ? numeroDebito : "N/A");

                // Escribir la línea en el archivo
                writer.write(linea);
                writer.newLine(); // Añadir una nueva línea en blanco entre registros

                reporteC.add(new ColumnaC(id, numeroCredito, numeroDebito));
            }

            writer.close();
            rs.close();
            pstmt.close();
            conn.close();

            outPut.setItems(reporteC);

            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte Generado");
            alert.setHeaderText(null);
            alert.setContentText("El archivo TXT ha sido creado con éxito.");

            alert.showAndWait();

        } catch (Exception e) {
            System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage());
        }
    }



    @FXML
    public void regresarButton() {
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
