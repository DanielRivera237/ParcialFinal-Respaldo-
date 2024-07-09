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
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReporteBController { //00379422 declaración de la clase ReporteBController
    @FXML
    private TextField idClienteField; //00379422 variable campo de texto ID cliente
    @FXML
    private TextField mesField; //00379422 variable campo de texto para mes
    @FXML
    private TextField anioField; //00379422 variable campo de texto para año
    @FXML
    private Button generarReporteButton; //00379422 variable botón para generar reporte de los datos ingresados del cliente
    @FXML
    private TableView<ColumnasB> contArea; //00379422 variable para mostrar contenido del select en la interfaz por medio de columnas
    @FXML
    private Button regresarButton; //00379422 variable botón para regresar al menú principal
    @FXML
    private TableColumn<ColumnasB, String> columnIdCliente; //00379422 variable columna ID cliente
    @FXML
    private TableColumn<ColumnasB, Double> columnGastos; //00379422 variable columna gastos

    @FXML
    public void initialize() { //00379422 inicialización del método initialize
        generarReporteButton.setOnAction(e -> generarReporte()); //00379422 establece la acción del botón para generar el reporte txt

        columnIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente")); //00379422 configuración de la columna ID cliente a partir de ColumnasB
        columnGastos.setCellValueFactory(new PropertyValueFactory<>("montoMes")); //00379422 configuración de la columna gastos a partir de ColumnasB
    } //00379422 fin método initialize

    private void generarReporte() { //00379422 inicialización del método generarReporte
        String idCliente = idClienteField.getText(); //00379422 obtener el ID del cliente del campo de texto de la interfaz
        String mes = mesField.getText(); //00379422 obtiene el mes del campo de texto de la interfaz
        String anio = anioField.getText(); //00379422 obtiene el año del campo de texto de la interfaz

        if (idCliente == null || idCliente.isEmpty() || anio.isEmpty() || mes.isEmpty()) { //00379422 verificar si algún campo está vacío para mostar alerta
            //00379422 muestra alerta de error
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 crea una alerta de error
            alert.setTitle("ERROR AL INGRESAR DATOS"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 elimina el encabezado de la alerta
            alert.setContentText("Por favor, complete todos los campos"); //00379422 mensaje de la alerta

            alert.showAndWait(); //00379422 muestra la alerta y espera
            return; //00379422 salir del método
        }//00379422 fin de la condicion

        ObservableList<ColumnasB> reporteB = FXCollections.observableArrayList(); //00379422 crea una lista observable para los resultados del reporte

        try { //00379422 inicio del bloque try para manejar excepciones
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bancocentralnlogonia", "dsandmin", "greninja1207"); //00379422 establece conexión con la base de datos con el admin y contraseña respectivos
            String query = "SELECT " +
                    "c.id_cliente, " +
                    "SUM(t.total_gastado) AS GASTOS_MES " +
                    "FROM Transaccion t " +
                    "JOIN Tarjeta ta ON t.numero_tarjeta = ta.numero " +
                    "JOIN Cliente c ON ta.id_cliente = c.id_cliente " +
                    "WHERE c.id_cliente = ? " +
                    "AND EXTRACT(MONTH FROM t.fecha_compra) = ? " +
                    "AND EXTRACT(YEAR FROM t.fecha_compra) = ? " +
                    "GROUP BY c.id_cliente"; //00379422 select utilizado para hacer la consulta SQL que pide el reporte B

            PreparedStatement pstmt = conn.prepareStatement(query); //00379422 prepara la consulta SQL
            pstmt.setString(1, idCliente); //00379422 establece el valor del primer parámetro (ID del cliente)
            pstmt.setString(2, mes); //00379422 establece el valor del segundo parámetro (mes)
            pstmt.setString(3, anio); //00379422 establece el valor del tercer parámetro (anio)

            ResultSet rs = pstmt.executeQuery(); //00379422 ejecuta la consulta y recoge los resultados

            //00379422 condicional en caso no se hayan encontrado nada en el select
            if (!rs.isBeforeFirst()) { //00379422 verifica si hay resultados del select
                Alert alert = new Alert(Alert.AlertType.WARNING); //00379422 crea una alerta de advertencia
                alert.setTitle("SIN RESULTADOS"); //00379422 título de la alerta
                alert.setHeaderText(null); //00379422 elimina el encabezado de la alerta
                alert.setContentText("No se encontraron registros en la base de datos con los datos proporcionados"); //00379422 Mensaje de la alerta
                alert.showAndWait(); //00379422 muestra la alerta y espera
                return; //00379422 salir del método
            }//00379422 fin de la condicion

            //00379422 obtiene la fecha y hora actual para nombrar el archivo
            LocalDateTime now = LocalDateTime.now(); //00379422 obtiene la fecha y hora actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); //00379422 crea un formateador para la fecha y hora
            String fechaHora = now.format(formatter); //00379422 formatea la fecha y hora actual

            //00379422 crear un archivo TXT para guardar los resultados
            File archivo = new File("C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes\\Reporte_B_" + fechaHora + ".txt"); //00379422 crear un archivo TXT para guardar los resultados del select hecho
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)); //00379422 crea un BufferedWriter para escribir en el archivo TXT

            while (rs.next()) { //00379422 recorre los resultados de la consulta
                ColumnasB vistaColumna = new ColumnasB( //00379422 crea un objeto ColumnasB con los datos del resultado del select
                        rs.getString("id_cliente"), //00379422 obtener el ID del cliente de la columna id_cliente
                        rs.getDouble("GASTOS_MES") //00379422 obtener los gastos del mes del cliente de la columna nombrada como GASTOS_MES
                );
                reporteB.add(vistaColumna); //00379422 agrega el objeto ColumnasB a la lista observable

                //00379422 escribe los resultados en el archivo TXT
                writer.write("ID Cliente: " + rs.getString("id_cliente") + "| Gastos del Mes del Cliente: " + rs.getDouble("GASTOS_MES")); //00379422 escribir los datos en el archivo TXT del reporte
                writer.newLine(); //00379422 escribe una nueva línea en el archivo
            }//00379422 fin de la insercion de datos

            writer.close(); //00379422 cerrar el BufferedWriter
            rs.close();  //00379422 cerrar el ResultSet
            pstmt.close();  //00379422 cerrar el PreparedStatement
            conn.close();  //00379422 cerrar la conexión

            contArea.setItems(reporteB); //00379422 va a agregar los resultados en la tabla en las respectivas columnas del table view

            //00379422 muestra alerta de éxito de que se creo correctamente el archivo txt
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //00379422 crea una alerta de información
            alert.setTitle("REPORTE GENERADO"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 elimina el encabezado de la alerta
            alert.setContentText("El archivo TXT de reporte ha creado con éxito"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera

        } catch (SQLException e) { //00379422 captura excepciones SQL
            System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage()); //00379422 imprime el mensaje de error en la consola
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 crea una alerta de error
            alert.setTitle("ERROR DE CONEXIÓN"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 elimina el encabezado de la alerta
            alert.setContentText("No se pudo conectar a la base de datos"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera
        } catch (IOException e) { //00379422 captura excepciones de E/S
            System.out.println("Error al escribir el archivo: " + e.getMessage()); //00379422 imprime el mensaje de error en la consola
            Alert alert = new Alert(Alert.AlertType.ERROR); //00379422 crea una alerta de error
            alert.setTitle("ERROR DE ARCHIVO"); //00379422 título de la alerta
            alert.setHeaderText(null); //00379422 elimina el encabezado de la alerta
            alert.setContentText("No se pudo crear el archivo TXT de reporte"); //00379422 mensaje de la alerta
            alert.showAndWait(); //00379422 muestra la alerta y espera
        }//00379422 fin de la captura de errores
    }//00379422 fin método generar reporte

    @FXML //00379422 anotación para vincular el método con el evento del botón
    public void regresarButton() { //00379422 inicialización del método regresarButton
        try { //00379422 inicio del bloque try
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00379422 obtener la ventana actual del botón regresar
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00379422 cargar el archivo FXML del menú principal
            stage.setScene(new Scene(root)); //00379422 establecer la nueva escena en el stage
            stage.centerOnScreen(); //00379422 centrar la ventana en la pantalla
            stage.show(); //00379422 muestra la ventana
        } catch (IOException e) { //00379422 capturar excepciones de E/S
            e.printStackTrace(); //00379422 imprimir la traza de la excepción
        } //00379422 fin del bloque catch
    } //00379422 fin del método regresarButton
} //00379422 fin de la clase ReporteBController
