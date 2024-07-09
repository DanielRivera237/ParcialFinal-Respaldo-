package com.example.conexiondebases.Controllers;

public class ColumnasB {
    private final String idCliente;
    private final double montoMes;

    public ColumnasB(String idCliente, double montoMes) {
        this.idCliente = idCliente;
        this.montoMes = montoMes;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public double getMontoMes() {
        return montoMes;
    }
}