package com.example.conexiondebases;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private Connection connection = null; //00048722 declaración de la variable de conexión

    private String usuario; //00048722 declaración de la variable usuario
    private String clave; //00048722 declaración de la variable clave
    private String dataBase; //00048722 declaración de la variable dataBase
    private String IP; //00048722 declaración de la variable IP
    private String port; //00048722 declaración de la variable port

<<<<<<< HEAD
    private static Conexion instancia;

    private Conexion(String usuario, String clave, String dataBase, String IP, String port) {
        this.usuario = usuario;
        this.clave = clave;
        this.dataBase = dataBase;
        this.IP = IP;
        this.port = port;
=======
    public Conexion(String usuario, String clave, String dataBase, String IP, String port) { //00048722 inicialización del constructor
        this.usuario = usuario; //00048722 asignación del parámetro usuario
        this.clave = clave; //00048722 asignación del parámetro clave
        this.dataBase = dataBase; //00048722 asignación del parámetro dataBase
        this.IP = IP; //00048722 asignación del parámetro IP
        this.port = port; //00048722 asignación del parámetro port
    }

    public Connection getConnection() { //00048722 método para obtener la conexión
        return connection; //00048722 retorno de la variable connection
>>>>>>> Daniel_ReporteD
    }

    public static Conexion getInstancia(String usuario, String clave, String dataBase, String IP, String port) {
        if (instancia == null) {
            instancia = new Conexion(usuario, clave, dataBase, IP, port);
        }
        return instancia;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean connect() {
        try {
            if (IP == null || port == null || IP.isEmpty() || port.isEmpty()) {
                throw new IllegalArgumentException("IP y port no pueden ser nulos o vacíos.");
            }
            String url = "jdbc:mysql://" + IP + ":" + port + "/" + dataBase + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, clave);
            JOptionPane.showMessageDialog(null, "Conectado a la base de datos.");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión de bases de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Parámetros inválidos para la conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean connectionStablished() { //00048722 método para verificar si la conexión está establecida
        return connection != null; //00048722 retorno verdadero si la conexión no es null
    }

<<<<<<< HEAD
 /*   public void createTable(String tableName) {
        String query;
        switch (tableName) {
=======
    public void createTable(String tableName) { //00048722 método para crear una tabla
        String query; //00048722 declaración de la variable query
        switch (tableName) { //00048722 selección de la tabla a crear según el nombre
>>>>>>> Daniel_ReporteD
            case "Clientes":
                query = "CREATE TABLE Clientes ("
                        + "id INT NOT NULL PRIMARY KEY,"
                        + "nombre_completo VARCHAR(255) NOT NULL,"
                        + "direccion VARCHAR(255),"
                        + "telefono VARCHAR(20)"
                        + ");"; //00048722 definición de la tabla Clientes
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
                        + ");"; //00048722 definición de la tabla Tarjetas
                break;
            case "Transacciones":
                query = "CREATE TABLE Transacciones ("
                        + "id INT NOT NULL PRIMARY KEY,"
                        + "fecha DATE NOT NULL,"
                        + "monto DECIMAL(10, 2) NOT NULL,"
                        + "descripcion VARCHAR(255),"
                        + "tarjeta_id INT,"
                        + "FOREIGN KEY (tarjeta_id) REFERENCES Tarjetas(id)"
                        + ");"; //00048722 definición de la tabla Transacciones
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName); //00048722 mensaje de error si el nombre de la tabla es desconocido
                return; //00048722 retorno si el nombre de la tabla es desconocido
        }

        if (connectionStablished()) { //00048722 verificación si la conexión está establecida
            try {
                Statement statement = connection.createStatement(); //00048722 creación del statement
                statement.execute(query); //00048722 ejecución de la query
                JOptionPane.showMessageDialog(null, "Tabla " + tableName + " creada con éxito."); //00048722 mensaje de éxito en la creación de la tabla
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error en la creación de la tabla: " + e.getMessage()); //00048722 mensaje de error en la creación de la tabla
            }
        }
    }

    public void insertCliente(int id, String nombreCompleto, String direccion, String telefono) { //00048722 método para insertar un cliente
        String query = "INSERT INTO Clientes (id, nombre_completo, direccion, telefono) VALUES (?, ?, ?, ?)"; //00048722 query de inserción
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setInt(1, id); //00048722 asignación del valor id
            pstmt.setString(2, nombreCompleto); //00048722 asignación del valor nombreCompleto
            pstmt.setString(3, direccion); //00048722 asignación del valor direccion
            pstmt.setString(4, telefono); //00048722 asignación del valor telefono
            pstmt.executeUpdate(); //00048722 ejecución de la query
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Clientes."); //00048722 mensaje de éxito en la inserción
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage()); //00048722 mensaje de error en la inserción
        }
    }

    public void insertTarjeta(int id, String numeroTarjeta, Date fechaExpiracion, String facilitador, String tipoTarjeta, int clienteId) { //00048722 método para insertar una tarjeta
        String query = "INSERT INTO Tarjetas (id, numero_tarjeta, fecha_expiracion, facilitador, tipo_tarjeta, cliente_id) VALUES (?, ?, ?, ?, ?, ?)"; //00048722 query de inserción
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setInt(1, id); //00048722 asignación del valor id
            pstmt.setString(2, numeroTarjeta); //00048722 asignación del valor numeroTarjeta
            pstmt.setDate(3, fechaExpiracion); //00048722 asignación del valor fechaExpiracion
            pstmt.setString(4, facilitador); //00048722 asignación del valor facilitador
            pstmt.setString(5, tipoTarjeta); //00048722 asignación del valor tipoTarjeta
            pstmt.setInt(6, clienteId); //00048722 asignación del valor clienteId
            pstmt.executeUpdate(); //00048722 ejecución de la query
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Tarjetas."); //00048722 mensaje de éxito en la inserción
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage()); //00048722 mensaje de error en la inserción
        }
    }

    public void insertTransaccion(int id, Date fecha, double monto, String descripcion, int tarjetaId) { //00048722 método para insertar una transacción
        String query = "INSERT INTO Transacciones (id, fecha, monto, descripcion, tarjeta_id) VALUES (?, ?, ?, ?, ?)"; //00048722 query de inserción
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setInt(1, id); //00048722 asignación del valor id
            pstmt.setDate(2, fecha); //00048722 asignación del valor fecha
            pstmt.setDouble(3, monto); //00048722 asignación del valor monto
            pstmt.setString(4, descripcion); //00048722 asignación del valor descripcion
            pstmt.setInt(5, tarjetaId); //00048722 asignación del valor tarjetaId
            pstmt.executeUpdate(); //00048722 ejecución de la query
            JOptionPane.showMessageDialog(null, "Registro insertado con éxito en la tabla Transacciones."); //00048722 mensaje de éxito en la inserción
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar el registro: " + e.getMessage()); //00048722 mensaje de error en la inserción
        }
    }

    public List<String> selectRecords(String tableName) { //00048722 método para seleccionar registros de una tabla
        String query; //00048722 declaración de la variable query
        switch (tableName) { //00048722 selección de la tabla según el nombre
            case "Clientes":
                query = "SELECT * FROM Clientes ORDER BY id"; //00048722 query de selección para Clientes
                break;
            case "Tarjetas":
                query = "SELECT * FROM Tarjetas ORDER BY id"; //00048722 query de selección para Tarjetas
                break;
            case "Transacciones":
                query = "SELECT * FROM Transacciones ORDER BY id"; //00048722 query de selección para Transacciones
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName); //00048722 mensaje de error si el nombre de la tabla es desconocido
                return new ArrayList<>(); //00048722 retorno de una lista vacía si el nombre de la tabla es desconocido
        }

        List<String> records = new ArrayList<>(); //00048722 creación de la lista de registros
        try {
            Statement statement = connection.createStatement(); //00048722 creación del statement
            ResultSet resultSet = statement.executeQuery(query); //00048722 ejecución de la query
            while (resultSet.next()) { //00048722 iteración sobre los resultados
                switch (tableName) { //00048722 selección de la tabla según el nombre
                    case "Clientes":
                        int idCliente = resultSet.getInt("id"); //00048722 obtención del valor id
                        String nombreCompleto = resultSet.getString("nombre_completo"); //00048722 obtención del valor nombreCompleto
                        String direccion = resultSet.getString("direccion"); //00048722 obtención del valor direccion
                        String telefono = resultSet.getString("telefono"); //00048722 obtención del valor telefono
                        records.add("ID: " + idCliente + ", Nombre: " + nombreCompleto + ", Dirección: " + direccion + ", Teléfono: " + telefono); //00048722 adición del registro a la lista
                        break;
                    case "Tarjetas":
                        int idTarjeta = resultSet.getInt("id"); //00048722 obtención del valor id
                        String numeroTarjeta = resultSet.getString("numero_tarjeta"); //00048722 obtención del valor numeroTarjeta
                        Date fechaExpiracion = resultSet.getDate("fecha_expiracion"); //00048722 obtención del valor fechaExpiracion
                        String facilitador = resultSet.getString("facilitador"); //00048722 obtención del valor facilitador
                        String tipoTarjeta = resultSet.getString("tipo_tarjeta"); //00048722 obtención del valor tipoTarjeta
                        int clienteId = resultSet.getInt("cliente_id"); //00048722 obtención del valor clienteId
                        records.add("ID: " + idTarjeta + ", Número de Tarjeta: " + numeroTarjeta + ", Fecha de Expiración: " + fechaExpiracion + ", Facilitador: " + facilitador + ", Tipo de Tarjeta: " + tipoTarjeta + ", ID Cliente: " + clienteId); //00048722 adición del registro a la lista
                        break;
                    case "Transacciones":
                        int idTransaccion = resultSet.getInt("id"); //00048722 obtención del valor id
                        Date fecha = resultSet.getDate("fecha"); //00048722 obtención del valor fecha
                        double monto = resultSet.getDouble("monto"); //00048722 obtención del valor monto
                        String descripcion = resultSet.getString("descripcion"); //00048722 obtención del valor descripcion
                        int tarjetaId = resultSet.getInt("tarjeta_id"); //00048722 obtención del valor tarjetaId
                        records.add("ID: " + idTransaccion + ", Fecha: " + fecha + ", Monto: " + monto + ", Descripción: " + descripcion + ", ID Tarjeta: " + tarjetaId); //00048722 adición del registro a la lista
                        break;
                }
            }
            JOptionPane.showMessageDialog(null, "Registros mostrados con éxito."); //00048722 mensaje de éxito en la selección
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros: " + e.getMessage()); //00048722 mensaje de error en la selección
        }
        return records; //00048722 retorno de la lista de registros
    }

    public void deleteRecord(String tableName, int id) { //00048722 método para eliminar un registro
        String query; //00048722 declaración de la variable query
        switch (tableName) { //00048722 selección de la tabla según el nombre
            case "Clientes":
                query = "DELETE FROM Clientes WHERE id = ?"; //00048722 query de eliminación para Clientes
                break;
            case "Tarjetas":
                query = "DELETE FROM Tarjetas WHERE id = ?"; //00048722 query de eliminación para Tarjetas
                break;
            case "Transacciones":
                query = "DELETE FROM Transacciones WHERE id = ?"; //00048722 query de eliminación para Transacciones
                break;
            default:
                JOptionPane.showMessageDialog(null, "Tabla desconocida: " + tableName); //00048722 mensaje de error si el nombre de la tabla es desconocido
                return; //00048722 retorno si el nombre de la tabla es desconocido
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setInt(1, id); //00048722 asignación del valor id
            int rowsAffected = pstmt.executeUpdate(); //00048722 ejecución de la query
            if (rowsAffected > 0) { //00048722 verificación si hubo registros afectados
                JOptionPane.showMessageDialog(null, "Registro con ID " + id + " eliminado con éxito."); //00048722 mensaje de éxito en la eliminación
            } else {
<<<<<<< HEAD
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ID " + id +.");
=======
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ID " + id + "."); //00048722 mensaje de error si no se encontró el registro
>>>>>>> Daniel_ReporteD
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + e.getMessage()); //00048722 mensaje de error en la eliminación
        }
    }

    public void updateCliente(int id, String newDireccion, String newTelefono) { //00048722 método para actualizar un cliente
        String query = "UPDATE Clientes SET direccion = ?, telefono = ? WHERE id = ?"; //00048722 query de actualización
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setString(1, newDireccion); //00048722 asignación del nuevo valor direccion
            pstmt.setString(2, newTelefono); //00048722 asignación del nuevo valor telefono
            pstmt.setInt(3, id); //00048722 asignación del valor id
            int rowsAffected = pstmt.executeUpdate(); //00048722 ejecución de la query
            if (rowsAffected > 0) { //00048722 verificación si hubo registros afectados
                JOptionPane.showMessageDialog(null, "Cliente con ID " + id + " actualizado con éxito."); //00048722 mensaje de éxito en la actualización
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún cliente con ID " + id + "."); //00048722 mensaje de error si no se encontró el registro
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + e.getMessage()); //00048722 mensaje de error en la actualización
        }
    }

    public void updateTarjeta(int id, Date newFechaExpiracion, String newFacilitador, String newTipoTarjeta) { //00048722 método para actualizar una tarjeta
        String query = "UPDATE Tarjetas SET fecha_expiracion = ?, facilitador = ?, tipo_tarjeta = ? WHERE id = ?"; //00048722 query de actualización
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setDate(1, newFechaExpiracion); //00048722 asignación del nuevo valor fechaExpiracion
            pstmt.setString(2, newFacilitador); //00048722 asignación del nuevo valor facilitador
            pstmt.setString(3, newTipoTarjeta); //00048722 asignación del nuevo valor tipoTarjeta
            pstmt.setInt(4, id); //00048722 asignación del valor id
            int rowsAffected = pstmt.executeUpdate(); //00048722 ejecución de la query
            if (rowsAffected > 0) { //00048722 verificación si hubo registros afectados
                JOptionPane.showMessageDialog(null, "Tarjeta con ID " + id + " actualizada con éxito."); //00048722 mensaje de éxito en la actualización
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna tarjeta con ID " + id + "."); //00048722 mensaje de error si no se encontró el registro
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la tarjeta: " + e.getMessage()); //00048722 mensaje de error en la actualización
        }
    }

    public List<String> innerJoin() { //00048722 método para realizar un INNER JOIN
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "INNER JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "INNER JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id"; //00048722 query del INNER JOIN
        return executeJoinQuery(query); //00048722 ejecución de la query del JOIN
    }

    public List<String> leftJoin() { //00048722 método para realizar un LEFT JOIN
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "LEFT JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "LEFT JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id"; //00048722 query del LEFT JOIN
        return executeJoinQuery(query); //00048722 ejecución de la query del JOIN
    }

    public List<String> rightJoin() { //00048722 método para realizar un RIGHT JOIN
        String query = "SELECT Clientes.nombre_completo, Transacciones.descripcion, Transacciones.monto "
                + "FROM Clientes "
                + "RIGHT JOIN Tarjetas ON Clientes.id = Tarjetas.cliente_id "
                + "RIGHT JOIN Transacciones ON Tarjetas.id = Transacciones.tarjeta_id"; //00048722 query del RIGHT JOIN
        return executeJoinQuery(query); //00048722 ejecución de la query del JOIN
    }

    private List<String> executeJoinQuery(String query) { //00048722 método para ejecutar una query de JOIN
        List<String> results = new ArrayList<>(); //00048722 creación de la lista de resultados
        try {
            Statement statement = connection.createStatement(); //00048722 creación del statement
            ResultSet resultSet = statement.executeQuery(query); //00048722 ejecución de la query
            while (resultSet.next()) { //00048722 iteración sobre los resultados
                String cliente = resultSet.getString("nombre_completo"); //00048722 obtención del valor nombre_completo
                String descripcion = resultSet.getString("descripcion"); //00048722 obtención del valor descripcion
                double monto = resultSet.getDouble("monto"); //00048722 obtención del valor monto
                results.add("Cliente: " + cliente + ", Descripción: " + descripcion + ", Monto: " + monto); //00048722 adición del resultado a la lista
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la ejecución de la consulta JOIN: " + e.getMessage()); //00048722 mensaje de error en la ejecución de la query
        }
        return results; //00048722 retorno de la lista de resultados
    }

    public void updateTransaccion(int id, Date fecha, double monto, String descripcion, int tarjetaId) { //00048722 método para actualizar una transacción
        String query = "UPDATE Transacciones SET fecha = ?, monto = ?, descripcion = ?, tarjeta_id = ? WHERE id = ?"; //00048722 query de actualización
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { //00048722 preparación del statement
            pstmt.setDate(1, fecha); //00048722 asignación del nuevo valor fecha
            pstmt.setDouble(2, monto); //00048722 asignación del nuevo valor monto
            pstmt.setString(3, descripcion); //00048722 asignación del nuevo valor descripcion
            pstmt.setInt(4, tarjetaId); //00048722 asignación del valor tarjetaId
            pstmt.setInt(5, id); //00048722 asignación del valor id
            int rowsAffected = pstmt.executeUpdate(); //00048722 ejecución de la query
            if (rowsAffected > 0) { //00048722 verificación si hubo registros afectados
                JOptionPane.showMessageDialog(null, "Transacción con ID " + id + " actualizada con éxito."); //00048722 mensaje de éxito en la actualización
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna transacción con ID " + id + "."); //00048722 mensaje de error si no se encontró el registro
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la transacción: " + e.getMessage()); //00048722 mensaje de error en la actualización
        }
    }*/

    public void closeConnection() { //00048722 método para cerrar la conexión
        try {
            if (connection != null && !connection.isClosed()) { //00048722 verificación si la conexión no es null y no está cerrada
                connection.close(); //00048722 cierre de la conexión
                JOptionPane.showMessageDialog(null, "Conexión a la base de datos cerrada con éxito."); //00048722 mensaje de éxito en el cierre de la conexión
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión a la base de datos: " + e.getMessage()); //00048722 mensaje de error en el cierre de la conexión
        }
    }
}
