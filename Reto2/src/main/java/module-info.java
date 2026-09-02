module ni.edu.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens ni.edu.uam.reto2 to javafx.fxml;
    opens ni.edu.uam.reto2.controllers to javafx.fxml;
    opens ni.edu.uam.reto2.models to javafx.base;

    exports ni.edu.uam.reto2;
    exports ni.edu.uam.reto2.controllers;
    exports ni.edu.uam.reto2.models;
}