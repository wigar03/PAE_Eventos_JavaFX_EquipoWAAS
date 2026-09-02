/**
 * Módulo del Reto #3: Sistema de Tienda de Artesanías Nicaragüenses.
 * 
 * Implementa una interfaz de escritorio con navegación multinivel basada en
 * MenuBar, ToolBar de acceso rápido, atajos de teclado y gestión de catálogo.
 * 
 * @author Equipo WAAS
 */
module org.uam.reto2 {
    // Dependencias fundamentales de JavaFX para componentes gráficos y FXML
    requires javafx.controls;
    requires javafx.fxml;

    // Apertura reflexiva de paquetes para FXMLLoader y PropertyValueFactory
    opens org.uam.reto2;
    opens org.uam.reto2.model;

    // Exportación pública para permitir la invocación desde MenuIntegrador
    exports org.uam.reto2;
    exports org.uam.reto2.model;
}