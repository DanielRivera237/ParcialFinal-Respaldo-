package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class ReporteDController {
    @FXML
    private TextField facilitadorField;
    @FXML
    private Button generarReporteButton;
    @FXML
    private Button regresarButton;
    @FXML
    private TableView<ReporteD> tableView;
    @FXML
    private TableColumn<ReporteD, String> clienteColumn;
    @FXML
    private TableColumn<ReporteD, Integer> comprasColumn;
    @FXML
    private TableColumn<ReporteD, BigDecimal> totalColumn;

    private Conexion conexion = Conexion.getInstancia("root", "tu_contraseña", "bancocentralnlogonia", "localhost", "3306");

    @FXML
    public void initialize() {
        generarReporteButton.setOnAction(e -> generarReporte());

        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        comprasColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalGastado"));
    }

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
        if (!this.conexion.connectionStablished()) {
            this.conexion.connect();
        }
    }

    @FXML
    private void generarReporte() {
        String facilitador = facilitadorField.getText();
        String query = "SELECT\n" +
                "    CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente,\n" +
                "    COUNT(Transaccion.id_transaccion) AS cantidad_compras,\n" +
                "    SUM(Transaccion.total_gastado) AS total_gastado\n" +
                "FROM\n" +
                "    Cliente\n" +
                "INNER JOIN\n" +
                "    Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente\n" +
                "INNER JOIN\n" +
                "    Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta\n" +
                "WHERE\n" +
                "    Tarjeta.facilitador = ?" +
                "GROUP BY Cliente.nombres, Cliente.apellidos";

        if (facilitador == null || facilitador.isEmpty()) {
            mostrarAlerta("Error", "Datos faltantes", "Por favor ingrese un facilitador.");
            return;
        }

        ObservableList<ReporteD> reporteDList = FXCollections.observableArrayList();
        try {
            PreparedStatement pstmt = conexion.getConnection().prepareStatement(query);
            pstmt.setString(1, facilitador);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ReporteD reporteD = new ReporteD(
                        rs.getString("cliente"),
                        rs.getInt("cantidad_compras"),
                        rs.getBigDecimal("total_gastado")
                );

                reporteDList.add(reporteD);
            }
            rs.close();
            pstmt.close();

            tableView.setItems(reporteDList);

        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al obtener reporte", "Hubo un error al obtener el reporte desde la base de datos.");
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
            mostrarAlerta("Error", "Error al regresar", "Hubo un error al intentar regresar al menú principal.");
        }
    }

    private void mostrarAlerta(String titulo, String encabezado, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

