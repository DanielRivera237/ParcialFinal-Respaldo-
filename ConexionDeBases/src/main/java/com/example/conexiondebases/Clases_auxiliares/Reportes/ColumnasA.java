package com.example.conexiondebases.Clases_auxiliares.Reportes;

public class ColumnasA {
    private final String idCliente;
    private final String nombres;
    private final String apellidos;
    private final double  Monto;
    private final int idTransaccion;
    private final String descripcion;


    public ColumnasA(String idCliente, String nombres, String apellidos, int idTransaccion, double Monto, String descripcion) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.idTransaccion = idTransaccion;
        this.Monto = Monto;
        this.descripcion = descripcion;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public double getMonto() {
        return Monto;
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}