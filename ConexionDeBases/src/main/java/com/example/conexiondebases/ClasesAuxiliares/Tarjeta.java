package com.example.conexiondebases.ClasesAuxiliares;

import java.util.Date;

public class Tarjeta {
    private String numero; // Número de la tarjeta
    private Date fechaCaducidad; // Fecha de caducidad de la tarjeta
    private String tipo; // Tipo de tarjeta
    private String facilitador; // Facilitador de la tarjeta
    private String idCliente; // ID del cliente al que pertenece la tarjeta

    // Constructor con todos los campos
    public Tarjeta(String numero, Date fechaCaducidad, String tipo, String facilitador, String idCliente) {
        this.numero = numero;
        this.fechaCaducidad = fechaCaducidad;
        this.tipo = tipo;
        this.facilitador = facilitador;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    // Método toString para una representación en texto del objeto Tarjeta
    @Override
    public String toString() {
        return "Tarjeta{" +
                "numero='" + numero + '\'' +
                ", fechaCaducidad=" + fechaCaducidad +
                ", tipo='" + tipo + '\'' +
                ", facilitador='" + facilitador + '\'' +
                ", idCliente='" + idCliente + '\'' +
                '}';
    }

    public String getNumeroCensurado() {
        if (numero.length() > 4) {
            return "XXXX XXXX XXXX " + numero.substring(numero.length() - 4);
        }
        return numero;
    }
}

