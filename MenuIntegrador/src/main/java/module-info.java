/**
 * Módulo principal del Proyecto Integrador.
 * 
 * Centraliza la navegación entre los tres retos desarrollados en la guía práctica,
 * consumiéndolos como dependencias modulares de biblioteca dentro de la misma máquina virtual de JavaFX.
 * 
 * @author Equipo WAAS
 */
module ni.edu.uam.menu {
    // Dependencias básicas de la plataforma JavaFX
    requires javafx.controls;
    requires javafx.fxml;

    // Dependencias modulares de los tres retos independientes
    requires org.uam.reto1;   // Reto #1: Inventario de Pulpería
    requires ni.edu.uam.reto2; // Reto #2: Control de Lotes Agrícolas
    requires org.uam.reto2;   // Reto #3: Tienda de Artesanías Nicaragüenses

    // Apertura reflexiva para permitir que JavaFX FXML cargue los controladores
    opens ni.edu.uam.menu to javafx.fxml;

    // Exportación del paquete para ejecución de la aplicación
    exports ni.edu.uam.menu;
}
