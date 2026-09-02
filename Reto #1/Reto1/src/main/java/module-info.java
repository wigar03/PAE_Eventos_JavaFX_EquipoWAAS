module org.uam.reto1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uam.reto1;
    opens org.uam.reto1.model;

    exports org.uam.reto1;
    exports org.uam.reto1.model;
}