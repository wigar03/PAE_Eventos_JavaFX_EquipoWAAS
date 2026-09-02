package org.uam.reto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación principal del Reto #3: Tienda de Artesanías Nicaragüenses.
 * 
 * Configura y despliega el escenario con la interfaz visual provista de MenuBar,
 * ToolBar y atajos, e inyecta la navegación de retorno hacia el Menú Integrador.
 * 
 * @author Equipo WAAS
 */
public class HelloApplication extends Application {

    // Acción de retorno hacia el Menú Integrador
    private static Runnable onVolverAlMenuGlobal;

    /**
     * Define el callback que se invocará al regresar al menú principal.
     * 
     * @param onVolverAlMenu Acción de navegación.
     */
    public static void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        onVolverAlMenuGlobal = onVolverAlMenu;
    }

    /**
     * Inicializa la interfaz gráfica del Reto 3 en el escenario provisto.
     * 
     * @param stage Escenario donde presentar la vista.
     * @throws IOException Si ocurre un problema al cargar hello-view.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista FXML de la tienda de artesanías
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 920, 580);

        // Inyecta el callback de retorno si fue definido desde el menú
        HelloController controller = fxmlLoader.getController();
        if (controller != null && onVolverAlMenuGlobal != null) {
            controller.setOnVolverAlMenu(onVolverAlMenuGlobal);
        }

        // Configuración de la ventana
        stage.setTitle("Tienda de Artesanías Nicaragüenses - Reto #3");
        stage.setMinWidth(750);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método para ejecución directa autónoma.
     * 
     * @param args Argumentos de arranque.
     */
    public static void main(String[] args) {
        launch();
    }
}
