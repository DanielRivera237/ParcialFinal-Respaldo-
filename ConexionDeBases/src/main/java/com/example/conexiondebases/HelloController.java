package com.example.conexiondebases;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;

public class HelloController {
    private Conexion conexion;

    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField direccionField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField cardIdField;
    @FXML
    private TextField numeroTarjetaField;
    @FXML
    private TextField fechaExpiracionField;
    @FXML
    private TextField facilitadorField;
    @FXML
    private TextField tipoTarjetaField;
    @FXML
    private TextField clienteIdField;
    @FXML
    private TextField transaccionIdField;
    @FXML
    private TextField fechaField;
    @FXML
    private TextField montoField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField tarjetaIdField;
    @FXML
    private TextArea outputArea;
    @FXML
    private Button insertClientButton;
    @FXML
    private Button showClientsButton;
    @FXML
    private Button updateClientButton;
    @FXML
    private Button deleteClientButton;
    @FXML
    private Button createClientTableButton;
    @FXML
    private Button insertCardButton;
    @FXML
    private Button showCardsButton;
    @FXML
    private Button updateCardButton;
    @FXML
    private Button deleteCardButton;
    @FXML
    private Button createCardTableButton;
    @FXML
    private Button insertTransactionButton;
    @FXML
    private Button showTransactionsButton;
    @FXML
    private Button updateTransactionButton;
    @FXML
    private Button deleteTransactionButton;
    @FXML
    private Button createTransactionTableButton;
    @FXML
    private Button innerJoinButton;
    @FXML
    private Button leftJoinButton;
    @FXML
    private Button rightJoinButton;
    @FXML
    private Button closeButton;

    public void setConexion(Conexion conexion) {
        this.conexion = conexion;
    }

    @FXML
    private void initialize() {
        insertClientButton.setOnAction(e -> insertarCliente());
        showClientsButton.setOnAction(e -> mostrarClientes());
        updateClientButton.setOnAction(e -> actualizarCliente());
        deleteClientButton.setOnAction(e -> eliminarCliente());
        createClientTableButton.setOnAction(e -> crearTablaClientes());

        insertCardButton.setOnAction(e -> insertarTarjeta());
        showCardsButton.setOnAction(e -> mostrarTarjetas());
        updateCardButton.setOnAction(e -> actualizarTarjeta());
        deleteCardButton.setOnAction(e -> eliminarTarjeta());
        createCardTableButton.setOnAction(e -> crearTablaTarjetas());

        insertTransactionButton.setOnAction(e -> insertarTransaccion());
        showTransactionsButton.setOnAction(e -> mostrarTransacciones());
        updateTransactionButton.setOnAction(e -> actualizarTransaccion());
        deleteTransactionButton.setOnAction(e -> eliminarTransaccion());
        createTransactionTableButton.setOnAction(e -> crearTablaTransacciones());

        innerJoinButton.setOnAction(e -> mostrarInnerJoin());
        leftJoinButton.setOnAction(e -> mostrarLeftJoin());
        rightJoinButton.setOnAction(e -> mostrarRightJoin());

        closeButton.setOnAction(e -> cerrarConexion());
    }

    private void insertarCliente() {
        int id = Integer.parseInt(idField.getText());
        String nombreCompleto = nameField.getText();
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();
        conexion.insertCliente(id, nombreCompleto, direccion, telefono);
        outputArea.appendText("Cliente insertado: " + nombreCompleto + "\n");
    }

    private void mostrarClientes() {
        outputArea.setText(""); // Limpiar área de texto
        for (String cliente : conexion.selectRecords("Clientes")) {
            outputArea.appendText(cliente + "\n");
        }
    }

    private void actualizarCliente() {
        int id = Integer.parseInt(idField.getText());
        String direccion = direccionField.getText();
        String telefono = telefonoField.getText();
        conexion.updateCliente(id, direccion, telefono);
        outputArea.appendText("Cliente actualizado: ID " + id + "\n");
    }

    private void eliminarCliente() {
        int id = Integer.parseInt(idField.getText());
        conexion.deleteRecord("Clientes", id);
        outputArea.appendText("Cliente eliminado: ID " + id + "\n");
    }

    private void crearTablaClientes() {
        conexion.createTable("Clientes");
        outputArea.appendText("Tabla Clientes creada.\n");
    }

    private void insertarTarjeta() {
        int id = Integer.parseInt(cardIdField.getText());
        String numeroTarjeta = numeroTarjetaField.getText();
        Date fechaExpiracion = Date.valueOf(fechaExpiracionField.getText());
        String facilitador = facilitadorField.getText();
        String tipoTarjeta = tipoTarjetaField.getText();
        int clienteId = Integer.parseInt(clienteIdField.getText());
        conexion.insertTarjeta(id, numeroTarjeta, fechaExpiracion, facilitador, tipoTarjeta, clienteId);
        outputArea.appendText("Tarjeta insertada: " + numeroTarjeta + "\n");
    }

    private void mostrarTarjetas() {
        outputArea.setText(""); // Limpiar área de texto
        for (String tarjeta : conexion.selectRecords("Tarjetas")) {
            outputArea.appendText(tarjeta + "\n");
        }
    }

    private void actualizarTarjeta() {
        int id = Integer.parseInt(cardIdField.getText());
        Date fechaExpiracion = Date.valueOf(fechaExpiracionField.getText());
        String facilitador = facilitadorField.getText();
        String tipoTarjeta = tipoTarjetaField.getText();
        conexion.updateTarjeta(id, fechaExpiracion, facilitador, tipoTarjeta);
        outputArea.appendText("Tarjeta actualizada: ID " + id + "\n");
    }

    private void eliminarTarjeta() {
        int id = Integer.parseInt(cardIdField.getText());
        conexion.deleteRecord("Tarjetas", id);
        outputArea.appendText("Tarjeta eliminada: ID " + id + "\n");
    }

    private void crearTablaTarjetas() {
        conexion.createTable("Tarjetas");
        outputArea.appendText("Tabla Tarjetas creada.\n");
    }

    private void insertarTransaccion() {
        int id = Integer.parseInt(transaccionIdField.getText());
        Date fecha = Date.valueOf(fechaField.getText());
        double monto = Double.parseDouble(montoField.getText());
        String descripcion = descripcionField.getText();
        int tarjetaId = Integer.parseInt(tarjetaIdField.getText());
        conexion.insertTransaccion(id, fecha, monto, descripcion, tarjetaId);
        outputArea.appendText("Transacción insertada: " + descripcion + "\n");
    }

    private void mostrarTransacciones() {
        outputArea.setText(""); // Limpiar área de texto
        for (String transaccion : conexion.selectRecords("Transacciones")) {
            outputArea.appendText(transaccion + "\n");
        }
    }

    private void actualizarTransaccion() {
        int id = Integer.parseInt(transaccionIdField.getText());
        Date fecha = Date.valueOf(fechaField.getText());
        double monto = Double.parseDouble(montoField.getText());
        String descripcion = descripcionField.getText();
        int tarjetaId = Integer.parseInt(tarjetaIdField.getText());
        conexion.updateTransaccion(id, fecha, monto, descripcion, tarjetaId);
        outputArea.appendText("Transacción actualizada: ID " + id + "\n");
    }

    private void eliminarTransaccion() {
        int id = Integer.parseInt(transaccionIdField.getText());
        conexion.deleteRecord("Transacciones", id);
        outputArea.appendText("Transacción eliminada: ID " + id + "\n");
    }

    private void crearTablaTransacciones() {
        conexion.createTable("Transacciones");
        outputArea.appendText("Tabla Transacciones creada.\n");
    }

    private void mostrarInnerJoin() {
        outputArea.setText(""); // Limpiar área de texto
        for (String record : conexion.innerJoin()) {
            outputArea.appendText(record + "\n");
        }
    }

    private void mostrarLeftJoin() {
        outputArea.setText(""); // Limpiar área de texto
        for (String record : conexion.leftJoin()) {
            outputArea.appendText(record + "\n");
        }
    }

    private void mostrarRightJoin() {
        outputArea.setText(""); // Limpiar área de texto
        for (String record : conexion.rightJoin()) {
            outputArea.appendText(record + "\n");
        }
    }

    private void cerrarConexion() {
        conexion.closeConnection();
        outputArea.appendText("Conexión cerrada.\n");
    }
}
