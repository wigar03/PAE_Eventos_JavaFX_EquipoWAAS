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
 * Controlador de la interfaz grafica del Menu Integrador.
 * <p>
 * Gestiona las acciones del menu principal, permitiendo la navegacion fluida
 * hacia los tres retos desarrollados (Reto 1: Inventario de Pulperia, Reto 2:
 * Gestion de Lotes de Granos, Reto 3: Catalogo de Artesanias).
 * Implementa mecanismos de retorno mediante callbacks ({@link Runnable}) y control
 * de visibilidad del escenario principal para una experiencia de usuario integrada.
 */
public class MenuController {

    // ==========================================
    // CONTROLES VINCULADOS CON EL FXML
    // ==========================================

    @FXML private Label lblVersionJava;
    @FXML private Button btnReto1;
    @FXML private Button btnReto2;
    @FXML private Button btnReto3;
    @FXML private VBox cardReto1;
    @FXML private VBox cardReto2;
    @FXML private VBox cardReto3;

    // Referencias a los escenarios (ventanas) secundarios de cada reto
    private Stage stageReto1;
    private Stage stageReto2;
    private Stage stageReto3;

    /**
     * Metodo de inicializacion del controlador invocado automaticamente por JavaFX
     * despues de cargar el archivo FXML y enlazar los componentes visuales.
     */
    @FXML
    public void initialize() {
        // Muestra la version actual del entorno de ejecucion de Java
        lblVersionJava.setText("Java: " + System.getProperty("java.version") + " (JavaFX 21)");

        // Aplica efectos visuales interactivos (hover) a los botones
        configurarEfectoBoton(btnReto1);
        configurarEfectoBoton(btnReto2);
        configurarEfectoBoton(btnReto3);

        // Aplica efectos visuales interactivos (hover y sombreado) a las tarjetas
        configurarEfectoTarjeta(cardReto1);
        configurarEfectoTarjeta(cardReto2);
        configurarEfectoTarjeta(cardReto3);
    }

    /**
     * Abre el Reto #1 (Inventario de Pulperia).
     * <p>
     * Oculta el menu principal y lanza la ventana del Reto 1. Configura un callback
     * para que, al presionar el boton de retorno o al cerrar la ventana, el menu
     * principal vuelva a ser visible.
     */
    @FXML
    protected void abrirReto1() {
        try {
            Stage menuStage = getMenuStage();

            stageReto1 = new Stage();

            // Callback invocado cuando el usuario pulsa "Volver al Menu" en el Reto 1
            org.uam.reto1.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto1.close();
                if (menuStage != null) menuStage.show();
            });

            // Restaura el menu si el usuario cierra la ventana directamente desde la barra de titulo
            stageReto1.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Inicia la aplicacion del Reto 1 en el nuevo escenario
            new org.uam.reto1.HelloApplication().start(stageReto1);

            // Oculta temporalmente la ventana del menu integrador
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #1", ex.getMessage());
        }
    }

    /**
     * Abre el Reto #2 (Gestion de Lotes de Granos de Cooperativa).
     * <p>
     * Oculta el menu principal y lanza la ventana del Reto 2, registrando el callback
     * de navegacion para retornar al menu integrador.
     */
    @FXML
    protected void abrirReto2() {
        try {
            Stage menuStage = getMenuStage();

            stageReto2 = new Stage();

            // Callback invocado cuando el usuario pulsa "Volver al Menu" en el Reto 2
            ni.edu.uam.reto2.LoteApplication.setOnVolverAlMenu(() -> {
                stageReto2.close();
                if (menuStage != null) menuStage.show();
            });

            // Restaura el menu si se cierra la ventana del Reto 2
            stageReto2.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Inicia la aplicacion del Reto 2 en el nuevo escenario
            new ni.edu.uam.reto2.LoteApplication().start(stageReto2);

            // Oculta temporalmente la ventana del menu integrador
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #2", ex.getMessage());
        }
    }

    /**
     * Abre el Reto #3 (Catalogo de Artesanias Nicaraguenses).
     * <p>
     * Oculta el menu principal y lanza la ventana del Reto 3, registrando el callback
     * de navegacion para retornar al menu integrador.
     */
    @FXML
    protected void abrirReto3() {
        try {
            Stage menuStage = getMenuStage();

            stageReto3 = new Stage();

            // Callback invocado cuando el usuario pulsa "Volver al Menu" en el Reto 3
            org.uam.reto2.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto3.close();
                if (menuStage != null) menuStage.show();
            });

            // Restaura el menu si se cierra la ventana del Reto 3
            stageReto3.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            // Inicia la aplicacion del Reto 3 en el nuevo escenario
            new org.uam.reto2.HelloApplication().start(stageReto3);

            // Oculta temporalmente la ventana del menu integrador
            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #3", ex.getMessage());
        }
    }

    /**
     * Muestra un cuadro de confirmacion para cerrar el sistema integrador.
     * Cierra cualquier ventana de retos abierta y finaliza la plataforma JavaFX.
     */
    @FXML
    protected void salirDeLaAplicacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("Cerrar Sistema Integrador");
        alert.setContentText("¿Deseas cerrar el menu principal y salir de la aplicacion?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Cierra escenarios secundarios si estan abiertos
            if (stageReto1 != null && stageReto1.isShowing()) stageReto1.close();
            if (stageReto2 != null && stageReto2.isShowing()) stageReto2.close();
            if (stageReto3 != null && stageReto3.isShowing()) stageReto3.close();

            // Termina la aplicacion JavaFX
            Platform.exit();
        }
    }

    /**
     * Obtiene la referencia al Stage (ventana) actual del menu principal.
     *
     * @return El Stage de la ventana actual o null si los componentes no estan adjuntos a una escena.
     */
    private Stage getMenuStage() {
        if (btnReto1 != null && btnReto1.getScene() != null) {
            return (Stage) btnReto1.getScene().getWindow();
        }
        return null;
    }

    /**
     * Configura estilos en linea y transiciones visuales de cursor y color al pasar el mouse por un boton.
     *
     * @param btn Boton al que se le aplicaran los efectos de hover.
     */
    private void configurarEfectoBoton(Button btn) {
        String base = "-fx-background-color: #0099AB; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        String hover = "-fx-background-color: #007d8c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    /**
     * Configura bordes y sombras dinámicas al pasar el cursor sobre las tarjetas de los retos.
     *
     * @param card Contenedor VBox que representa la tarjeta visual.
     */
    private void configurarEfectoTarjeta(VBox card) {
        String base = "-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.08), 12, 0, 0, 4);";
        String hover = "-fx-background-color: #ffffff; -fx-border-color: #0099AB; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.25), 16, 0, 0, 6);";
        card.setStyle(base);
        card.setOnMouseEntered(e -> card.setStyle(hover));
        card.setOnMouseExited(e -> card.setStyle(base));
    }

    /**
     * Despliega un cuadro de dialogo de error estandar en caso de excepciones.
     *
     * @param titulo  Encabezado del error.
     * @param mensaje Detalle del mensaje de excepcion.
     */
    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
