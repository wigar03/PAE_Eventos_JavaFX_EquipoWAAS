package ni.edu.uam.reto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.reto2.controllers.LoteController;

import java.io.IOException;

/**
 * Aplicación principal del Reto #2: Cooperativa y Gestión de Lotes Agrícolas.
 * 
 * Configura el escenario JavaFX, carga la vista FXML lote-view.fxml y enlaza
 * el callback de navegación hacia el Menú Principal si se ejecuta de forma integrada.
 * 
 * @author Equipo WAAS
 */
public class LoteApplication extends Application {

    // Callback opcional suministrado por el Menú Integrador para retornar al menú principal
    private static Runnable onVolverAlMenuGlobal;

    /**
     * Establece el callback de retorno al Menú Integrador.
     * 
     * @param onVolverAlMenu Acción a ejecutar cuando se presione el botón de retorno.
     */
    public static void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        onVolverAlMenuGlobal = onVolverAlMenu;
    }

    /**
     * Inicializa la interfaz gráfica del Reto 2 en el escenario especificado.
     * 
     * @param stage Escenario en el cual presentar la vista.
     * @throws IOException Si ocurre un problema al cargar lote-view.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la plantilla visual FXML desde el paquete local
        FXMLLoader fxmlLoader = new FXMLLoader(LoteApplication.class.getResource("lote-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Inyecta el callback de retorno al controlador si fue provisto
        LoteController controller = fxmlLoader.getController();
        if (controller != null && onVolverAlMenuGlobal != null) {
            controller.setOnVolverAlMenu(onVolverAlMenuGlobal);
        }

        // Configuración de la ventana
        stage.setTitle("Sistema de Cooperativa - Reto 2");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    /**
     * Método de arranque en ejecución directa autónoma.
     * 
     * @param args Argumentos de arranque.
     */
    public static void main(String[] args) {
        launch();
    }
}