module org.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uam.reto2 to javafx.fxml;
    opens org.uam.reto2.model to javafx.base, javafx.fxml;

    exports org.uam.reto2;
    exports org.uam.reto2.model;
}