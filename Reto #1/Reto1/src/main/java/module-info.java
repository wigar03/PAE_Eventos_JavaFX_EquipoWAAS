/**
 * Módulo del Reto #1: Sistema de Inventario de Pulpería.
 * 
 * Implementa un catálogo de productos con filtrado dinámico reactivo,
 * eventos de teclado y selección rápida en TableView mediante JavaFX.
 * 
 * @author Equipo WAAS
 */
module org.uam.reto1 {
    // Módulos esenciales de JavaFX con exportación transitiva para clientes de la API gráfica
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    // Apertura de paquetes para reflexión en tiempo de ejecución (PropertyValueFactory y FXMLLoader)
    opens org.uam.reto1;
    opens org.uam.reto1.model;

    // Exportación pública para permitir integración modular desde MenuIntegrador
    exports org.uam.reto1;
    exports org.uam.reto1.model;
}