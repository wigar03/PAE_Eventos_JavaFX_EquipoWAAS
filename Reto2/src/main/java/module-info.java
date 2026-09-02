/**
 * Descriptor modular del Reto #2: Sistema de Gestión de Lotes de Cooperativa.
 * 
 * Implementa la arquitectura en capas con patrón DAO y la interfaz genérica CRUD<T>,
 * exponiendo sus componentes para ser consumido tanto de forma autónoma como integrado
 * en el Menú Principal.
 * 
 * @author Equipo WAAS
 */
module ni.edu.uam.reto2 {
    // Módulos base de JavaFX requeridos para UI y enlace de plantillas FXML
    requires javafx.controls;
    requires javafx.fxml;

    // Apertura de paquetes para reflexión en tiempo de ejecución (FXML loaders y PropertyValueFactory)
    opens ni.edu.uam.reto2;
    opens ni.edu.uam.reto2.controllers;
    opens ni.edu.uam.reto2.models;

    // Exportación pública de paquetes para acceso desde otros módulos (ej. MenuIntegrador)
    exports ni.edu.uam.reto2;
    exports ni.edu.uam.reto2.controllers;
    exports ni.edu.uam.reto2.models;
}