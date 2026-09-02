module ni.edu.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.reto2 to javafx.fxml;
    opens ni.edu.uam.reto2.controllers to javafx.fxml;
    opens ni.edu.uam.reto2.models to javafx.base, javafx.fxml;

    exports ni.edu.uam.reto2;
    exports ni.edu.uam.reto2.controllers;
    exports ni.edu.uam.reto2.models;
}