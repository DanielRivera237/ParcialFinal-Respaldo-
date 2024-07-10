package com.example.conexiondebases.ClasesAuxiliares.Chain;

import com.example.conexiondebases.ClasesAuxiliares.InfoInsertar;
import com.example.conexiondebases.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HandlerTarjetaDatos implements Handler { //00104923 Define la clase HandlerTarjetaDatos que implementa la interfaz Handler
    private Handler siguienteHandler; //00104923 Declaración del siguiente manejador en la cadena
    private Conexion conexion; //00104923 Declaración de la variable conexión

    public HandlerTarjetaDatos() { //00104923 Constructor de la clase HandlerTarjetaDatos
        this.conexion = Conexion.getInstancia("root", "**CssisnotasC#Punk**", "bancocentralnlogonia", "localhost", "3306"); //00104923 Inicializa la conexión con la base de datos
    }

    @Override
    public void setNext(Handler next) { //00104923 Implementación del método setNext de la interfaz Handler
        this.siguienteHandler = next; //00104923 Asigna el siguiente manejador en la cadena
    }

    @Override
    public boolean handle(InfoInsertar info) { //00104923 Implementación del método handle de la interfaz Handler
        String tarjetaConsulta = "SELECT numero, fecha_caducidad, tipo, facilitador, id_cliente FROM Tarjeta WHERE numero = ? AND fecha_caducidad = ? AND tipo = ? AND facilitador = ? AND id_cliente = ?"; //00104923 Consulta SQL para buscar una tarjeta con los datos específicos

        try { //00104923 Inicio del bloque try para manejar excepciones

            PreparedStatement preparedStatement = conexion.getConnection().prepareStatement(tarjetaConsulta); //00104923 Prepara la consulta SQL

            preparedStatement.setString(1, info.getNumeroTarjeta()); //00104923 Establece el primer parámetro de la consulta
            preparedStatement.setDate(2, info.getFechaVencimiento()); //00104923 Establece el segundo parámetro de la consulta
            preparedStatement.setString(3, info.getTipoTarjeta()); //00104923 Establece el tercer parámetro de la consulta
            preparedStatement.setString(4, info.getFacilitador()); //00104923 Establece el cuarto parámetro de la consulta
            preparedStatement.setString(5, info.getIdCliente()); //00104923 Establece el quinto parámetro de la consulta

            try (ResultSet resultSet = preparedStatement.executeQuery()) { //00104923 Ejecuta la consulta y obtiene el resultado
                if (resultSet.next()) { //00104923 Verifica si hay un resultado
                    return true; //00104923 Si se encuentra una fila, retorna verdadero
                } else { //00104923 Si no se encuentra ninguna fila
                    return false; //00104923 Retorna falso
                }
            }

        } catch (Exception e) { //00104923 Maneja excepciones
            e.printStackTrace(); //00104923 Imprime el stack trace de la excepción
            return false; //00104923 Retorna false en caso de excepción
        }
    }
}
