package com.example.conexiondebases.Controllers;

import com.example.conexiondebases.ClasesAuxiliares.Alertas.Alertas;
import com.example.conexiondebases.Conexion;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InicioSesionController implements Alertas { //00104923 Implementa la interfaz Alertas
    private Conexion conexion = Conexion.getInstancia("dsandmin", "greninja1207", "bancocentralnlogonia", "localhost", "3306"); //00104923 Obtención de instancia de conexión a la base de datos
    @FXML
    private Button aceptar; //00104923 Botón aceptar para iniciar sesión
    @FXML
    private Button cancelar; //00104923 Botón cancelar para regresar al menú principal
    @FXML
    private TextField usuario; //00104923 Campo de texto para el nombre de usuario
    @FXML
    private PasswordField clave; //00104923 Campo de texto para la contraseña

    public boolean verificarInicioSesion() { //00104923 Método para verificar el inicio de sesión
        try {
            String query = "SELECT nombre, clave FROM Admin WHERE nombre = ? AND clave = ?"; //00104923 Consulta SQL para verificar el nombre y la clave
            PreparedStatement preparedStatement = conexion.getConnection().prepareStatement(query); //00104923 Preparación de la consulta
            preparedStatement.setString(1, this.usuario.getText()); //00104923 Establecer el nombre de usuario en la consulta
            preparedStatement.setString(2, this.clave.getText()); //00104923 Establecer la clave en la consulta

            ResultSet resultSet = preparedStatement.executeQuery(); //00104923 Ejecución de la consulta

            if (resultSet.next()) { //00104923 Si hay un resultado, el inicio de sesión es exitoso
                tipoAlerta("Inicio de sesión exitoso", 1); //00104923 Muestra una alerta de éxito
                return true; //00104923 Retorna true
            } else { //00104923 Si no hay resultados, el inicio de sesión falla
                tipoAlerta("Usuario o clave incorrecta", 2); //00104923 Muestra una alerta de error
                return false; //00104923 Retorna false
            }

        } catch (Exception e) { //00104923 Manejo de excepciones
            tipoAlerta(e.getMessage(), 2); //00104923 Muestra una alerta de error con el mensaje de la excepción
            return false; //00104923 Retorna false
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

    @FXML
    private void irAdminOpciones() { //00104923 Método para cambiar a la escena de opciones del administrador
        try {
            if (verificarInicioSesion()) { //00104923 Verifica si el inicio de sesión es exitoso
                Stage stage = (Stage) aceptar.getScene().getWindow(); //00104923 Obtiene la ventana actual
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/conexiondebases/opciones-administrador.fxml")); //00104923 Carga el archivo FXML
                Parent root = loader.load(); //00104923 Carga el contenido del archivo FXML
                Scene nuevaEscena = new Scene(root); //00104923 Crea una nueva escena con el contenido cargado
                stage.setScene(nuevaEscena); //00104923 Establece la nueva escena en el stage
                stage.centerOnScreen(); //00104923 Centra la ventana en la pantalla
                stage.show(); //00104923 Muestra la ventana

                // Usar un PauseTransition para mover el enfoque después de que la escena se haya cargado
                PauseTransition pause = new PauseTransition(Duration.millis(10)); //00104923 Crea una pausa de 10 milisegundos
                pause.setOnFinished(event -> nuevaEscena.getRoot().requestFocus()); //00104923 Mueve el enfoque al nodo raíz después de la pausa
                pause.play(); //00104923 Inicia la pausa
            }
        } catch (Exception e) { //00104923 Manejo de excepciones
            tipoAlerta(e.getMessage(), 2); //00104923 Muestra una alerta de error con el mensaje de la excepción
        }
    }

    @FXML
    private void cancelar() { //00104923 Método para regresar al menú principal
        try {
            Stage stage = (Stage) cancelar.getScene().getWindow(); //00104923 Obtiene la ventana actual
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/conexiondebases/main-menu.fxml")); //00104923 Carga el archivo FXML del menú principal
            Scene nuevaEscena = new Scene(root); //00104923 Crea una nueva escena con el contenido cargado
            stage.setScene(nuevaEscena); //00104923 Establece la nueva escena en el stage
            stage.centerOnScreen(); //00104923 Centra la ventana en la pantalla
            stage.show(); //00104923 Muestra la ventana

            // Usar un PauseTransition para mover el enfoque después de que la escena se haya cargado
            PauseTransition pause = new PauseTransition(Duration.millis(10)); //00104923 Crea una pausa de 10 milisegundos
            pause.setOnFinished(event -> nuevaEscena.getRoot().requestFocus()); //00104923 Mueve el enfoque al nodo raíz después de la pausa
            pause.play(); //00104923 Inicia la pausa
        } catch (IOException e) { //00104923 Manejo de excepciones IO
            tipoAlerta(e.getMessage(), 2); //00104923 Muestra una alerta de error con el mensaje de la excepción
        }
    }
}
