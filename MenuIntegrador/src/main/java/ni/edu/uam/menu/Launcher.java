package ni.edu.uam.menu;

import javafx.application.Application;

/**
 * Clase lanzadora (Bootstrap) del Menú Integrador.
 * 
 * En Java modular (Java 9+), ejecutar directamente una clase que hereda de Application
 * desde ciertos entornos de desarrollo (IDEs) puede provocar que la JVM reclame la ausencia
 * de componentes de JavaFX en tiempo de ejecución. Esta clase no hereda de Application,
 * sirviendo como punto de entrada convencional para delegar el arranque a {@link MenuApplication}.
 * 
 * @author Equipo WAAS
 */
public class Launcher {

    /**
     * Método principal que inicia la aplicación JavaFX.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(MenuApplication.class, args);
    }
}
