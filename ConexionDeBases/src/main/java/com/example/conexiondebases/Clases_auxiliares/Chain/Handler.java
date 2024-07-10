package com.example.conexiondebases.Clases_auxiliares.Chain;

import com.example.conexiondebases.Clases_auxiliares.InfoInsertar;

public interface Handler { //00104923 Define una interfaz llamada Handler
    void setNext(Handler next); //00104923 Define un método para establecer el siguiente manejador en la cadena
    boolean handle(InfoInsertar info); //00104923 Define un método para manejar la información de inserción
}