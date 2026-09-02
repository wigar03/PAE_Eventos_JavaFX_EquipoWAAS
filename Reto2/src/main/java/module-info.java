module ni.edu.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens ni.edu.uam.reto2;
    opens ni.edu.uam.reto2.controllers;
    opens ni.edu.uam.reto2.models;

    exports ni.edu.uam.reto2;
    exports ni.edu.uam.reto2.controllers;
    exports ni.edu.uam.reto2.models;
}