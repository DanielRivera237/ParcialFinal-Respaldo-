package com.example.conexiondebases;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private Connection connection = null;

    private String usuario;
    private String clave;
    private String dataBase;
    private String IP;
    private String port;

    public Conexion(String usuario, String clave, String dataBase, String IP, String port) {
        this.usuario = usuario;
        this.clave = clave;
        this.dataBase = dataBase;
        this.IP = IP;
        this.port = port;
    }

    public boolean connect() {
        try {
            String url = "jdbc:mysql://" + IP + ":" + port + "/" + dataBase + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, clave);
            JOptionPane.showMessageDialog(null, "Conectado a la base de datos.");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión de bases de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean connectionStablished() {
        return connection != null;
    }

    public void createTable(String tableName) {
        String query;
        switch (tableName) {
            case "Clientes":
                query = "CREATE TABLE Clientes ("
                        + "id INT NOT NULL PRIMARY KEY,"
                        + "nombre_completo VARCHAR(255) NOT NULL,"
                        + "direccion VARCHAR(255),"
                        + "telefono VARCHAR(20)"
                        + ");";
                break;
            case "Tarjetas":
                query = "CREATE TABLE Tarjetas ("
                        + "id INT NOT NULL PRIMARY KEY,"
                        + "numero_tarjeta VARCHAR(20) NOT NULL,"
                        + "fecha_expiracion DATE,"
                        + "facilitador VARCHAR(50),"
                        + "tipo_tarjeta VARCHAR(10),"
                        + "cliente_id INT,"
                        + "FOREIGN KEY (cliente_id) REFERENCES Clientes(id)"
                        + ");";
                break;
            case "Transacciones":
                query = "CREATE TABLE Transacciones ("
                        + "id INT NOT NULL PRIMARY KEY,"
                        + "fecha DATE NOT NULL,"
                        + "monto DECIMAL(10, 2) NOT NULL,"
                        + "descripcion VARCHAR(255),"
                        + "tarjeta_id INT,"
                        + "FOREIGN KEY (tarjeta_id) REFERENCES Tarjetas(id)"
                        + ");";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName);
                return;
        }

        if (connectionStablished()) {
            try {
                Statement statement = connection.createStatement();
                statement.execute(query);
                JOptionPane.showMessageDialog(null, "Tabla " + tableName + " creada con éxito.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en la creación de la tabla: " + e.getMessage());
            }
        }
    }

    public void insertCliente(int id, String nombreCompleto, String direccion, String telefono) {
        String query = "INSERT INTO Clientes (id, nombre_completo, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombreCompleto);
            pstmt.setString(3, direccion);
            pstmt.setString(4, telefono);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Clientes.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage());
        }
    }

    public void insertTarjeta(int id, String numeroTarjeta, Date fechaExpiracion, String facilitador, String tipoTarjeta, int clienteId) {
        String query = "INSERT INTO Tarjetas (id, numero_tarjeta, fecha_expiracion, facilitador, tipo_tarjeta, cliente_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, numeroTarjeta);
            pstmt.setDate(3, fechaExpiracion);
            pstmt.setString(4, facilitador);
            pstmt.setString(5, tipoTarjeta);
            pstmt.setInt(6, clienteId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Tarjetas.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage());
        }
    }

    public void insertTransaccion(int id, Date fecha, double monto, String descripcion, int tarjetaId) {
        String query = "INSERT INTO Transacciones (id, fecha, monto, descripcion, tarjeta_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setDate(2, fecha);
            pstmt.setDouble(3, monto);
            pstmt.setString(4, descripcion);
            pstmt.setInt(5, tarjetaId);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Transacciones.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage());
        }
    }

    public List<String> selectRecords(String tableName) {
        String query;
        switch (tableName) {
            case "Clientes":
                query = "SELECT * FROM Clientes ORDER BY id";
                break;
            case "Tarjetas":
                query = "SELECT * FROM Tarjetas ORDER BY id";
                break;
            case "Transacciones":
                query = "SELECT * FROM Transacciones ORDER BY id";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName);
                return new ArrayList<>();
        }

        List<String> records = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                switch (tableName) {
                    case "Clientes":
                        int idCliente = resultSet.getInt("id");
                        String nombreCompleto = resultSet.getString("nombre_completo");
                        String direccion = resultSet.getString("direccion");
                        String telefono = resultSet.getString("telefono");
                        records.add("ID: " + idCliente + ", Nombre: " + nombreCompleto + ", Dirección: " + direccion + ", Teléfono: " + telefono);
                        break;
                    case "Tarjetas":
                        int idTarjeta = resultSet.getInt("id");
                        String numeroTarjeta = resultSet.getString("numero_tarjeta");
                        Date fechaExpiracion = resultSet.getDate("fecha_expiracion");
                        String facilitador = resultSet.getString("facilitador");
                        String tipoTarjeta = resultSet.getString("tipo_tarjeta");
                        int clienteId = resultSet.getInt("cliente_id");
                        records.add("ID: " + idTarjeta + ", Número de Tarjeta: " + numeroTarjeta + ", Fecha de Expiración: " + fechaExpiracion + ", Facilitador: " + facilitador + ", Tipo de Tarjeta: " + tipoTarjeta + ", ID Cliente: " + clienteId);
                        break;
                    case "Transacciones":
                        int idTransaccion = resultSet.getInt("id");
                        Date fecha = resultSet.getDate("fecha");
                        double monto = resultSet.getDouble("monto");
                        String descripcion = resultSet.getString("descripcion");
                        int tarjetaId = resultSet.getInt("tarjeta_id");
                        records.add("ID: " + idTransaccion + ", Fecha: " + fecha + ", Monto: " + monto + ", Descripción: " + descripcion + ", ID Tarjeta: " + tarjetaId);
                        break;
                }
            }
            JOptionPane.showMessageDialog(null, "Registros mostrados con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros: " + e.getMessage());
        }
        return records;
    }

    public void deleteRecord(String tableName, int id) {
        String query;
        switch (tableName) {
            case "Clientes":
                query = "DELETE FROM Clientes WHERE id = ?";
                break;
            case "Tarjetas":
                query = "DELETE FROM Tarjetas WHERE id = ?";
                break;
            case "Transacciones":
                query = "DELETE FROM Transacciones WHERE id = ?";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName);
                return;
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Registro con ID " + id + " eliminado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ID " + id + ".");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + e.getMessage());
        }
    }

    public void updateCliente(int id, String newDireccion, String newTelefono) {
        String query = "UPDATE Clientes SET direccion = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, newDireccion);
            pstmt.setString(2, newTelefono);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cliente con ID " + id + " actualizado con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún cliente con ID " + id + ".");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + e.getMessage());
        }
    }

    public void updateTarjeta(int id, Date newFechaExpiracion, String newFacilitador, String newTipoTarjeta) {
        String query = "UPDATE Tarjetas SET fecha_expiracion = ?, facilitador = ?, tipo_tarjeta = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, newFechaExpiracion);
            pstmt.setString(2, newFacilitador);
            pstmt.setString(3, newTipoTarjeta);
            pstmt.setInt(4, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Tarjeta con ID " + id + " actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarjeta con ID " + id + ".");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la tarjeta: " + e.getMessage());
        }
    }

    public List<String> innerJoin() {
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "INNER JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "INNER JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id";
        return executeJoinQuery(query);
    }

    public List<String> leftJoin() {
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "LEFT JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "LEFT JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id";
        return executeJoinQuery(query);
    }

    public List<String> rightJoin() {
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "RIGHT JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "RIGHT JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id";
        return executeJoinQuery(query);
    }

    private List<String> executeJoinQuery(String query) {
        List<String> results = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String cliente = resultSet.getString("nombre_completo");
                String descripcion = resultSet.getString("descripcion");
                double monto = resultSet.getDouble("monto");
                results.add("Cliente: " + cliente + ", Descripción: " + descripcion + ", Monto: " + monto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la ejecución de la consulta JOIN: " + e.getMessage());
        }
        return results;
    }

    public void updateTransaccion(int id, Date fecha, double monto, String descripcion, int tarjetaId) {
        String query = "UPDATE Transacciones SET fecha = ?, monto = ?, descripcion = ?, tarjeta_id = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, fecha);
            pstmt.setDouble(2, monto);
            pstmt.setString(3, descripcion);
            pstmt.setInt(4, tarjetaId);
            pstmt.setInt(5, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Transacción con ID " + id + " actualizada con éxito.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna transacción con ID " + id + ".");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la transacción: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos cerrada con éxito.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}
