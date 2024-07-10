package com.example.conexiondebases.ClasesAuxiliares.Chain;
import com.example.conexiondebases.ClasesAuxiliares.InfoInsertar;

public interface Handler { //00104923 Define una interfaz llamada Handler
    void setNext(Handler next); //00104923 Define un método para establecer el siguiente manejador en la cadena
    boolean handle(InfoInsertar info); //00104923 Define un método para manejar la información de inserción
}