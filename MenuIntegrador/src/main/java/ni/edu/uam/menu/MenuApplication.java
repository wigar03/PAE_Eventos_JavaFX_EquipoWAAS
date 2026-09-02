package ni.edu.uam.menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicacion JavaFX para el Menu Integrador.
 * <p>
 * Se encarga de inicializar el ciclo de vida de la aplicacion, cargar la interfaz
 * grafica FXML ({@code menu-view.fxml}), configurar la escena inicial con sus
 * dimensiones minimas y presentar la ventana principal al usuario.
 * Desde este menu central se navega hacia los Retos 1, 2 y 3.
 */
public class MenuApplication extends Application {

    /**
     * Metodo del ciclo de vida de JavaFX invocado al iniciar la aplicacion.
     * Carga el archivo FXML del menu principal, crea la escena y configura el escenario (Stage).
     *
     * @param stage Escenario primario proporcionado por la plataforma JavaFX.
     * @throws IOException Si ocurre un error al cargar el archivo menu-view.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Carga la jerarquia de componentes visuales definida en el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(MenuApplication.class.getResource("menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 980, 680);

        // Configuracion de propiedades de la ventana principal
        stage.setTitle("Sistema Integrador de Retos JavaFX - Equipo WAAS");
        stage.setMinWidth(850);
        stage.setMinHeight(580);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo de arranque estandar de Java.
     *
     * @param args Argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
