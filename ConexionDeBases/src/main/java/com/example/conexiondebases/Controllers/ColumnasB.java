package com.example.conexiondebases.Controllers;

public class ColumnasB { //00379422 declaración de la clase ColumnasB
    private final String idCliente; //00379422 variable final para almacenar el ID del cliente
    private final double montoMes; //00379422 variable final para almacenar el monto total gastado en el mes que queramos buscar

    //00379422 Constructor de la clase ColumnasB que inicializa las variables de instancia
    public ColumnasB(String idCliente, double montoMes) { //00379422 indicamos los parametros para la creacion del constructor
        this.idCliente = idCliente; //00379422 asignación del parámetro idCliente a la variable de instancia idCliente
        this.montoMes = montoMes; //00379422 asignación del parámetro montoMes a la variable de instancia montoMes
    }//00379422 fin del constructor

    //00379422 Método getter para obtener el ID del cliente
    public String getIdCliente() { //00379422 inicio del metodo
        return idCliente; //00379422 devuelve el valor de idCliente
    } //00379422 fin del metodo

    //00379422 Método getter para obtener el monto total gastado en el mes
    public double getMontoMes() { //00379422 inicio del metodo
        return montoMes; //00379422 devuelve el valor de montoMes
    }//00379422 fin del metodo
} //00379422 fin de la clase ColumnasB
