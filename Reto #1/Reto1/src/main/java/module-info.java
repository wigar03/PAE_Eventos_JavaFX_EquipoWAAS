module org.uam.reto1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uam.reto1 to javafx.fxml;
    opens org.uam.reto1.model to javafx.base, javafx.fxml;

    exports org.uam.reto1;
    exports org.uam.reto1.model;
}