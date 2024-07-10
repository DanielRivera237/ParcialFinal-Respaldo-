package com.example.conexiondebases.ClasesAuxiliares.Alertas;

import javafx.scene.control.Alert;

public interface Alertas { //00104923 Define una interfaz llamada Alertas
    void tipoAlerta(String mensaje, int tipo); //00104923 Define un método para mostrar una alerta de un tipo específico
    void alertaExito(String mensaje, Alert alerta); //00104923 Define un método para mostrar una alerta de éxito
    void alertaError(String mensaje, Alert alerta); //00104923 Define un método para mostrar una alerta de error
}

