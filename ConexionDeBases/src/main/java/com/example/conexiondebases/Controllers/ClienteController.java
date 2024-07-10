package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.ClasesAuxiliares.Alertas.Alertas;
import com.example.conexiondebases.ClasesAuxiliares.Chain.Handler;
import com.example.conexiondebases.ClasesAuxiliares.Chain.HandlerNombreCliente;
import com.example.conexiondebases.ClasesAuxiliares.Chain.HandlerTarjetaDatos;
import com.example.conexiondebases.ClasesAuxiliares.InfoInsertar;

import com.example.conexiondebases.Conexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClienteController implements Initializable, Alertas { //00104923 Define la clase ClienteController que implementa Initializable y Alertas
    @FXML
    private TextField nombre; //00104923 Campo de texto para el nombre
    @FXML
    private TextField apellido; //00104923 Campo de texto para el apellido
    @FXML
    private TextArea descripcion; //00104923 Área de texto para la descripción
    @FXML
    private TextField numeroTarjeta; //00104923 Campo de texto para el número de tarjeta
    @FXML
    private TextField fechaVencimiento; //00104923 Campo de texto para la fecha de vencimiento
    @FXML
    private TextField totalPagar; //00104923 Campo de texto para el total a pagar
    @FXML
    private RadioButton credito; //00104923 Botón de radio para tarjeta de crédito
    @FXML
    private RadioButton debito; //00104923 Botón de radio para tarjeta de débito
    @FXML
    private ComboBox<String> facilitador; //00104923 ComboBox para el facilitador de la tarjeta
    @FXML
    private Button guardar; //00104923 Botón para guardar
    @FXML
    private Button cancelar; //00104923 Botón para cancelar

    private InfoInsertar informe; //00104923 Declaración del objeto InfoInsertar

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { //00104923 Método initialize de Initializable
        facilitador.setValue("---------"); //00104923 Establece el valor inicial del ComboBox
        facilitador.getItems().addAll("Visa", "Mastercard", "American Express"); //00104923 Añade elementos al ComboBox
    }

    @FXML
    public void cancelarVolver() { //00104923 Método para manejar el botón cancelar
        try {
            Stage stage = (Stage) cancelar.getScene().getWindow(); //00104923 Obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00104923 Carga el archivo FXML del menú principal
            stage.setScene(new Scene(root)); //00104923 Establece la nueva escena
            stage.centerOnScreen(); //00104923 Centra la ventana en la pantalla
            stage.show(); //00104923 Muestra la ventana
        } catch (IOException e) { //00104923 Maneja excepciones IO
            e.printStackTrace(); //00104923 Imprime el stack trace de la excepción
        }
    }

    @FXML
    public void guardarVolver() { //00104923 Método para manejar el botón guardar
        boolean condicionDatosPersonajes = this.nombre.getText().isEmpty() || this.apellido.getText().isEmpty(); //00104923 Verifica si los campos de nombre y apellido están vacíos
        boolean condicionTarjeta = this.numeroTarjeta.getText().isEmpty() || this.fechaVencimiento.getText().isEmpty(); //00104923 Verifica si los campos de tarjeta están vacíos
        boolean condicionTipoTarjeta = !this.credito.isSelected() && !this.debito.isSelected(); //00104923 Verifica si no se ha seleccionado ningún tipo de tarjeta
        boolean condicionFacilitador = this.facilitador.getValue().equals("---------"); //00104923 Verifica si no se ha seleccionado un facilitador
        boolean condicionPago = this.totalPagar.getText().isEmpty(); //00104923 Verifica si el campo de total a pagar está vacío

        if (condicionDatosPersonajes || condicionTarjeta || condicionTipoTarjeta || condicionFacilitador || condicionPago) { //00104923 Si alguna condición no se cumple
            tipoAlerta("Debe de rellenar completamente todos los campos.", 2); //00104923 Muestra una alerta de error
        } else {
            String tipoTarjeta = deducirTarjetaTipo(); //00104923 Deduce el tipo de tarjeta
            BigDecimal totalMonto = new BigDecimal(this.totalPagar.getText()); //00104923 Convierte el total a pagar a BigDecimal

            informe = new InfoInsertar(this.nombre.getText(), this.apellido.getText(), this.descripcion.getText(), this.numeroTarjeta.getText(), totalMonto, this.fechaVencimiento.getText(), tipoTarjeta, this.facilitador.getValue()); //00104923 Crea un nuevo objeto InfoInsertar

            if(insertarDatosTransaccion(informe)){ //00104923 Si la inserción de datos es exitosa
                tipoAlerta("Se insertó correctamente", 1); //00104923 Muestra una alerta de éxito
            }else{
                tipoAlerta("No se lograron insertar los datos", 2); //00104923 Muestra una alerta de error
            }
        }
    }

    @Override
    public void tipoAlerta(String mensaje, int tipo) { //00104923 Implementación del método tipoAlerta de la interfaz Alertas
        Alert alert = null; //00104923 Declaración de la alerta
        switch (tipo) { //00104923 Selección del tipo de alerta
            case 1:
                alertaExito(mensaje, alert); //00104923 Muestra una alerta de éxito
                break;
            case 2:
                alertaError(mensaje, alert); //00104923 Muestra una alerta de error
                break;
        }
    }

    @Override
    public void alertaExito(String mensaje, Alert alerta) { //00104923 Implementación del método alertaExito de la interfaz Alertas
        alerta = new Alert(Alert.AlertType.INFORMATION); //00104923 Crea una nueva alerta de información
        alerta.setHeaderText(null); //00104923 Establece el encabezado de la alerta en null
        alerta.setContentText(mensaje); //00104923 Establece el contenido de la alerta
        alerta.showAndWait(); //00104923 Muestra y espera que se cierre la alerta
    }

    @Override
    public void alertaError(String mensaje, Alert alerta) { //00104923 Implementación del método alertaError de la interfaz Alertas
        alerta = new Alert(Alert.AlertType.ERROR); //00104923 Crea una nueva alerta de error
        alerta.setHeaderText(null); //00104923 Establece el encabezado de la alerta en null
        alerta.setContentText(mensaje); //00104923 Establece el contenido de la alerta
        alerta.showAndWait(); //00104923 Muestra y espera que se cierre la alerta
    }

    private String deducirTarjetaTipo(){ //00104923 Método para deducir el tipo de tarjeta
        if(this.credito.isSelected()){ //00104923 Si la tarjeta es de crédito
            return this.credito.getText(); //00104923 Retorna el texto del botón de crédito
        }else if(this.debito.isSelected()){ //00104923 Si la tarjeta es de débito
            return this.debito.getText(); //00104923 Retorna el texto del botón de débito
        }
        return null; //00104923 Retorna null si no se seleccionó ningún tipo
    }

    private boolean insertarDatosTransaccion(InfoInsertar informacion){ //00104923 Método para insertar los datos de la transacción
        Handler chain = hacerCadena(new HandlerNombreCliente(), new HandlerTarjetaDatos()); //00104923 Crea la cadena de responsabilidad
        if(chain.handle(informacion)){ //00104923 Si la cadena maneja correctamente la información
            java.util.Date fechaActual = new java.util.Date(); //00104923 Obtiene la fecha actual
            java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime()); //00104923 Convierte la fecha actual a SQL Date
            try {
                String insercionConsulta = "INSERT INTO Transaccion(fecha_compra, total_gastado, descripcion_compra, numero_tarjeta) VALUES(?,?,?,?)"; //00104923 Consulta SQL para insertar una transacción
                PreparedStatement preparedStatement = Conexion.getInstancia("dsandmin", "greninja1207", "bancocentralnlogonia", "localhost", "3306").getConnection().prepareStatement(insercionConsulta); //00104923 Prepara la consulta SQL
                preparedStatement.setDate(1, sqlFechaActual); //00104923 Establece el primer parámetro de la consulta
                preparedStatement.setBigDecimal(2, informacion.getTotalPagar()); //00104923 Establece el segundo parámetro de la consulta
                preparedStatement.setString(3, informacion.getDescripcion()); //00104923 Establece el tercer parámetro de la consulta
                preparedStatement.setString(4, informacion.getNumeroTarjeta()); //00104923 Establece el cuarto parámetro de la consulta

                preparedStatement.executeUpdate(); //00104923 Ejecuta la actualización

                return true; //00104923 Retorna verdadero si la inserción fue exitosa
            } catch (SQLException e) { //00104923 Maneja excepciones SQL
                tipoAlerta(e.getMessage(), 2); //00104923 Muestra una alerta de error
            }

            return false; //00104923 Retorna falso si hubo una excepción
        }else{
            return false; //00104923 Retorna falso si la cadena no manejó la información
        }
    }

    private Handler hacerCadena(Handler primero, Handler ...resto){ //00104923 Método para crear la cadena de responsabilidad
        Handler aux = primero; //00104923 Inicializa el manejador auxiliar con el primero
        for(Handler verificar : resto){ //00104923 Itera sobre los manejadores restantes
            aux.setNext(verificar); //00104923 Establece el siguiente manejador en la cadena
            aux = verificar; //00104923 Actualiza el manejador auxiliar
        }
        return primero; //00104923 Retorna el primer manejador de la cadena
    }
}
