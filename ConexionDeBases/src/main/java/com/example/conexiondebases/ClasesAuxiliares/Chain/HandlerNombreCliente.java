package com.example.conexiondebases.ClasesAuxiliares.Chain;

import com.example.conexiondebases.ClasesAuxiliares.InfoInsertar;
import com.example.conexiondebases.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HandlerNombreCliente implements Handler { //00104923 Define la clase HandlerNombreCliente que implementa la interfaz Handler
    private Handler siguienteHandler; //00104923 Declaración del siguiente manejador en la cadena
    private Conexion conexion; //00104923 Declaración de la variable conexión

    public HandlerNombreCliente() { //00104923 Constructor de la clase HandlerNombreCliente
        this.conexion = Conexion.getInstancia("root", "**CssisnotasC#Punk**", "bancocentralnlogonia", "localhost", "3306"); //00104923 Inicializa la conexión con la base de datos
    }

    @Override
    public void setNext(Handler next) { //00104923 Implementación del método setNext de la interfaz Handler
        this.siguienteHandler = next; //00104923 Asigna el siguiente manejador en la cadena
    }

    @Override
    public boolean handle(InfoInsertar info) { //00104923 Implementación del método handle de la interfaz Handler
        String nombresConsulta = "SELECT id_cliente FROM Cliente WHERE nombres = ? AND apellidos = ?;"; //00104923 Consulta SQL para buscar un cliente por nombres y apellidos

        try  { //00104923 Inicio del bloque try para manejar excepciones SQL

            PreparedStatement preparedStatement = conexion.getConnection().prepareStatement(nombresConsulta); //00104923 Prepara la consulta SQL
            preparedStatement.setString(1, info.getNombre()); //00104923 Establece el primer parámetro de la consulta
            preparedStatement.setString(2, info.getApellido()); //00104923 Establece el segundo parámetro de la consulta

            try (ResultSet resultSet = preparedStatement.executeQuery()) { //00104923 Ejecuta la consulta y obtiene el resultado
                if (resultSet.next()) { //00104923 Verifica si hay un resultado
                    String idCliente = resultSet.getString("id_cliente"); //00104923 Obtiene el idCliente del resultado
                    info.setIdCliente(idCliente); //00104923 Establece el idCliente en el objeto InfoInsertar
                } else { //00104923 Si no hay coincidencias
                    return false; //00104923 Detiene la cadena y retorna false
                }
            }

            if (siguienteHandler == null) { //00104923 Verifica si hay un siguiente manejador en la cadena
                return true; //00104923 Si no hay, retorna true
            } else { //00104923 Si hay un siguiente manejador
                return siguienteHandler.handle(info); //00104923 Llama al método handle del siguiente manejador
            }

        } catch (SQLException e) { //00104923 Maneja excepciones SQL
            e.printStackTrace(); //00104923 Imprime el stack trace de la excepción
            return false; //00104923 Retorna false en caso de excepción
        }
    }
}

