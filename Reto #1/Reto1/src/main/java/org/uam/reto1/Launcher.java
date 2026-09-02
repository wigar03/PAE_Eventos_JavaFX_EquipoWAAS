package org.uam.reto1;

import javafx.application.Application;

/**
 * Punto de entrada alternativo para el Reto #1 (Inventario de Pulpería).
 * 
 * Permite ejecutar la aplicación directamente desde entornos de desarrollo
 * sin requerir argumentos de máquina virtual para los módulos de JavaFX.
 * 
 * @author Equipo WAAS
 */
public class Launcher {

    /**
     * Lanza la aplicación delegando en {@link HelloApplication}.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
