package com.example.conexiondebases.Controllers; //00048722 declaración del paquete

import com.example.conexiondebases.Conexion; //00048722 importación de la clase Conexion
import javafx.fxml.FXML; //00048722 importación de la anotación FXML
import javafx.fxml.FXMLLoader; //00048722 importación de la clase FXMLLoader
import javafx.scene.Parent; //00048722 importación de la clase Parent
import javafx.scene.Scene; //00048722 importación de la clase Scene
import javafx.scene.control.Button; //00048722 importación de la clase Button
import javafx.scene.control.TableColumn; //00048722 importación de la clase TableColumn
import javafx.scene.control.TableView; //00048722 importación de la clase TableView
import javafx.scene.control.TextField; //00048722 importación de la clase TextField
import javafx.scene.control.cell.PropertyValueFactory; //00048722 importación de la clase PropertyValueFactory
import javafx.stage.Stage; //00048722 importación de la clase Stage

import java.io.IOException; //00048722 importación de la excepción IOException
import java.math.BigDecimal; //00048722 importación de la clase BigDecimal
import java.sql.*; //00048722 importación de las clases de SQL
import java.util.ArrayList; //00048722 importación de la clase ArrayList
import java.util.List; //00048722 importación de la clase List

public class ReporteDController { //00048722 declaración de la clase ReporteDController
    @FXML //00048722 anotación FXML para facilitadorField
    private TextField facilitadorField; //00048722 declaración del campo de texto facilitadorField
    @FXML //00048722 anotación FXML para generarReporteButton
    private Button generarReporteButton; //00048722 declaración del botón generarReporteButton
    @FXML //00048722 anotación FXML para regresarButton
    private Button regresarButton; //00048722 declaración del botón regresarButton
    @FXML //00048722 anotación FXML para tableView
    private TableView<ReporteD> tableView; //00048722 declaración del TableView para ReporteD
    @FXML //00048722 anotación FXML para clienteColumn
    private TableColumn<ReporteD, String> clienteColumn; //00048722 declaración de la columna clienteColumn
    @FXML //00048722 anotación FXML para comprasColumn
    private TableColumn<ReporteD, Integer> comprasColumn; //00048722 declaración de la columna comprasColumn
    @FXML //00048722 anotación FXML para totalColumn
    private TableColumn<ReporteD, BigDecimal> totalColumn; //00048722 declaración de la columna totalColumn

    private Conexion conexion; //00048722 declaración de la variable conexion

    @FXML //00048722 anotación FXML para el método initialize
    public void initialize() { //00048722 método initialize
        conexion = new Conexion("usuario", "clave", "dataBase", "IP", "port"); //00048722 creación de una nueva instancia de Conexion
        conexion.connect(); //00048722 conexión a la base de datos

        generarReporteButton.setOnAction(e -> generarReporte()); //00048722 asignación del evento al botón generarReporteButton

        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente")); //00048722 asignación del valor de la celda para clienteColumn
        comprasColumn.setCellValueFactory(new PropertyValueFactory<>("cantidadCompras")); //00048722 asignación del valor de la celda para comprasColumn
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalGastado")); //00048722 asignación del valor de la celda para totalColumn
    }

    private void generarReporte() { //00048722 método generarReporte
        String facilitador = facilitadorField.getText(); //00048722 obtención del texto del campo facilitadorField
        List<ReporteD> reporteDList = obtenerReporteD(facilitador); //00048722 obtención de la lista de ReporteD
        tableView.getItems().setAll(reporteDList); //00048722 asignación de la lista al TableView
    }

    private List<ReporteD> obtenerReporteD(String facilitador) { //00048722 método obtenerReporteD
        List<ReporteD> reporteDList = new ArrayList<>(); //00048722 creación de una nueva lista de ReporteD
        String query = "SELECT Clientes.nombre_completo AS cliente, COUNT(Transacciones.id) AS cantidad_compras, SUM(Transacciones.monto) AS total_gastado " +
                "FROM Clientes " +
                "INNER JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id " +
                "INNER JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id " +
                "WHERE Tarjetas.facilitador = ? " +
                "GROUP BY Clientes.nombre_completo"; //00048722 declaración de la consulta SQL

        try (PreparedStatement pstmt = conexion.getConnection().prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setString(1, facilitador); //00048722 asignación del valor facilitador
            ResultSet resultSet = pstmt.executeQuery(); //00048722 ejecución de la consulta
            while (resultSet.next()) { //00048722 iteración sobre los resultados
                String cliente = resultSet.getString("cliente"); //00048722 obtención del valor cliente
                int cantidadCompras = resultSet.getInt("cantidad_compras"); //00048722 obtención del valor cantidad_compras
                BigDecimal totalGastado = resultSet.getBigDecimal("total_gastado"); //00048722 obtención del valor total_gastado
                reporteDList.add(new ReporteD(cliente, cantidadCompras, totalGastado)); //00048722 adición del resultado a la lista
            }
        } catch (SQLException e) { //00048722 captura de la excepción SQL
            e.printStackTrace(); //00048722 impresión del stack trace del error
        }

        return reporteDList; //00048722 retorno de la lista de ReporteD
    }

    @FXML //00048722 anotación FXML para el método regresarButton
    public void regresarButton() { //00048722 método regresarButton
        try {
            Stage stage = (Stage) regresarButton.getScene().getWindow(); //00048722 obtención del Stage actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00048722 carga del archivo FXML
            stage.setScene(new Scene(root)); //00048722 establecimiento de la nueva escena
            stage.centerOnScreen(); //00048722 centrado de la ventana en la pantalla
            stage.show(); //00048722 muestra de la ventana
        } catch (IOException e) { //00048722 captura de la excepción IO
            e.printStackTrace(); //00048722 impresión del stack trace del error
        }
    }
}

//Posible solucion a errorres en clase AdminOptionsController (Solo por si acaso)
class ReporteD { //00048722 declaración de la clase ReporteD
    private String cliente; //00048722 declaración de la variable cliente
    private int cantidadCompras; //00048722 declaración de la variable cantidadCompras
    private BigDecimal totalGastado; //00048722 declaración de la variable totalGastado

    public ReporteD(String cliente, int cantidadCompras, BigDecimal totalGastado) { //00048722 constructor de la clase ReporteD
        this.cliente = cliente; //00048722 asignación del valor cliente
        this.cantidadCompras = cantidadCompras; //00048722 asignación del valor cantidadCompras
        this.totalGastado = totalGastado; //00048722 asignación del valor totalGastado
    }

    public String getCliente() { //00048722 método getCliente
        return cliente; //00048722 retorno del valor cliente
    }

    public void setCliente(String cliente) { //00048722 método setCliente
        this.cliente = cliente; //00048722 asignación del valor cliente
    }

    public int getCantidadCompras() { //00048722 método getCantidadCompras
        return cantidadCompras; //00048722 retorno del valor cantidadCompras
    }

    public void setCantidadCompras(int cantidadCompras) { //00048722 método setCantidadCompras
        this.cantidadCompras = cantidadCompras; //00048722 asignación del valor cantidadCompras
    }

    public BigDecimal getTotalGastado() { //00048722 método getTotalGastado
        return totalGastado; //00048722 retorno del valor totalGastado
    }

    public void setTotalGastado(BigDecimal totalGastado) { //00048722 método setTotalGastado
        this.totalGastado = totalGastado; //00048722 asignación del valor totalGastado
    }
}
