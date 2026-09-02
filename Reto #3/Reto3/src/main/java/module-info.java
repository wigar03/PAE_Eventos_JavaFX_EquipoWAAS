module org.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.uam.reto2;
    opens org.uam.reto2.model;

    exports org.uam.reto2;
    exports org.uam.reto2.model;
}