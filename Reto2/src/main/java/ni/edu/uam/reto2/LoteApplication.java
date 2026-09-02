package ni.edu.uam.reto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ni.edu.uam.reto2.controllers.LoteController;

import java.io.IOException;

public class LoteApplication extends Application {

    private static Runnable onVolverAlMenuGlobal;

    public static void setOnVolverAlMenu(Runnable onVolverAlMenu) {
        onVolverAlMenuGlobal = onVolverAlMenu;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoteApplication.class.getResource("lote-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoteController controller = fxmlLoader.getController();
        if (controller != null && onVolverAlMenuGlobal != null) {
            controller.setOnVolverAlMenu(onVolverAlMenuGlobal);
        }

        stage.setTitle("Sistema de Cooperativa - Reto 2");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}