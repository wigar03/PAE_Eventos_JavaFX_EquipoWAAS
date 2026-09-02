package org.uam.reto2;

import javafx.application.Application;

/**
 * Lanzador independiente para el Reto #3 (Tienda de Artesanías Nicaragüenses).
 * 
 * Permite ejecutar la aplicación directamente desde entornos de desarrollo
 * sin inconvenientes de carga de módulos en tiempo de ejecución.
 * 
 * @author Equipo WAAS
 */
public class Launcher {

    /**
     * Punto de entrada principal que delega el arranque a {@link HelloApplication}.
     * 
     * @param args Argumentos de arranque.
     */
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }
}
