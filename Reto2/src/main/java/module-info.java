module ni.edu.uam.reto2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.reto2 to javafx.fxml;
    exports ni.edu.uam.reto2;
}