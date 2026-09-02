package ni.edu.uam.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Controlador de la interfaz del Menú Integrador.
 * 
 * Gestiona la apertura nativa de cada uno de los tres retos en sus propias ventanas
 * independientes, el ocultamiento y reapertura del menú principal, y los efectos visuales
 * interactivos implementados en JavaFX puro sin hojas de estilo externas.
 * 
 * @author Equipo WAAS
 */
public class MenuController {

    // Etiqueta informativa de versión de Java
    @FXML private Label lblVersionJava;

    // Botones de lanzamiento de cada reto
    @FXML private Button btnReto1;
    @FXML private Button btnReto2;
    @FXML private Button btnReto3;

    // Contenedores tipo tarjeta para cada reto
    @FXML private VBox cardReto1;
    @FXML private VBox cardReto2;
    @FXML private VBox cardReto3;

    // Referencias a las ventanas de cada reto para gestionar su ciclo de vida
    private Stage stageReto1;
    private Stage stageReto2;
    private Stage stageReto3;

    /**
     * Inicialización del controlador tras cargar el archivo FXML.
     * Configura la información del entorno y aplica efectos hover interactivos mediante JavaFX puro.
     */
    @FXML
    public void initialize() {
        // Muestra la versión actual de Java en ejecución
        lblVersionJava.setText("Java: " + System.getProperty("java.version") + " (JavaFX 21)");

        // Configuración de efectos hover dinámicos en botones (paleta #0099AB y blanco)
        configurarEfectoBoton(btnReto1);
        configurarEfectoBoton(btnReto2);
        configurarEfectoBoton(btnReto3);

        // Configuración de efectos hover en los bordes y sombras de las tarjetas
        configurarEfectoTarjeta(cardReto1);
        configurarEfectoTarjeta(cardReto2);
        configurarEfectoTarjeta(cardReto3);
    }

    /**
     * Abre de forma nativa la ventana del Reto #1 (Inventario de Pulpería).
     * Oculta el menú principal y configura el callback para reabrirlo al salir del reto.
     */
    @FXML
    protected void abrirReto1() {
        try {
            Stage menuStage = getMenuStage();

            // Creación del nuevo escenario para el Reto 1
            stageReto1 = new Stage();

            // Callback para cuando el usuario presione el botón "Volver al Menú Principal"
            org.uam.reto1.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto1.close();
                if (menuStage != null) menuStage.show();
            });

            // Si el usuario cierra la ventana con la "X", también se restaura el menú
            stageReto1.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Delegación del arranque a la clase nativa del Reto 1
            new org.uam.reto1.HelloApplication().start(stageReto1);

            // Oculta la ventana del menú principal mientras el reto esté activo
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #1", ex.getMessage());
        }
    }

    /**
     * Abre de forma nativa la ventana del Reto #2 (Cooperativa Agrícola - Control de Lotes).
     * Oculta el menú principal y configura el callback para reabrirlo al salir del reto.
     */
    @FXML
    protected void abrirReto2() {
        try {
            Stage menuStage = getMenuStage();

            // Creación del nuevo escenario para el Reto 2
            stageReto2 = new Stage();

            // Callback para cuando el usuario presione el botón "Volver al Menú Principal"
            ni.edu.uam.reto2.LoteApplication.setOnVolverAlMenu(() -> {
                stageReto2.close();
                if (menuStage != null) menuStage.show();
            });

            // Si el usuario cierra la ventana con la "X", también se restaura el menú
            stageReto2.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Delegación del arranque a la clase nativa del Reto 2
            new ni.edu.uam.reto2.LoteApplication().start(stageReto2);

            // Oculta la ventana del menú principal mientras el reto esté activo
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #2", ex.getMessage());
        }
    }

    /**
     * Abre de forma nativa la ventana del Reto #3 (Tienda de Artesanías Nicaragüenses).
     * Oculta el menú principal y configura el callback para reabrirlo al salir del reto.
     */
    @FXML
    protected void abrirReto3() {
        try {
            Stage menuStage = getMenuStage();

            // Creación del nuevo escenario para el Reto 3
            stageReto3 = new Stage();

            // Callback para cuando el usuario presione el botón "Volver al Menú Principal"
            org.uam.reto2.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto3.close();
                if (menuStage != null) menuStage.show();
            });

            // Si el usuario cierra la ventana con la "X", también se restaura el menú
            stageReto3.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Delegación del arranque a la clase nativa del Reto 3
            new org.uam.reto2.HelloApplication().start(stageReto3);

            // Oculta la ventana del menú principal mientras el reto esté activo
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #3", ex.getMessage());
        }
    }

    /**
     * Muestra un diálogo de confirmación para cerrar todas las ventanas y terminar la aplicación.
     */
    @FXML
    protected void salirDeLaAplicacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("Cerrar Sistema Integrador");
        alert.setContentText("¿Deseas cerrar el menú principal y salir de la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Cierra cualquier ventana de reto que haya quedado abierta
            if (stageReto1 != null && stageReto1.isShowing()) stageReto1.close();
            if (stageReto2 != null && stageReto2.isShowing()) stageReto2.close();
            if (stageReto3 != null && stageReto3.isShowing()) stageReto3.close();

            // Finaliza el entorno JavaFX
            Platform.exit();
        }
    }

    /**
     * Obtiene la referencia al escenario (Stage) del Menú Principal a partir de la escena del botón.
     * 
     * @return El Stage del menú principal o null si la escena aún no está disponible.
     */
    private Stage getMenuStage() {
        if (btnReto1 != null && btnReto1.getScene() != null) {
            return (Stage) btnReto1.getScene().getWindow();
        }
        return null;
    }

    /**
     * Configura el comportamiento visual interactivo de los botones en JavaFX puro.
     * Alterna entre el color base (#0099AB) y el color de realce (#007d8c) al pasar el puntero.
     * 
     * @param btn Botón al cual aplicar los estilos y eventos hover.
     */
    private void configurarEfectoBoton(Button btn) {
        String base = "-fx-background-color: #0099AB; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        String hover = "-fx-background-color: #007d8c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    /**
     * Configura el efecto hover de las tarjetas del catálogo en JavaFX puro.
     * Resalta el borde con el color distintivo (#0099AB) y expande la sombra al pasar el puntero.
     * 
     * @param card Contenedor VBox de la tarjeta.
     */
    private void configurarEfectoTarjeta(VBox card) {
        String base = "-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.08), 12, 0, 0, 4);";
        String hover = "-fx-background-color: #ffffff; -fx-border-color: #0099AB; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.25), 16, 0, 0, 6);";
        card.setStyle(base);
        card.setOnMouseEntered(e -> card.setStyle(hover));
        card.setOnMouseExited(e -> card.setStyle(base));
    }

    /**
     * Despliega un cuadro de diálogo modal informando de un error.
     * 
     * @param titulo Título de la alerta.
     * @param mensaje Mensaje descriptivo del error.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
