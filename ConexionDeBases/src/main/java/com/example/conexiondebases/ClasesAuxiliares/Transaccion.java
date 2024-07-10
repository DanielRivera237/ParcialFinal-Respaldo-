package com.example.conexiondebases.ClasesAuxiliares;

import java.math.BigDecimal;
import java.util.Date;

public class Transaccion {
    private int idTransaccion; // ID de la transacción
    private Date fechaCompra; // Fecha de la compra
    private BigDecimal totalGastado; // Total gastado en la compra
    private String descripcionCompra; // Descripción de la compra
    private String numeroTarjeta; // Número de la tarjeta utilizada

    // Constructor con todos los campos
    public Transaccion(int idTransaccion, Date fechaCompra, BigDecimal totalGastado, String descripcionCompra, String numeroTarjeta) {
        this.idTransaccion = idTransaccion;
        this.fechaCompra = fechaCompra;
        this.totalGastado = totalGastado;
        this.descripcionCompra = descripcionCompra != null ? descripcionCompra : "No especificado"; // Valor por defecto
        this.numeroTarjeta = numeroTarjeta;
    }

    // Getters y Setters
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }

    public String getDescripcionCompra() {
        return descripcionCompra;
    }

    public void setDescripcionCompra(String descripcionCompra) {
        this.descripcionCompra = descripcionCompra;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    // Método toString para una representación en texto del objeto Transaccion
    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion=" + idTransaccion +
                ", fechaCompra=" + fechaCompra +
                ", totalGastado=" + totalGastado +
                ", descripcionCompra='" + descripcionCompra + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                '}';
    }
}
