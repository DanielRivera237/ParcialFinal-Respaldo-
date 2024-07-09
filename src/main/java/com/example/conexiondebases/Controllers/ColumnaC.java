package com.example.conexiondebases.Controllers;

public class ColumnaC {
    private final String idCliente;
    private final String tarjetaCredito;
    private final String tarjetaDebito;

    public ColumnaC(String idCliente, String tarjetaCredito, String tarjetaDebito) {
        this.idCliente = idCliente;
        this.tarjetaCredito = tarjetaCredito;
        this.tarjetaDebito = tarjetaDebito;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getTarjetaCredito() {
        return tarjetaCredito;
    }

    public String getTarjetaDebito() {
        return tarjetaDebito;
    }


}
