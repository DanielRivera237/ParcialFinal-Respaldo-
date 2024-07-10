package com.example.conexiondebases.ClasesAuxiliares;


public class Cliente {
    private String idCliente; // ID del cliente
    private String nombres; // Nombres del cliente
    private String apellidos; // Apellidos del cliente
    private String direccion; // Dirección del cliente
    private String telefono; // Teléfono del cliente

    // Constructor con todos los campos
    public Cliente(String idCliente, String nombres, String apellidos, String direccion, String telefono) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono != null ? telefono : "No especificado"; // Valor por defecto
    }

    // Getters y Setters
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Método toString para una representación en texto del objeto Cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente='" + idCliente + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}

