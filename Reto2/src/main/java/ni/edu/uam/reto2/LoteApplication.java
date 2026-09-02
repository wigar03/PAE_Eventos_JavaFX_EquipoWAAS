package ni.edu.uam.reto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoteApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo visual FXML
        FXMLLoader fxmlLoader = new FXMLLoader(LoteApplication.class.getResource("lote-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Configura la ventana principal
        stage.setTitle("Sistema de Cooperativa - Reto 2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}