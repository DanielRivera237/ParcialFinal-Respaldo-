package com.example.conexiondebases.ClasesAuxiliares;

import java.math.BigDecimal;
import java.sql.Date;

public class InfoInsertar {
    private String idCliente;
    private String nombre;
    private String apellido;
    private String descripcion;
    private String numeroTarjeta;
    private String fechaVencimiento;
    private BigDecimal totalPagar;
    private String tipoTarjeta;
    private String facilitador;

    public InfoInsertar(String nombre, String apellido, String descripcion, String numeroTarjeta, BigDecimal totalPagar, String fechaNacimiento, String tipoTarjeta, String facilitador) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = descripcion;
        this.numeroTarjeta = numeroTarjeta;
        this.totalPagar = totalPagar;
        this.fechaVencimiento = fechaNacimiento;
        this.tipoTarjeta = tipoTarjeta;
        this.facilitador = facilitador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Date getFechaVencimiento() {
       return  Date.valueOf(fechaVencimiento);
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
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
}
