package com.example.conexiondebases.Controllers; //00104923 declaración del paquete

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML; //00104923 importación de la clase FXML
import javafx.fxml.FXMLLoader; //00104923 importación de la clase FXMLLoader
import javafx.scene.Parent; //00104923 importación de la clase Parent
import javafx.scene.Scene; //00104923 importación de la clase Scene
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage; //00104923 importación de la clase Stage

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException; //00104923 importación de la clase IOException
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteBController { //00104923 declaración de la clase ReporteBController
    @FXML
    private TextField idClienteField; //00104923 variable campo de texto ID cliente
    @FXML
    private TextField mesField; //00104923 variable campo de texto mes
    @FXML
    private TextField anioField; //00104923 variable campo de texto año
    @FXML
    private Button generarReporteButton; //00104923 variable botón generar reporte
    @FXML
    private TableView<ColumnasB> contArea;
    @FXML
    private Button regresarButton; //00104923 variable botón regresar
    @FXML
    private TableColumn<ColumnasB, String> columnIdCliente;
    @FXML
    private TableColumn<ColumnasB, Double> columnGastos;

    @FXML
    public void initialize() { //00104923 inicialización del método initialize()
        generarReporteButton.setOnAction(e -> generarReporte()); //00104923 establecer acción del botón generar reporte

        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        columnGastos.setCellValueFactory(new PropertyValueFactory<>("montoMes"));
    } //00104923 fin método initialize

    private void generarReporte() {
        String idCliente = idClienteField.getText();
        String mes = mesField.getText();
        String anio = anioField.getText();

        if (idCliente == null || idCliente.isEmpty() || anio.isEmpty() || mes.isEmpty()) {
            // Mostrar alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR AL INGRESAR DATOS");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos");

            alert.showAndWait();
            return;
        }

        ObservableList<ColumnasB> reporteB = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bancocentralnlogonia", "dsandmin", "greninja1207");
            String query = "SELECT " +
                    "c.id_cliente, " +
                    "SUM(t.total_gastado) AS GASTOS_MES " +
                    "FROM Transaccion t " +
                    "JOIN Tarjeta ta ON t.numero_tarjeta = ta.numero " +
                    "JOIN Cliente c ON ta.id_cliente = c.id_cliente " +
                    "WHERE c.id_cliente = ? " +
                    "AND EXTRACT(MONTH FROM t.fecha_compra) = ? " +
                    "AND EXTRACT(YEAR FROM t.fecha_compra) = ? " +
                    "GROUP BY c.id_cliente";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, idCliente);
            pstmt.setString(2, mes);
            pstmt.setString(3, anio);

            ResultSet rs = pstmt.executeQuery();

            // Verificar si se encontraron resultados
            if (!rs.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("SIN RESULTADOS");
                alert.setHeaderText(null);
                alert.setContentText("No se encontraron registros en la base de datos.");
                alert.showAndWait();
                return;
            }

            // Obtener la fecha y hora actual para nombrar el archivo
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String timestamp = now.format(formatter);

            // Crear un archivo TXT para guardar los resultados
            File archivo = new File("C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes\\Reporte_B_"+timestamp + ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            while (rs.next()) {
                ColumnasB vistaColumna = new ColumnasB(
                        rs.getString("id_cliente"),
                        rs.getDouble("GASTOS_MES")
                );
                reporteB.add(vistaColumna);

                // Escribir los resultados en el archivo TXT
                writer.write("ID Cliente: " + rs.getString("id_cliente") + "| Gastos del Mes del Cliente: " + rs.getDouble("GASTOS_MES"));
                writer.newLine();
            }

            writer.close(); // Cerrar el BufferedWriter
            rs.close();  // Cerrar el ResultSet
            pstmt.close();  // Cerrar el PreparedStatement
            conn.close();  // Cerrar la conexión

            contArea.setItems(reporteB);

            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("REPORTE GENERADO");
            alert.setHeaderText(null);
            alert.setContentText("El archivo de reporte se ha creado con éxito.");
            alert.showAndWait();

        } catch (SQLException e) {
            System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR DE CONEXIÓN");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar a la base de datos.");
            alert.showAndWait();
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR DE ARCHIVO");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo crear el archivo de reporte.");
            alert.showAndWait();
        }
    }

    @FXML
    public void regresarButton() { //00104923 inicialización del método regresarButton
        try { //00104923 inicio del bloque try
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00104923 obtener la ventana actual del botón regresar
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00104923 cargar FXML y asignar a root
            stage.setScene(new Scene(root)); //00104923 establecer nueva escena en el stage
            stage.centerOnScreen();
            stage.show(); //00104923 mostrar stage
        } catch (IOException e) { //00104923 captura de excepción IOException
            e.printStackTrace(); //00104923 imprimir traza de la excepción
        } //00104923 fin del bloque catch
    } //00104923 fin método regresarButton
} //00104923 fin clase ReporteBController
