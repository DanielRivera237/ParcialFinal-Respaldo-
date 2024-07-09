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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @FXML
    private Label totalLabel;

    private Conexion conexion = Conexion.getInstancia("root", "tu_contraseña", "gestionbcn", "localhost", "3306");

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
        String queryTodos = "SELECT " +
                "CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente, " +
                "COUNT(Transaccion.id_transaccion) AS cantidad_compras, " +
                "COALESCE(SUM(Transaccion.total_gastado), 0) AS total_gastado " +
                "FROM Cliente " +
                "INNER JOIN Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente " +
                "LEFT JOIN Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta " +
                "WHERE Tarjeta.facilitador = ? " +
                "GROUP BY Cliente.nombres, Cliente.apellidos";

        String queryConGasto = "SELECT " +
                "CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente, " +
                "COUNT(Transaccion.id_transaccion) AS cantidad_compras, " +
                "SUM(Transaccion.total_gastado) AS total_gastado " +
                "FROM Cliente " +
                "INNER JOIN Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente " +
                "INNER JOIN Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta " +
                "WHERE Tarjeta.facilitador = ? " +
                "GROUP BY Cliente.nombres, Cliente.apellidos " +
                "HAVING SUM(Transaccion.total_gastado) > 0";

        if (facilitador == null || facilitador.isEmpty()) {
            mostrarAlerta("Error", "Datos faltantes", "Por favor ingrese un facilitador.", Alert.AlertType.ERROR);
            return;
        }

        ObservableList<ReporteD> reporteDList = FXCollections.observableArrayList();
        ObservableList<ReporteD> reporteDListConGasto = FXCollections.observableArrayList();
        BigDecimal sumaTotal = BigDecimal.ZERO;

        try {
            if (!conexion.connectionStablished()) {
                mostrarAlerta("Error", "Error de conexión", "No se pudo establecer conexión con la base de datos.", Alert.AlertType.ERROR);
                return;
            }

            // Obtener todos los clientes con el facilitador
            PreparedStatement pstmt = conexion.getConnection().prepareStatement(queryTodos);
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

            // Obtener solo los clientes con gastos
            pstmt = conexion.getConnection().prepareStatement(queryConGasto);
            pstmt.setString(1, facilitador);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ReporteD reporteD = new ReporteD(
                        rs.getString("cliente"),
                        rs.getInt("cantidad_compras"),
                        rs.getBigDecimal("total_gastado")
                );
                sumaTotal = sumaTotal.add(rs.getBigDecimal("total_gastado"));
                reporteDListConGasto.add(reporteD);
            }
            rs.close();
            pstmt.close();

            tableView.setItems(reporteDList);
            totalLabel.setText("Total Gastado: " + sumaTotal); // Mostrar la suma total en el Label

            // Guardar el reporte en un archivo de texto
            guardarReporteEnArchivo(reporteDListConGasto, facilitador, sumaTotal);

        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al obtener reporte", "Hubo un error al obtener el reporte desde la base de datos.\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void guardarReporteEnArchivo(ObservableList<ReporteD> reporteDList, String facilitador, BigDecimal sumaTotal) {
        try {
            // Crear el directorio "Reportes" si no existe en la ruta del proyecto
            String reportesDir = "src/main/java/com/example/conexiondebases/Reportes";
            Path path = Paths.get(reportesDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Formatear la fecha y hora actual para el nombre del archivo
            String fechaHora = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String nombreArchivo = reportesDir + "/Reporte_" + facilitador + "_" + fechaHora + ".txt";

            // Crear el archivo y escribir el contenido del reporte
            FileWriter writer = new FileWriter(nombreArchivo);
            writer.write("Reporte para facilitador: " + facilitador + "\n\n");
            writer.write(String.format("%-30s %-20s %-20s\n", "Cliente", "Cantidad de Compras", "Total Gastado"));
            writer.write("------------------------------------------------------------\n");

            for (ReporteD reporteD : reporteDList) {
                writer.write(String.format("%-30s %-20d %-20.2f\n",
                        reporteD.getCliente(),
                        reporteD.getCantidadCompras(),
                        reporteD.getTotalGastado().doubleValue()));
            }

            // Escribir la suma total al final del archivo
            writer.write("\nSuma Total: " + sumaTotal + "\n");

            writer.close();
            mostrarAlerta("Éxito", "Reporte guardado", "El reporte ha sido guardado en " + nombreArchivo, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            mostrarAlerta("Error", "Error al guardar reporte", "Hubo un error al guardar el reporte.\n" + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
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
            mostrarAlerta("Error", "Error al regresar", "Hubo un error al intentar regresar al menú principal.", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String encabezado, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
