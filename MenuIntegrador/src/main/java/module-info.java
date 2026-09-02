module ni.edu.uam.menu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.uam.reto1;
    requires ni.edu.uam.reto2;
    requires org.uam.reto2;

    opens ni.edu.uam.menu to javafx.fxml;
    exports ni.edu.uam.menu;
}
