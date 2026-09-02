package ni.edu.uam.menu;

import javafx.application.Application;

/**
 * Punto de entrada alternativo para la ejecucion del Menu Integrador.
 * <p>
 * Esta clase no hereda directamente de {@link Application}, lo que permite
 * ejecutar la aplicacion JavaFX correctamente en entornos donde el sistema de
 * modulos o el empaquetado JAR requieran un lanzador independiente sin
 * verificaciones estrictas de componentes JavaFX en tiempo de arranque.
 */
public class Launcher {

    /**
     * Metodo principal que inicia la aplicacion JavaFX delegando en MenuApplication.
     *
     * @param args Argumentos de linea de comandos pasados al programa.
     */
    public static void main(String[] args) {
        Application.launch(MenuApplication.class, args);
    }
}
