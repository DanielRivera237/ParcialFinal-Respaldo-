package com.example.conexiondebases.Controllers;

public class ColumnasA { //00379422 declaración de la clase ColumnasA
    private final String idCliente; //00379422 variable final para almacenar el ID del cliente
    private final String nombres; //00379422 variable final para almacenar los nombres del cliente
    private final String apellidos; //00379422 variable final para almacenar los apellidos del cliente
    private final double Monto; //00379422 variable final para almacenar el monto de la transacción
    private final int idTransaccion; //00379422 variable final para almacenar el ID de la transacción
    private final String descripcion; //00379422 variable final para almacenar la descripción de la transacción

    //00379422 Constructor de la clase ColumnasA que inicializa todas las variables de instancia
    public ColumnasA(String idCliente, String nombres, String apellidos, int idTransaccion, double Monto, String descripcion) { //00379422 ponemos los parametros para la declaracion del constructor
        this.idCliente = idCliente; //00379422 asignación del parámetro idCliente a la variable de instancia idCliente
        this.nombres = nombres; //00379422 asignación del parámetro nombres a la variable de instancia nombres
        this.apellidos = apellidos; //00379422 asignación del parámetro apellidos a la variable de instancia apellidos
        this.idTransaccion = idTransaccion; //00379422 asignación del parámetro idTransaccion a la variable de instancia idTransaccion
        this.Monto = Monto; //00379422 asignación del parámetro Monto a la variable de instancia Monto
        this.descripcion = descripcion; //00379422 asignación del parámetro descripcion a la variable de instancia descripcion
    } //00379422 fin del constructor

    //00379422 Método getter para obtener el ID del cliente
    public String getIdCliente() {
        return idCliente; //00379422 devuelve el valor de idCliente
    }

    //00379422 Método getter para obtener los nombres del cliente
    public String getNombres() {
        return nombres; //00379422 devuelve el valor de nombres
    }

    //00379422 Método getter para obtener los apellidos del cliente
    public String getApellidos() {
        return apellidos; //00379422 devuelve el valor de apellidos
    }

    //00379422 Método getter para obtener el monto de la transacción
    public double getMonto() {
        return Monto; //00379422 devuelve el valor de Monto
    }

    //00379422 Método getter para obtener el ID de la transacción
    public int getIdTransaccion() {
        return idTransaccion; //00379422 devuelve el valor de idTransaccion
    }

    //00379422 Método getter para obtener la descripción de la transacción
    public String getDescripcion() {
        return descripcion; //00379422 devuelve el valor de descripcion
    }
} //00379422 fin de la clase ColumnasA
