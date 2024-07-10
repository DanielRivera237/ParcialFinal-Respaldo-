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

public class ReporteDController { // 00048722: Declara la clase pública ReporteDController
    @FXML // 00048722: Anotación FXML para inyección del campo facilitadorField
    private TextField facilitadorField; // 00048722: Declara un campo privado de tipo TextField llamado facilitadorField
    @FXML // 00048722: Anotación FXML para inyección del campo generarReporteButton
    private Button generarReporteButton; // 00048722: Declara un campo privado de tipo Button llamado generarReporteButton
    @FXML // 00048722: Anotación FXML para inyección del campo regresarButton
    private Button regresarButton; // 00048722: Declara un campo privado de tipo Button llamado regresarButton
    @FXML // 00048722: Anotación FXML para inyección del campo tableView
    private TableView<ReporteD> tableView; // 00048722: Declara un campo privado de tipo TableView llamado tableView
    @FXML // 00048722: Anotación FXML para inyección del campo clienteColumn
    private TableColumn<ReporteD, String> clienteColumn; // 00048722: Declara un campo privado de tipo TableColumn llamado clienteColumn
    @FXML // 00048722: Anotación FXML para inyección del campo comprasColumn
    private TableColumn<ReporteD, Integer> comprasColumn; // 00048722: Declara un campo privado de tipo TableColumn llamado comprasColumn
    @FXML // 00048722: Anotación FXML para inyección del campo totalColumn
    private TableColumn<ReporteD, BigDecimal> totalColumn; // 00048722: Declara un campo privado de tipo TableColumn llamado totalColumn
    @FXML // 00048722: Anotación FXML para inyección del campo totalLabel
    private Label totalLabel; // 00048722: Declara un campo privado de tipo Label llamado totalLabel

    private Conexion conexion = Conexion.getInstancia("dsandmin", "greninja1207", "bancocentralnlogonia", "localhost", "3306"); // 00048722: Inicializa el campo conexion con una instancia de Conexion

    @FXML // 00048722: Anotación FXML para el método initialize
    public void initialize() { // 00048722: Método initialize que se ejecuta al cargar el FXML
        generarReporteButton.setOnAction(e -> generarReporte()); // 00048722: Establece una acción para el botón generarReporteButton que llama al método generarReporte

        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente")); // 00048722: Establece la fábrica de valores de celda para la columna clienteColumn
        comprasColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras")); // 00048722: Establece la fábrica de valores de celda para la columna comprasColumn
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalGastado")); // 00048722: Establece la fábrica de valores de celda para la columna totalColumn
    } // 00048722: Cierra el método initialize

    public void setConexion(Conexion conexion) { // 00048722: Método para establecer la conexión
        this.conexion = conexion; // 00048722: Asigna el parámetro conexion al campo de instancia conexion
        if (!this.conexion.connectionStablished()) { // 00048722: Verifica si la conexión no está establecida
            this.conexion.connect(); // 00048722: Si no está establecida, intenta conectar
        }
    } // 00048722: Cierra el método setConexion

    @FXML // 00048722: Anotación FXML para el método generarReporte
    private void generarReporte() { // 00048722: Método privado generarReporte
        String facilitador = facilitadorField.getText(); // 00048722: Obtiene el texto del campo facilitadorField y lo asigna a la variable facilitador
        String queryTodos = "SELECT " + // 00048722: Consulta SQL para obtener todos los clientes con el facilitador
                "CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente, " + // 00048722: Concatena nombres y apellidos del cliente
                "COUNT(Transaccion.id_transaccion) AS cantidad_compras, " + // 00048722: Cuenta la cantidad de transacciones
                "COALESCE(SUM(Transaccion.total_gastado), 0) AS total_gastado " + // 00048722: Suma el total gastado, o 0 si es nulo
                "FROM Cliente " + // 00048722: Desde la tabla Cliente
                "INNER JOIN Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente " + // 00048722: Uniendo con la tabla Tarjeta
                "LEFT JOIN Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta " + // 00048722: Uniendo con la tabla Transaccion
                "WHERE Tarjeta.facilitador = ? " + // 00048722: Donde el facilitador coincide con el parámetro
                "GROUP BY Cliente.nombres, Cliente.apellidos"; // 00048722: Agrupa por nombres y apellidos del cliente

        String queryConGasto = "SELECT " + // 00048722: Consulta SQL para obtener solo los clientes con gastos
                "CONCAT(Cliente.nombres, ' ', Cliente.apellidos) AS cliente, " + // 00048722: Concatena nombres y apellidos del cliente
                "COUNT(Transaccion.id_transaccion) AS cantidad_compras, " + // 00048722: Cuenta la cantidad de transacciones
                "SUM(Transaccion.total_gastado) AS total_gastado " + // 00048722: Suma el total gastado
                "FROM Cliente " + // 00048722: Desde la tabla Cliente
                "INNER JOIN Tarjeta ON Cliente.id_cliente = Tarjeta.id_cliente " + // 00048722: Uniendo con la tabla Tarjeta
                "INNER JOIN Transaccion ON Tarjeta.numero = Transaccion.numero_tarjeta " + // 00048722: Uniendo con la tabla Transaccion
                "WHERE Tarjeta.facilitador = ? " + // 00048722: Donde el facilitador coincide con el parámetro
                "GROUP BY Cliente.nombres, Cliente.apellidos " + // 00048722: Agrupa por nombres y apellidos del cliente
                "HAVING SUM(Transaccion.total_gastado) > 0"; // 00048722: Solo incluye grupos con gastos mayores a 0

        if (facilitador == null || facilitador.isEmpty()) { // 00048722: Verifica si el facilitador es nulo o está vacío
            mostrarAlerta("Error", "Datos faltantes", "Por favor ingrese un facilitador.", Alert.AlertType.ERROR); // 00048722: Muestra una alerta de error
            return; // 00048722: Sale del método
        }

        ObservableList<ReporteD> reporteDList = FXCollections.observableArrayList(); // 00048722: Crea una lista observable para almacenar los reportes
        ObservableList<ReporteD> reporteDListConGasto = FXCollections.observableArrayList(); // 00048722: Crea una lista observable para almacenar los reportes con gasto
        BigDecimal sumaTotal = BigDecimal.ZERO; // 00048722: Inicializa sumaTotal a cero

        try { // 00048722: Inicia un bloque try-catch para manejar excepciones
            if (!conexion.connectionStablished()) { // 00048722: Verifica si la conexión no está establecida
                mostrarAlerta("Error", "Error de conexión", "No se pudo establecer conexión con la base de datos.", Alert.AlertType.ERROR); // 00048722: Muestra una alerta de error
                return; // 00048722: Sale del método
            }

            // 00048722: Obtener todos los clientes con el facilitador
            PreparedStatement pstmt = conexion.getConnection().prepareStatement(queryTodos); // 00048722: Prepara la consulta SQL queryTodos
            pstmt.setString(1, facilitador); // 00048722: Establece el parámetro facilitador en la consulta
            ResultSet rs = pstmt.executeQuery(); // 00048722: Ejecuta la consulta y obtiene el resultado
            while (rs.next()) { // 00048722: Itera sobre los resultados
                ReporteD reporteD = new ReporteD( // 00048722: Crea una nueva instancia de ReporteD con los datos del resultado
                        rs.getString("cliente"), // 00048722: Obtiene el valor de la columna cliente
                        rs.getInt("cantidad_compras"), // 00048722: Obtiene el valor de la columna cantidad_compras
                        rs.getBigDecimal("total_gastado") // 00048722: Obtiene el valor de la columna total_gastado
                );
                reporteDList.add(reporteD); // 00048722: Agrega el reporteD a la lista reporteDList
            }
            rs.close(); // 00048722: Cierra el ResultSet
            pstmt.close(); // 00048722: Cierra el PreparedStatement

            // 00048722: Obtener solo los clientes con gastos
            pstmt = conexion.getConnection().prepareStatement(queryConGasto); // 00048722: Prepara la consulta SQL queryConGasto
            pstmt.setString(1, facilitador); // 00048722: Establece el parámetro facilitador en la consulta
            rs = pstmt.executeQuery(); // 00048722: Ejecuta la consulta y obtiene el resultado
            while (rs.next()) { // 00048722: Itera sobre los resultados
                ReporteD reporteD = new ReporteD( // 00048722: Crea una nueva instancia de ReporteD con los datos del resultado
                        rs.getString("cliente"), // 00048722: Obtiene el valor de la columna cliente
                        rs.getInt("cantidad_compras"), // 00048722: Obtiene el valor de la columna cantidad_compras
                        rs.getBigDecimal("total_gastado") // 00048722: Obtiene el valor de la columna total_gastado
                );
                sumaTotal = sumaTotal.add(rs.getBigDecimal("total_gastado")); // 00048722: Suma el total gastado al sumaTotal
                reporteDListConGasto.add(reporteD); // 00048722: Agrega el reporteD a la lista reporteDListConGasto
            }
            rs.close(); // 00048722: Cierra el ResultSet
            pstmt.close(); // 00048722: Cierra el PreparedStatement

            tableView.setItems(reporteDList); // 00048722: Establece los elementos de la tabla con la lista reporteDList
            totalLabel.setText("Total Gastado: " + sumaTotal); // 00048722: Muestra la suma total en el Label totalLabel

            // 00048722: Guardar el reporte en un archivo de texto
            guardarReporteEnArchivo(reporteDListConGasto, facilitador, sumaTotal); // 00048722: Llama al método guardarReporteEnArchivo con los parámetros correspondientes

        } catch (SQLException e) { // 00048722: Captura las excepciones SQL
            mostrarAlerta("Error", "Error al obtener reporte", "Hubo un error al obtener el reporte desde la base de datos.\n" + e.getMessage(), Alert.AlertType.ERROR); // 00048722: Muestra una alerta de error con el mensaje de la excepción
            e.printStackTrace(); // 00048722: Imprime la traza de la excepción
        }
    } // 00048722: Cierra el método generarReporte

    private void guardarReporteEnArchivo(ObservableList<ReporteD> reporteDList, String facilitador, BigDecimal sumaTotal) { // 00048722: Método privado para guardar el reporte en un archivo
        try { // 00048722: Inicia un bloque try-catch para manejar excepciones
            // 00048722: Crear el directorio "Reportes" si no existe en la ruta del proyecto
            String reportesDir = "C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes"; // 00048722: Define la ruta del directorio de reportes
            Path path = Paths.get(reportesDir); // 00048722: Crea un objeto Path para la ruta del directorio
            if (!Files.exists(path)) { // 00048722: Verifica si el directorio no existe
                Files.createDirectories(path); // 00048722: Crea el directorio si no existe
            }

            // 00048722: Formatear la fecha y hora actual para el nombre del archivo
            String fechaHora = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()); // 00048722: Formatea la fecha y hora actual
            String nombreArchivo = reportesDir + "\\Reporte_D_" + facilitador + "_" + fechaHora + ".txt"; // 00048722: Define el nombre del archivo

            // 00048722: Crear el archivo y escribir el contenido del reporte
            FileWriter writer = new FileWriter(nombreArchivo); // 00048722: Crea un FileWriter para el archivo
            writer.write("Reporte para facilitador: " + facilitador + "\n\n"); // 00048722: Escribe el encabezado del reporte
            writer.write(String.format("%-30s %-20s %-20s\n", "Cliente", "Cantidad de Compras", "Total Gastado")); // 00048722: Escribe las columnas del reporte
            writer.write("------------------------------------------------------------\n"); // 00048722: Escribe una línea separadora

            for (ReporteD reporteD : reporteDList) { // 00048722: Itera sobre la lista de reportes
                writer.write(String.format("%-30s %-20d %-20.2f\n", // 00048722: Escribe los datos de cada reporte
                        reporteD.getCliente(), // 00048722: Obtiene el cliente del reporte
                        reporteD.getCantidadCompras(), // 00048722: Obtiene la cantidad de compras del reporte
                        reporteD.getTotalGastado().doubleValue())); // 00048722: Obtiene el total gastado del reporte
            }

            // 00048722: Escribir la suma total al final del archivo
            writer.write("\nSuma Total: " + sumaTotal + "\n"); // 00048722: Escribe la suma total en el archivo

            writer.close(); // 00048722: Cierra el FileWriter
            mostrarAlerta("Éxito", "Reporte guardado", "El reporte ha sido guardado en " + nombreArchivo, Alert.AlertType.INFORMATION); // 00048722: Muestra una alerta de éxito indicando la ubicación del archivo guardado
        } catch (IOException e) { // 00048722: Captura las excepciones de entrada/salida
            mostrarAlerta("Error", "Error al guardar reporte", "Hubo un error al guardar el reporte.\n" + e.getMessage(), Alert.AlertType.ERROR); // 00048722: Muestra una alerta de error con el mensaje de la excepción
            e.printStackTrace(); // 00048722: Imprime la traza de la excepción
        }
    } // 00048722: Cierra el método guardarReporteEnArchivo

    @FXML // 00048722: Anotación FXML para el método regresarButton
    public void regresarButton() { // 00048722: Método público regresarButton
        try { // 00048722: Inicia un bloque try-catch para manejar excepciones
            Stage stage = (Stage) regresarButton.getScene().getWindow(); // 00048722: Obtiene la ventana actual del botón regresarButton
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); // 00048722: Carga el archivo FXML del menú principal
            stage.setScene(new Scene(root)); // 00048722: Establece la nueva escena en la ventana actual
            stage.centerOnScreen(); // 00048722: Centra la ventana en la pantalla
            stage.show(); // 00048722: Muestra la ventana
        } catch (IOException e) { // 00048722: Captura las excepciones de entrada/salida
            e.printStackTrace(); // 00048722: Imprime la traza de la excepción
            mostrarAlerta("Error", "Error al regresar", "Hubo un error al intentar regresar al menú principal.", Alert.AlertType.ERROR); // 00048722: Muestra una alerta de error indicando que hubo un problema al regresar
        }
    } // 00048722: Cierra el método regresarButton

    private void mostrarAlerta(String titulo, String encabezado, String mensaje, Alert.AlertType tipo) { // 00048722: Método privado para mostrar alertas
        Alert alert = new Alert(tipo); // 00048722: Crea una nueva instancia de Alert con el tipo especificado
        alert.setTitle(titulo); // 00048722: Establece el título de la alerta
        alert.setHeaderText(encabezado); // 00048722: Establece el encabezado de la alerta
        alert.setContentText(mensaje); // 00048722: Establece el mensaje de la alerta
        alert.showAndWait(); // 00048722: Muestra la alerta y espera a que el usuario la cierre
    } // 00048722: Cierra el método mostrarAlerta
}