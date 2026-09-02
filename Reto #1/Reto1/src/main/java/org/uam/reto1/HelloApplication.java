package org.uam.reto1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación principal del Reto #1: Inventario de Pulpería.
 * 
 * Gestiona el ciclo de vida de la ventana JavaFX, carga la vista FXML
 * y configura la navegación hacia el Menú Integrador si se invoca de forma integrada.
 * 
 * @author Equipo WAAS
 */
public class HelloApplication extends Application {

    // Acción opcional de navegación hacia el Menú Integrador
    private static Runnable onVolverAlMenuGlobal;

    /**
     * Registra el callback para volver al Menú Integrador al cerrar el reto.
     * 
     * @param onVolverAlMenu Acción a ejecutar cuando se solicite retornar.
     */
    public static void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        onVolverAlMenuGlobal = onVolverAlMenu;
    }

    /**
     * Despliega la interfaz gráfica del Reto 1 en el escenario especificado.
     * 
     * @param stage Escenario donde se presentará la interfaz.
     * @throws IOException Si ocurre un error al cargar hello-view.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la plantilla FXML desde el paquete local
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 580);

        // Inyecta el callback de navegación al controlador si fue provisto
        HelloController controller = fxmlLoader.getController();
        if (controller != null && onVolverAlMenuGlobal != null) {
            controller.setOnVolverAlMenu(onVolverAlMenuGlobal);
        }

        // Configuración de dimensiones y título de la ventana
        stage.setTitle("Inventario de Pulpería - Reto #1 (Eventos JavaFX)");
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada para ejecución individual de la aplicación.
     * 
     * @param args Argumentos de arranque.
     */
    public static void main(String[] args) {
        launch();
    }
}
