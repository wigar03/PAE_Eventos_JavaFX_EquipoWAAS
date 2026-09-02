package ni.edu.uam.reto2;

import javafx.application.Application;

/**
 * Punto de entrada alternativo para la ejecucion del Reto #2 (Gestion de Lotes).
 * <p>
 * Permite ejecutar la aplicacion de manera directa evitando restricciones del modulo
 * JavaFX en tiempo de compilacion y empaquetado.
 */
public class Launcher {

    /**
     * Metodo principal que inicia LoteApplication.
     *
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(LoteApplication.class, args);
    }
}
