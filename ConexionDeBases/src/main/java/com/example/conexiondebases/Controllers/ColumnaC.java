package com.example.conexiondebases.Controllers;

public class ColumnaC { //00083223 declaracion de la clase ColumnaC
    private final String idCliente; //00083223  almacena el id del cliente
    private final String tarjetaCredito; //00083223  almacena el numero de la tarjeta de credito
    private final String tarjetaDebito; //00083223  almacena el numero de la tarjeta de debito

    public ColumnaC(String idCliente, String tarjetaCredito, String tarjetaDebito) { //00083223 constructor de la clase
        this.idCliente = idCliente; //00083223 inicializa el id del cliente
        this.tarjetaCredito = tarjetaCredito; //00083223 inicializa el número de la tarjeta de credito
        this.tarjetaDebito = tarjetaDebito; //00083223 inicializa el número de la tarjeta de debito
    }//00083223 fin del constructor de la clase

    public String getIdCliente() { //00083223 metodo para obtener el ID del cliente
        return idCliente; //00083223 devuelve el id del cliente
    }//00083223 fin del metodo getIdCliente()

    public String getTarjetaCredito() { //00083223 metodo para obtener el número de la tarjeta de crédito
        return tarjetaCredito; //00083223 devuelve el numero de la tarjeta de credito
    } //00083223 fin del metodo getTarjetaCredito()

    public String getTarjetaDebito() { //00083223 metodo para obtener el número de la tarjeta de débito
        return tarjetaDebito; //00083223 devuelve el número de la tarjeta de debito
    }//00083223 fin del metodo getTarjetaDebito()
}//00083223 fin de la clase ColumnaC