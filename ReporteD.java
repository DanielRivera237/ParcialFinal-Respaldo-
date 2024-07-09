package com.example.conexiondebases.Controllers;

import java.math.BigDecimal;

public class ReporteD {
    private String cliente;
    private int cantidadCompras;
    private BigDecimal totalGastado;

    public ReporteD(String cliente, int cantidadCompras, BigDecimal totalGastado) {
        this.cliente = cliente;
        this.cantidadCompras = cantidadCompras;
        this.totalGastado = totalGastado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getCantidadCompras() {
        return cantidadCompras;
    }

    public void setCantidadCompras(int cantidadCompras) {
        this.cantidadCompras = cantidadCompras;
    }

    public BigDecimal getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(BigDecimal totalGastado) {
        this.totalGastado = totalGastado;
    }


}
