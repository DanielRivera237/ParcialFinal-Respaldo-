package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.Conexion;
import com.example.conexiondebases.Clases_auxiliares.Reportes.ColumnasA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;


public class ReporteAController {
    private Conexion conexion = Conexion.getInstancia("root", "tu_contraseña", "gestionbcn", "localhost", "3306");
    @FXML
    private TextField idClienteField;
    @FXML
    private DatePicker fechaInicioField;
    @FXML
    private DatePicker fechaFinField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private TableView<ColumnasA> outputAreaField;
    @FXML
    private TableColumn<ColumnasA, String> ColumnID;
    @FXML
    private TableColumn<ColumnasA, String> ColumnNombres;
    @FXML
    private TableColumn<ColumnasA, String> ColumnApellidos;
    @FXML
    private TableColumn<ColumnasA, Double> ColumnMonto;
    @FXML
    private TableColumn<ColumnasA, Integer> ColumnIDTransaccion;
    @FXML
    private TableColumn<ColumnasA, String> ColumnDescripcion;
    @FXML
    private Button regresarButton;

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());

        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        ColumnNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        ColumnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        ColumnIDTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion"));
        ColumnMonto.setCellValueFactory(new PropertyValueFactory<>("Monto"));
        ColumnDescripcion.setCellValueFactory((new PropertyValueFactory<>("descripcion")));
    }

    private void generarReporte() {
        if(conexion.connect()){
            String idCliente = idClienteField.getText();
            LocalDate fechaInicio = fechaInicioField.getValue();
            LocalDate fechaFin = fechaFinField.getValue();

            if (idCliente == null || idCliente.isEmpty() || fechaInicio == null || fechaFin == null) {
                // Mostrar alerta de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR AL INGRESAR DATOS");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, complete todos los campos");

                alert.showAndWait();
                return;
            }

            ObservableList<ColumnasA> reporteA = FXCollections.observableArrayList();

            try {
                String query = "SELECT " +
                        "c.id_cliente, " +
                        "c.nombres, " +
                        "c.apellidos, " +
                        "t.id_transaccion, "+
                        "t.total_gastado, " +
                        "t.descripcion_compra "+
                        "FROM Transaccion t " +
                        "JOIN Tarjeta ta ON t.numero_tarjeta = ta.numero " +
                        "JOIN Cliente c ON ta.id_cliente = c.id_cliente " +
                        "WHERE c.id_cliente = ? " +
                        "AND t.fecha_compra BETWEEN ? AND ?";

                PreparedStatement pstmt = conexion.getConnection().prepareStatement(query);
                pstmt.setString(1, idCliente);
                pstmt.setDate(2, java.sql.Date.valueOf(fechaInicio));
                pstmt.setDate(3, java.sql.Date.valueOf(fechaFin));

                ResultSet rs = pstmt.executeQuery();

                // Obtener la fecha y hora actual para nombrar el archivo
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String timestamp = now.format(formatter);

                // Crear un archivo TXT para guardar los resultados
                File archivo = new File("ConexionDeBases/Reportes/Reporte_A_" +timestamp + ".txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

                while (rs.next()) {
                    ColumnasA vistaColumna = new ColumnasA(
                            rs.getString("id_cliente"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getInt("id_transaccion"),
                            rs.getDouble("total_gastado"),
                            rs.getString("descripcion_compra")
                    );
                    reporteA.add(vistaColumna);

                    // Escribir los resultados en el archivo TXT
                    writer.write("ID Cliente: " + rs.getString("id_cliente") +
                            ", Nombres: " + rs.getString("nombres") +
                            ", Apellidos: " + rs.getString("apellidos") +
                            ", ID Transacción: " + rs.getInt("id_transaccion") +
                            ", Total Gastado: " + rs.getDouble("total_gastado") +
                            ", Descripción: " + rs.getString("descripcion_compra"));
                    writer.newLine();
                }

                writer.close(); // Cerrar el BufferedWriter
                rs.close();  // Cerrar el ResultSet
                pstmt.close();  // Cerrar el PreparedStatement
                conexion.getConnection().close();  // Cerrar la conexión

                outputAreaField.setItems(reporteA);

            } catch (Exception e) {
                System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage());
            }
        }
    }


    @FXML
    public void regresarButton() {
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/opciones-administrador.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
