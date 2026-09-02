package ni.edu.uam.reto2;

import javafx.application.Application;

/**
 * Clase lanzadora para el Reto #2 (Cooperativa Agrícola - Control de Lotes).
 * 
 * Permite la ejecución directa del proyecto desde entornos de desarrollo (IDEs)
 * sin requerir parámetros de máquina virtual adicionales para los módulos de JavaFX.
 * 
 * @author Equipo WAAS
 */
public class Launcher {

    /**
     * Punto de entrada principal que delega a {@link LoteApplication}.
     * 
     * @param args Argumentos de la consola.
     */
    public static void main(String[] args) {
        Application.launch(LoteApplication.class, args);
    }
}
