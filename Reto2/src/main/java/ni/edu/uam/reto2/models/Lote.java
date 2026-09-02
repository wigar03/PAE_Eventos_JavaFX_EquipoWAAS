package ni.edu.uam.reto2.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modelo de datos que representa un Lote de Granos en la cooperativa.
 * 
 * Contiene los datos descriptivos del lote, fechas operativas y tipo de grano.
 * Incluye getters y setters estándar para compatibilidad total con PropertyValueFactory de JavaFX,
 * así como equals y hashCode basados en el identificador único del lote.
 * 
 * @author Equipo WAAS
 */
public class Lote {

    // Identificador único o código del lote (ej. L-001)
    private String idLote;

    // Nombre descriptivo del producto (ej. Maíz blanco, Café especial)
    private String nombreProducto;

    // Cantidad del lote expresada en kilogramos
    private String cantidadKilos;

    // Fecha en la que el lote fue recibido/entregado en el almacén
    private LocalDate fechaEntrega;

    // Fecha límite de vigencia o caducidad estimada del lote
    private LocalDate fechaCaducidad;

    // Clasificación o variedad del grano (ej. Café, Frijol, Maíz, Arroz, Trigo)
    private String tipoGrano;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Lote() {
    }

    /**
     * Constructor parametrizado para instanciar un nuevo lote con todos sus atributos.
     * 
     * @param idLote Código único del lote.
     * @param nombreProducto Nombre del producto.
     * @param cantidadKilos Cantidad en kilogramos.
     * @param fechaEntrega Fecha de recepción.
     * @param fechaCaducidad Fecha de caducidad.
     * @param tipoGrano Categoría o tipo de grano.
     */
    public Lote(String idLote, String nombreProducto, String cantidadKilos, LocalDate fechaEntrega, LocalDate fechaCaducidad, String tipoGrano) {
        this.idLote = idLote;
        this.nombreProducto = nombreProducto;
        this.cantidadKilos = cantidadKilos;
        this.fechaEntrega = fechaEntrega;
        this.fechaCaducidad = fechaCaducidad;
        this.tipoGrano = tipoGrano;
    }

    // --- Getters y Setters requeridos por PropertyValueFactory de JavaFX ---

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidadKilos() {
        return cantidadKilos;
    }

    public void setCantidadKilos(String cantidadKilos) {
        this.cantidadKilos = cantidadKilos;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipoGrano() {
        return tipoGrano;
    }

    public void setTipoGrano(String tipoGrano) {
        this.tipoGrano = tipoGrano;
    }

    /**
     * Comprueba igualdad entre dos lotes basada exclusivamente en su código idLote.
     * Esencial para que List.indexOf(lote) funcione correctamente en las operaciones del DAO.
     * 
     * @param o Objeto a comparar.
     * @return true si ambos lotes tienen el mismo idLote.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return Objects.equals(idLote, lote.idLote);
    }

    /**
     * Genera el hash code basado en el idLote.
     * 
     * @return Valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idLote);
    }

    /**
     * Representación textual del lote para depuración y registros.
     * 
     * @return Cadena formateada con los campos del lote.
     */
    @Override
    public String toString() {
        return "Lote{" +
                "idLote='" + idLote + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", cantidadKilos='" + cantidadKilos + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", fechaCaducidad=" + fechaCaducidad +
                ", tipoGrano='" + tipoGrano + '\'' +
                '}';
    }
}
