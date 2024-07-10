package com.example.conexiondebases;

import javax.swing.*;
import java.sql.*;

public class Conexion { //00048722 Declaración de la clase
    private Connection connection = null; //00048722 declaración de la variable de conexión

    private String usuario; //00048722 declaración de la variable usuario
    private String clave; //00048722 declaración de la variable clave
    private String dataBase; //00048722 declaración de la variable dataBase
    private String IP; //00048722 declaración de la variable IP
    private String port; //00048722 declaración de la variable port

    private static Conexion instancia; //00048722 declaración de la variable instancia estática

    private Conexion(String usuario, String clave, String dataBase, String IP, String port) { //00048722 constructor privado de la clase
        this.usuario = usuario; //00048722 asignación de la variable usuario
        this.clave = clave; //00048722 asignación de la variable clave
        this.dataBase = dataBase; //00048722 asignación de la variable dataBase
        this.IP = IP; //00048722 asignación de la variable IP
        this.port = port; //00048722 asignación de la variable port
    }

    public static Conexion getInstancia(String usuario, String clave, String dataBase, String IP, String port) { //00048722 método para obtener la instancia única de la clase
        if (instancia == null) { //00048722 verificación si la instancia es null
            instancia = new Conexion(usuario, clave, dataBase, IP, port); //00048722 creación de la instancia
        }
        return instancia; //00048722 retorno de la instancia
    }

    public Connection getConnection() { //00048722 método para obtener la conexión
        return connection; //00048722 retorno de la variable connection
    }

    public boolean connect() { //00048722 método para establecer la conexión a la base de datos
        try {
            if (IP == null || port == null || IP.isEmpty() || port.isEmpty()) { //00048722 verificación de la validez de los parámetros IP y port
                throw new IllegalArgumentException("IP y port no pueden ser nulos o vacíos."); //00048722 lanzamiento de una excepción si los parámetros son inválidos
            }
            String url = "jdbc:mysql://" + IP + ":" + port + "/" + dataBase + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"; //00048722 construcción de la URL de conexión
            Class.forName("com.mysql.cj.jdbc.Driver"); //00048722 carga del driver JDBC de MySQL
            connection = DriverManager.getConnection(url, usuario, clave); //00048722 establecimiento de la conexión
            JOptionPane.showMessageDialog(null, "Conectado"); //00048722 mensaje de éxito en la conexión
            return true; //00048722 retorno verdadero si la conexión es exitosa
        } catch (ClassNotFoundException | SQLException e) { //00048722 manejo de excepciones de clase no encontrada y SQL
            JOptionPane.showMessageDialog(null, "Error en la conexión de bases de datos: " + e.getMessage()); //00048722 mensaje de error en la conexión
            e.printStackTrace(); //00048722 impresión del stack trace de la excepción
        } catch (IllegalArgumentException e) { //00048722 manejo de excepciones de argumentos inválidos
            JOptionPane.showMessageDialog(null, "Parámetros inválidos para la conexión: " + e.getMessage()); //00048722 mensaje de error en los parámetros de conexión
            e.printStackTrace(); //00048722 impresión del stack trace de la excepción
        }
        return false; //00048722 retorno falso si la conexión falla
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
        } catch (SQLException e) { //00048722 manejo de excepciones SQL
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión a la base de datos: " + e.getMessage()); //00048722 mensaje de error en el cierre de la conexión
        }
    }
}
