package com.example.conexiondebases.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;



public class ReporteAController {
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LABO", "labo13", "walle");
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

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, idCliente);
            pstmt.setDate(2, java.sql.Date.valueOf(fechaInicio));
            pstmt.setDate(3, java.sql.Date.valueOf(fechaFin));

            ResultSet rs = pstmt.executeQuery();

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
            }

            rs.close();  // Cerrar el ResultSet
            pstmt.close();  // Cerrar el PreparedStatement
            conn.close();  // Cerrar la conexión

            outputAreaField.setItems(reporteA);

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
