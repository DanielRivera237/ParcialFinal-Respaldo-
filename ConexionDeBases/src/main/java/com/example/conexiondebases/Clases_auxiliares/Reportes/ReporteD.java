package com.example.conexiondebases.Clases_auxiliares.Reportes;

import java.math.BigDecimal;
public class ReporteD { // 00048722: Declara la clase pública ReporteD
    private String cliente; // 00048722: Declara una variable privada de tipo String llamada cliente
    private int cantidadCompras; // 00048722: Declara una variable privada de tipo int llamada cantidadCompras
    private BigDecimal totalGastado; // 00048722: Declara una variable privada de tipo BigDecimal llamada totalGastado

    public ReporteD(String cliente, int cantidadCompras, BigDecimal totalGastado) { // 00048722: Declara el constructor público de la clase ReporteD que inicializa las variables cliente, cantidadCompras y totalGastado
        this.cliente = cliente; // 00048722: Asigna el parámetro cliente a la variable de instancia cliente
        this.cantidadCompras = cantidadCompras; // 00048722: Asigna el parámetro cantidadCompras a la variable de instancia cantidadCompras
        this.totalGastado = totalGastado; // 00048722: Asigna el parámetro totalGastado a la variable de instancia totalGastado
    } // 00048722: Cierra el constructor

    public String getCliente() { // 00048722: Método público que devuelve el valor de la variable cliente
        return cliente; // 00048722: Retorna el valor de la variable cliente
    }

    public void setCliente(String cliente) { // 00048722: Método público que asigna un valor a la variable cliente
        this.cliente = cliente; // 00048722: Asigna el parámetro cliente a la variable de instancia cliente
    }

    public int getCantidadCompras() { // 00048722: Método público que devuelve el valor de la variable cantidadCompras
        return cantidadCompras; // 00048722: Retorna el valor de la variable cantidadCompras
    }

    public void setCantidadCompras(int cantidadCompras) { // 00048722: Método público que asigna un valor a la variable cantidadCompras
        this.cantidadCompras = cantidadCompras; // 00048722: Asigna el parámetro cantidadCompras a la variable de instancia cantidadCompras
    }

    public BigDecimal getTotalGastado() { // 00048722: Método público que devuelve el valor de la variable totalGastado
        return totalGastado; // 00048722: Retorna el valor de la variable totalGastado
    }

    public void setTotalGastado(BigDecimal totalGastado) { // 00048722: Método público que asigna un valor a la variable totalGastado
        this.totalGastado = totalGastado; // 00048722: Asigna el parámetro totalGastado a la variable de instancia totalGastado
    }
}

