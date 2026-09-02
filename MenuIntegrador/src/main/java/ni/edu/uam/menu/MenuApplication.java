package ni.edu.uam.menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación principal del Menú Integrador de Retos.
 * 
 * Se encarga de instanciar el escenario principal (Stage), cargar el diseño visual
 * FXML y desplegar la ventana inicial con el catálogo de retos disponibles.
 * 
 * @author Equipo WAAS
 */
public class MenuApplication extends Application {

    /**
     * Punto de entrada del ciclo de vida de la aplicación JavaFX.
     * Carga el archivo FXML del menú, configura dimensiones mínimas y muestra la ventana.
     * 
     * @param stage Escenario principal provisto por la plataforma JavaFX.
     * @throws IOException Si ocurre un error al leer el archivo menu-view.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista visual FXML del menú integrador
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 980, 680);

        // Configuración de la ventana principal
        stage.setTitle("Sistema Integrador de Retos JavaFX - Equipo WAAS");
        stage.setMinWidth(850);
        stage.setMinHeight(580);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método de respaldo para lanzar la aplicación directamente si no se usa Launcher.
     * 
     * @param args Argumentos de arranque.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
