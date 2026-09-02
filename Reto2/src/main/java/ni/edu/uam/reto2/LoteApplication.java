package ni.edu.uam.reto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.reto2.controllers.LoteController;

import java.io.IOException;

/**
 * Clase principal de la aplicacion JavaFX para el Reto #2 (Cooperativa de Granos).
 * <p>
 * Gestiona el ciclo de vida de la ventana del Reto 2, cargando el archivo FXML
 * ({@code lote-view.fxml}), inyectando el callback de retorno al Menu Integrador
 * en caso de ejecutarse desde el menu central, y configurando la ventana (Stage).
 */
public class LoteApplication extends Application {

    // Callback global para retornar al Menu Integrador
    private static Runnable onVolverAlMenuGlobal;

    /**
     * Permite asignar el callback de navegacion desde el Menu Integrador u otra clase externa.
     *
     * @param onVolverAlMenu Accion ejecutable que se activa al pulsar "Volver al Menu".
     */
    public static void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        onVolverAlMenuGlobal = onVolverAlMenu;
    }

    /**
     * Inicializa y despliega la ventana principal de gestion de lotes.
     *
     * @param stage Escenario primario de JavaFX para esta ventana.
     * @throws IOException Si ocurre un fallo en la lectura del archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la interfaz visual definida en lote-view.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(LoteApplication.class.getResource("lote-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Obtiene la instancia del controlador para inyectarle el callback de navegacion
        LoteController controller = fxmlLoader.getController();
        if (controller != null && onVolverAlMenuGlobal != null) {
            controller.setOnVolverAlMenu(onVolverAlMenuGlobal);
        }

        // Configuracion visual del Stage
        stage.setTitle("Sistema de Cooperativa - Reto 2");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    /**
     * Metodo estandar de arranque en Java.
     *
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}