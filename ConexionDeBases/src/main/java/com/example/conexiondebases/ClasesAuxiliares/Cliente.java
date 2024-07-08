package com.example.conexiondebases.ClasesAuxiliares;

<<<<<<< HEAD
public class Cliente { //00104923 Definición de la clase Cliente
    private String idCliente; //00104923 ID del cliente
    private String nombres; //00104923 Nombres del cliente
    private String apellidos; //00104923 Apellidos del cliente
    private String direccion; //00104923 Dirección del cliente
    private String telefono; //00104923 Teléfono del cliente

    //00104923 Constructor con todos los campos
    public Cliente(String idCliente, String nombres, String apellidos, String direccion, String telefono) { //00104923 Constructor de la clase Cliente
        this.idCliente = idCliente; //00104923 Asigna el ID del cliente
        this.nombres = nombres; //00104923 Asigna los nombres del cliente
        this.apellidos = apellidos; //00104923 Asigna los apellidos del cliente
        this.direccion = direccion; //00104923 Asigna la dirección del cliente
        this.telefono = telefono != null ? telefono : "No especificado"; //00104923 Asigna el teléfono del cliente o un valor por defecto
    }

    //00104923 Getters y Setters
    public String getIdCliente() { //00104923 Getter para idCliente
        return idCliente; //00104923 Retorna el ID del cliente
    }

    public void setIdCliente(String idCliente) { //00104923 Setter para idCliente
        this.idCliente = idCliente; //00104923 Asigna el ID del cliente
    }

    public String getNombres() { //00104923 Getter para nombres
        return nombres; //00104923 Retorna los nombres del cliente
    }

    public void setNombres(String nombres) { //00104923 Setter para nombres
        this.nombres = nombres; //00104923 Asigna los nombres del cliente
    }

    public String getApellidos() { //00104923 Getter para apellidos
        return apellidos; //00104923 Retorna los apellidos del cliente
    }

    public void setApellidos(String apellidos) { //00104923 Setter para apellidos
        this.apellidos = apellidos; //00104923 Asigna los apellidos del cliente
    }

    public String getDireccion() { //00104923 Getter para direccion
        return direccion; //00104923 Retorna la dirección del cliente
    }

    public void setDireccion(String direccion) { //00104923 Setter para direccion
        this.direccion = direccion; //00104923 Asigna la dirección del cliente
    }

    public String getTelefono() { //00104923 Getter para telefono
        return telefono; //00104923 Retorna el teléfono del cliente
    }

    public void setTelefono(String telefono) { //00104923 Setter para telefono
        this.telefono = telefono; //00104923 Asigna el teléfono del cliente
    }

    //00104923 Método toString para una representación en texto del objeto Cliente
    @Override
    public String toString() { //00104923 Sobrescribe el método toString
        return "Cliente{" + //00104923 Comienza la representación en texto del objeto Cliente
                "idCliente='" + idCliente + '\'' + //00104923 Añade el ID del cliente
                ", nombres='" + nombres + '\'' + //00104923 Añade los nombres del cliente
                ", apellidos='" + apellidos + '\'' + //00104923 Añade los apellidos del cliente
                ", direccion='" + direccion + '\'' + //00104923 Añade la dirección del cliente
                ", telefono='" + telefono + '\'' + //00104923 Añade el teléfono del cliente
                '}'; //00104923 Cierra la representación en texto del objeto Cliente
    }
}
=======
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

>>>>>>> Daniel_ReporteD
