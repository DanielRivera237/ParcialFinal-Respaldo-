package com.example.conexiondebases;

import javax.swing.*;
import java.sql.*;


public class Conexion {
    private Connection connection = null; //00048722 declaración de la variable de conexión

    private String usuario; //00048722 declaración de la variable usuario
    private String clave; //00048722 declaración de la variable clave
    private String dataBase; //00048722 declaración de la variable dataBase
    private String IP; //00048722 declaración de la variable IP
    private String port; //00048722 declaración de la variable port

    private static Conexion instancia;

    private Conexion(String usuario, String clave, String dataBase, String IP, String port) {
        this.usuario = usuario;
        this.clave = clave;
        this.dataBase = dataBase;
        this.IP = IP;
        this.port = port;
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
