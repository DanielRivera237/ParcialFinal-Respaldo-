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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteAController { //00379422 declaración de la clase ReporteAController
    @FXML
    private TextField idClienteField; //00379422 campo de texto para que el admin ingrese el ID del cliente
    @FXML
    private DatePicker fechaInicioField; //00379422 Selector de fecha para la fecha de inicio
    @FXML
    private DatePicker fechaFinField; //00379422 Selector de fecha para la fecha de fin
    @FXML
    private Button generarReporteButton; //00379422 Botón que generará el reporte
    @FXML
    private TableView<ColumnasA> outputAreaField; //00379422 Tabla para mostrar los resultados del reporte en formato de columnas
    @FXML
    private TableColumn<ColumnasA, String> ColumnID; //00379422 Columna para mostrar el ID del cliente
    @FXML
    private TableColumn<ColumnasA, String> ColumnNombres; //00379422 Columna para mostrar los nombres del cliente
    @FXML
    private TableColumn<ColumnasA, String> ColumnApellidos; //00379422 Columna para mostrar los apellidos del cliente
    @FXML
    private TableColumn<ColumnasA, Double> ColumnMonto; //00379422 Columna para mostrar el monto de la transacción
    @FXML
    private TableColumn<ColumnasA, Integer> ColumnIDTransaccion; //00379422 Columna para mostrar el ID de la transacción
    @FXML
    private TableColumn<ColumnasA, String> ColumnDescripcion; //00379422 Columna para mostrar la descripción de la transacción
    @FXML
    private Button regresarButton; //00379422 Botón para regresar al menú principal

    @FXML
    public void initialize() { //00379422 Método para inicializar los componentes de la interfaz
        generarReporteButton.setOnAction(e -> generarReporte()); //00379422 Asignación de la acción al botón para generar reporte

        ColumnID.setCellValueFactory(new PropertyValueFactory<>("idCliente")); //00379422 Configuración de la columna de ID del cliente para tomar datos de la propiedad idCliente de ColumnasA
        ColumnNombres.setCellValueFactory(new PropertyValueFactory<>("nombres")); //00379422 Configuración de la columna de nombres para tomar datos de la propiedad nombres de ColumnasA
        ColumnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos")); //00379422 Configuración de la columna de apellidos para tomar datos de la propiedad apellidos de ColumnasA
        ColumnIDTransaccion.setCellValueFactory(new PropertyValueFactory<>("idTransaccion")); //00379422 Configuración de la columna de ID de transacción para tomar datos de la propiedad idTransaccion de ColumnasA
        ColumnMonto.setCellValueFactory(new PropertyValueFactory<>("Monto")); //00379422 Configuración de la columna de monto para tomar datos de la propiedad Monto de ColumnasA
        ColumnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion")); //00379422 Configuración de la columna de descripción para tomar datos de la propiedad descripcion de ColumnasA
    }//00379422 fin del metodo initialize

    private void generarReporte() { //00379422 Método para generar el reporte
        String idCliente = idClienteField.getText(); //00379422 Obtener el ID del cliente del campo de texto
        LocalDate fechaInicio = fechaInicioField.getValue(); //00379422 Obtener la fecha de inicio del selector de fecha inicio
        LocalDate fechaFin = fechaFinField.getValue(); //00379422 Obtener la fecha de fin del selector de fecha fin

        if (idCliente == null || idCliente.isEmpty() || fechaInicio == null || fechaFin == null) { //00379422 Verificar si alguno de los campos a ingresar está vacío
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 Crear una alerta de error
            alert.setTitle("ERROR AL INGRESAR DATOS"); //00379422 Establece el título de la alerta
            alert.setHeaderText(null); //00379422 Elimina el encabezado de la alerta
            alert.setContentText("Por favor, complete todos los campos"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 Muestra la alerta y espera
            return; //00379422 Salir del método
        }//00379422 fin de la condicion

        ObservableList<ColumnasA> reporteA = FXCollections.observableArrayList(); //00379422 Crear una lista observable para los resultados del reporte

        try { //00379422 Bloque try para manejar excepciones
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bancocentralnlogonia", "dsandmin", "greninja1207"); //00379422 Establecer conexión con la base de datos a nuestra computadora con el admin y contraseña respectiva
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
                    "AND t.fecha_compra BETWEEN ? AND ?"; //00379422 select utilizado en sql

            PreparedStatement pstmt = conn.prepareStatement(query); //00379422 Preparar la consulta SQL
            pstmt.setString(1, idCliente); //00379422 Establecer el valor del primer parámetro (ID del cliente)
            pstmt.setDate(2, java.sql.Date.valueOf(fechaInicio)); //00379422 Establecer el valor del segundo parámetro (fecha de inicio)
            pstmt.setDate(3, java.sql.Date.valueOf(fechaFin)); //00379422 Establecer el valor del tercer parámetro (fecha de fin)

            ResultSet rs = pstmt.executeQuery(); //00379422 ejecutar la consulta y obtener los resultados

            if (!rs.isBeforeFirst()) { //00379422 condición para verificar si hay resultados a partir del select realizado
                Alert alert = new Alert(Alert.AlertType.WARNING); //00379422 Crear una alerta de advertencia
                alert.setTitle("SIN RESULTADOS"); //00379422 título de la alerta
                alert.setHeaderText(null); //00379422 Elimina el encabezado de la alerta
                alert.setContentText("No se encontraron registros en la base de datos con los datos proporcionados"); //00379422 mensaje de la alerta
                alert.showAndWait(); //00379422 muestra la alerta y espera
                return; //00379422 Salir del método
            }//00379422 fin de la condicion

            LocalDateTime now = LocalDateTime.now(); //00379422 Obtener la fecha y hora actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); //00379422 Crear un formateador para la fecha y hora
            String fechaHora = now.format(formatter); //00379422 Formatea la fecha y hora actual

            File archivo = new File("C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes\\Reporte_A_" + fechaHora + ".txt"); //00379422 Crear un archivo txt para guardar los resultados dentro de reportes
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)); //00379422 Crear un BufferedWriter para escribir en el archivo

            while (rs.next()) { //00379422 Recorrer los resultados de la consulta
                ColumnasA vistaColumna = new ColumnasA( //00379422 Crear un objeto ColumnasA con los datos del resultado
                        rs.getString("id_cliente"), //00379422 Obtener el ID del cliente de la columna id_cliente
                        rs.getString("nombres"), //00379422 Obtener los nombres del cliente de la columna nombres
                        rs.getString("apellidos"), //00379422 Obtener los apellidos del cliente de la columna apellidos
                        rs.getInt("id_transaccion"), //00379422 Obtener el ID de la transacción de la columna id_transaccion
                        rs.getDouble("total_gastado"), //00379422 Obtener el monto de la transacción de la cplumna total_gastado
                        rs.getString("descripcion_compra") //00379422 Obtener la descripción de la transacción de la columna descripcion_compra
                );
                reporteA.add(vistaColumna); //00379422 Agregar el objeto ColumnasA a la lista observable

                writer.write("ID Cliente: " + rs.getString("id_cliente") +
                        "| Nombres: " + rs.getString("nombres") +
                        "| Apellidos: " + rs.getString("apellidos") +
                        "| ID Transacción: " + rs.getInt("id_transaccion") +
                        "| Monto: " + rs.getDouble("total_gastado") +
                        "| Descripción: " + rs.getString("descripcion_compra")); //00379422 Escribe los datos en el archivo txt
                writer.newLine(); //00379422 Escribe una nueva línea en el archivo txt
            }//00379422 fin de la insercion de datos

            writer.close(); //00379422 cierra el BufferedWriter
            rs.close();  //00379422 cierra el ResultSet
            pstmt.close();  //00379422 cierra el PreparedStatement
            conn.close();  //00379422 cierra la conexión

            outputAreaField.setItems(reporteA); //00379422 Establece los resultados en la tabla

            Alert alert = new Alert(Alert.AlertType.INFORMATION); //00379422 Crea una alerta de información
            alert.setTitle("REPORTE GENERADO"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 Elimina el encabezado de la alerta
            alert.setContentText("El archivo TXT de reporte ha creado con éxito"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera

        } catch (SQLException e) { //00379422 Capturar excepciones SQL
            System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage()); //00379422 Imprimir el mensaje de error en la consola
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 Crea una alerta de error
            alert.setTitle("ERROR DE CONEXIÓN"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 Elimina el encabezado de la alerta
            alert.setContentText("No se pudo conectar a la base de datos"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera
        } catch (IOException e) { //00379422 Capturar excepciones de E/S
            System.out.println("Error al escribir el archivo: " + e.getMessage()); //00379422 Imprimir el mensaje de error en la consola
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 Crea una alerta de error
            alert.setTitle("ERROR DE ARCHIVO"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 Elimina el encabezado de la alerta
            alert.setContentText("No se pudo crear el archivo TXT de reporte"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera
        }//00379422 fin del bloque catch
    }//00379422 fin del metodo generar reporte

    @FXML
    public void regresarButton() { //00379422 Método para regresar al menú principal
        try { //00379422 Bloque try para manejar excepciones
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00379422 Obtener la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00379422 Cargar el archivo FXML del menú principal
            stage.setScene(new Scene(root)); //00379422 Establece la nueva escena en la ventana
            stage.centerOnScreen(); //00379422 Centra la ventana en la pantalla
            stage.show(); //00379422 muestra la ventana
        } catch (IOException e) { //00379422 Captura excepciones de E/S
            e.printStackTrace(); //00379422 imprime el mensaje de error en la consola
        } //00379422 fin del bloque catch
    } //00379422 fin del método regresarButton
} //00379422 fin de la clase ReporteBController
