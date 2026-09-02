module ni.edu.uam.reto2 {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    opens ni.edu.uam.reto2;
    opens ni.edu.uam.reto2.controllers;
    opens ni.edu.uam.reto2.models;

    exports ni.edu.uam.reto2;
    exports ni.edu.uam.reto2.controllers;
    exports ni.edu.uam.reto2.models;
}