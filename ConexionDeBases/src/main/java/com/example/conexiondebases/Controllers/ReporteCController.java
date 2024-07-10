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

public class ReporteCController {// 00083223 declaracion de la clase ReporteCController

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

    private void generarReporte() {// 00083223 inicializa el metodo generarReporte()
        String idCliente = idClienteField.getText(); //00083223 obtiene el id del cliente ingresado

        if (idCliente == null || idCliente.trim().isEmpty()) { //00083223 verifica si el campo de ID está vacío
            Alert alert = new Alert(Alert.AlertType.ERROR); //00083223 muestra una alerta de error
            alert.setTitle("ERROR AL INGRESAR DATOS"); //00083223 establece el título de la alerta
            alert.setHeaderText(null); //00083223 establece el encabezado de la alerta como nulo
            alert.setContentText("Por favor, complete todos los campos"); //00083223 establece el contenido de la alerta

            alert.showAndWait(); //00083223 esto muestra la alerta y espera a que el usuario la cierre
            return; //00083223 salida del método si el campo está vacío
        }// 00083223 fin de la verificacion

        ObservableList<ColumnaC> reporteC = FXCollections.observableArrayList(); //00083223 crea una lista observable para almacenar los datos del reporte
        try {// 00083223 incio del bloque try
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bancocentralnlogonia", "dsandmin", "greninja1207"); //00083223 establece la conexión con la base de datos
            String query = "SELECT c.id_cliente AS IDCliente, " + //00083223 selecciona la columna a mostrar con su respectivo alias
                    "MAX(CASE WHEN t.tipo = 'Débito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaDebito, " +//00083223 usa una expresion "case" para las tarjetas de débito: si el tipo es 'Débito', concatena 'XXXX XXXX XXXX ' con los últimos 4 dígitos del número de la tarjeta. Si no, usa 'N/A'.
                    "MAX(CASE WHEN t.tipo = 'Crédito' THEN CONCAT('XXXX XXXX XXXX ', RIGHT(t.numero, 4)) ELSE 'N/A' END) AS TarjetaCredito " +//00083223 usa una expresion "case" para las tarjetas de crédito: si el tipo es 'Crédito', concatena 'XXXX XXXX XXXX ' con los últimos 4 dígitos del número de la tarjeta. Si no, usa 'N/A'.
                    "FROM Cliente c " +//00083223 selecciona la tabla Cliente con su respectivo alias
                    "LEFT JOIN Tarjeta t ON c.id_cliente = t.id_cliente " +//00083223 hace una unión  de la tabla Cliente con la tabla Tarjeta usando una LEFT JOIN para incluir todos los clientes.
                    "WHERE c.id_cliente = ? " +//00083223 filtra los resultados para que solo se incluyan los datos del cliente cuyo id_cliente coincide con el valor proporcionado.
                    "GROUP BY c.id_cliente";//00083223 agrupa los resultados por id_cliente para asegurarse de que cada cliente aparezca una sola vez en los resultados.

            PreparedStatement pstmt = conn.prepareStatement(query); //00083223 prepara la consulta SQL
            pstmt.setString(1, idCliente); //00083223 esto establece el id del cliente en la consulta

            ResultSet rs = pstmt.executeQuery(); //00083223 ejecuta la consulta y obtiene los resultados

            if (!rs.isBeforeFirst()) { //00083223 verifica si no hay resultados
                Alert alert = new Alert(Alert.AlertType.ERROR); //00083223 muestra una alerta de error
                alert.setTitle("ID NO ENCONTRADO"); //00083223 esto establece el título de la alerta
                alert.setHeaderText(null); //00083223 aqui establece el encabezado de la alerta como nulo
                alert.setContentText("No se encontró un cliente con el ID proporcionado."); //00083223 establece el contenido de la alerta

                alert.showAndWait(); //00083223 esto muestra la alerta y espera a que el usuario la cierre
                return; //00083223 salida del método si no hay resultados
            }// 00083223 fin de la verficacion

            LocalDateTime now = LocalDateTime.now(); //00083223 obtiene la fecha y hora actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"); //00083223 define el formato de la fecha y hora
            String timestamp = now.format(formatter); //00083223 formatea la fecha y hora actual

            File archivo = new File("C:\\Users\\dsand\\IdeaProjects\\demoParcialFinalPOO\\src\\main\\java\\com\\example\\conexiondebases\\Controllers\\Reportes\\Reporte_C_" + timestamp + ".txt"); //00379422 Crear un archivo txt para guardar los resultados dentro de reportes
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo)); //00083223 aqui prepara el archivo para escribir en él

            while (rs.next()) { //00083223 itera sobre los resultados de la consulta
                String id = rs.getString("IDCliente"); //00083223 obtiene el ID del cliente
                String numeroCredito = rs.getString("TarjetaCredito"); //00083223 obtiene el número de la tarjeta de crédito
                String numeroDebito = rs.getString("TarjetaDebito"); //00083223 obtiene el número de la tarjeta de débito

                String linea = "ID Cliente: " + id + ", Tarjeta de crédito: " + (numeroCredito != null ? numeroCredito : "N/A") + ", Tarjeta de débito: " + (numeroDebito != null ? numeroDebito : "N/A"); //00083223 crea una línea con todos los datos concatenados

                writer.write(linea); //00083223 escribe la linea en el archivo
                writer.newLine(); //00083223 añade una nueva linea en blanco entre registros

                reporteC.add(new ColumnaC(id, numeroCredito, numeroDebito)); //00083223 Añade los datos a la lista observable
            }//0083223 fin de la iteracion

            writer.close(); //00083223 cierra el escritor de archivos
            rs.close(); //00083223 cierra el conjunto de resultados
            pstmt.close(); //00083223 cierra el statement preparado
            conn.close(); //00083223 cierra la conexión con la base de datos

            outPut.setItems(reporteC); //00083223 establece los datos en la tabla

            Alert alert = new Alert(Alert.AlertType.INFORMATION); //00083223 muestra una alerta de éxito
            alert.setTitle("Reporte Generado"); //00083223 establece el título de la alerta
            alert.setHeaderText(null); //00083223 establece el encabezado de la alerta como nulo
            alert.setContentText("El archivo TXT ha sido creado con éxito."); //00083223 establece el contenido de la alerta

            alert.showAndWait(); //00083223 aqui muestra la alerta y espera a que el usuario la cierre

        } catch (Exception e) {//00083223 captura de excepción IOException
            System.out.println("Fallo al conectar la Base de Datos: " + e.getMessage()); //00083223 imprime un mensaje de error si ocurre una excepción
        }//00083223 fin del bloque catch
    }//00083223 fin del metodo generarReporte()

    @FXML
    public void regresarButton() {//00083223 incio del metodo regresarButton()
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00083223 obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00083223 Carga la vista del menú principal
            stage.setScene(new Scene(root)); //00083223 aqui establece la escena del menú principal en la ventana
            stage.centerOnScreen(); //00083223 esto centra la ventana en la pantalla
            stage.show(); //00083223 muestra la ventana
        } catch (IOException e) { //00083223 captura de excepción IOException
            e.printStackTrace(); //00083223 imprime la traza de la excepción si ocurre un error al cargar la vista
        }//00083223 fin del bloque catch
    }// 00083223 fin del metodo regresarButton()
}//00083223 fin de la clase ReporteCController
