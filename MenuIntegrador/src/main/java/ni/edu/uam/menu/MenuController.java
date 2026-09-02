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

public class MenuController {

    @FXML private Label lblVersionJava;
    @FXML private Button btnReto1;
    @FXML private Button btnReto2;
    @FXML private Button btnReto3;
    @FXML private VBox cardReto1;
    @FXML private VBox cardReto2;
    @FXML private VBox cardReto3;

    private Stage stageReto1;
    private Stage stageReto2;
    private Stage stageReto3;

    @FXML
    public void initialize() {
        lblVersionJava.setText("Java: " + System.getProperty("java.version") + " (JavaFX 21)");

        configurarEfectoBoton(btnReto1);
        configurarEfectoBoton(btnReto2);
        configurarEfectoBoton(btnReto3);

        configurarEfectoTarjeta(cardReto1);
        configurarEfectoTarjeta(cardReto2);
        configurarEfectoTarjeta(cardReto3);
    }

    @FXML
    protected void abrirReto1() {
        try {
            Stage menuStage = getMenuStage();

            stageReto1 = new Stage();
            org.uam.reto1.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto1.close();
                if (menuStage != null) menuStage.show();
            });

            stageReto1.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            new org.uam.reto1.HelloApplication().start(stageReto1);

            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #1", ex.getMessage());
        }
    }

    @FXML
    protected void abrirReto2() {
        try {
            Stage menuStage = getMenuStage();

            stageReto2 = new Stage();
            ni.edu.uam.reto2.LoteApplication.setOnVolverAlMenu(() -> {
                stageReto2.close();
                if (menuStage != null) menuStage.show();
            });

            stageReto2.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            new ni.edu.uam.reto2.LoteApplication().start(stageReto2);

            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #2", ex.getMessage());
        }
    }

    @FXML
    protected void abrirReto3() {
        try {
            Stage menuStage = getMenuStage();

            stageReto3 = new Stage();
            org.uam.reto2.HelloApplication.setOnVolverAlMenu(() -> {
                stageReto3.close();
                if (menuStage != null) menuStage.show();
            });

            stageReto3.setOnCloseRequest(e -> {
                if (menuStage != null) menuStage.show();
            });

            new org.uam.reto2.HelloApplication().start(stageReto3);

            if (menuStage != null) menuStage.hide();

        } catch (Exception ex) {
            mostrarError("Error al abrir Reto #3", ex.getMessage());
        }
    }

    @FXML
    protected void salirDeLaAplicacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("Cerrar Sistema Integrador");
        alert.setContentText("¿Deseas cerrar el menú principal y salir de la aplicación?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (stageReto1 != null && stageReto1.isShowing()) stageReto1.close();
            if (stageReto2 != null && stageReto2.isShowing()) stageReto2.close();
            if (stageReto3 != null && stageReto3.isShowing()) stageReto3.close();
            Platform.exit();
        }
    }

    private Stage getMenuStage() {
        if (btnReto1 != null && btnReto1.getScene() != null) {
            return (Stage) btnReto1.getScene().getWindow();
        }
        return null;
    }

    private void configurarEfectoBoton(Button btn) {
        String base = "-fx-background-color: #0099AB; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        String hover = "-fx-background-color: #007d8c; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-padding: 10 18; -fx-background-radius: 8; -fx-cursor: hand;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    private void configurarEfectoTarjeta(VBox card) {
        String base = "-fx-background-color: #ffffff; -fx-border-color: #e2e8f0; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.08), 12, 0, 0, 4);";
        String hover = "-fx-background-color: #ffffff; -fx-border-color: #0099AB; -fx-border-width: 1.5; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 24; -fx-effect: dropshadow(three-pass-box, rgba(0, 153, 171, 0.25), 16, 0, 0, 6);";
        card.setStyle(base);
        card.setOnMouseEntered(e -> card.setStyle(hover));
        card.setOnMouseExited(e -> card.setStyle(base));
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
