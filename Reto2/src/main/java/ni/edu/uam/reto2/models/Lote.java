package ni.edu.uam.reto2.models;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Modelo de datos que representa un lote de granos en la cooperativa.
 * <p>
 * Encapsula la informacion correspondiente a un lote registrado: identificador unico,
 * nombre del producto, cantidad en kilogramos, fechas de entrega y caducidad, y tipo de grano.
 * Proporciona metodos accesores, constructores y sobreescrituras de {@link #equals(Object)}
 * y {@link #hashCode()} basadas en el {@code idLote} para identificar de forma univoca cada registro.
 */
public class Lote {

    /** Identificador unico del lote (ej. LOTE-001) */
    private String idLote;

    /** Nombre o descripcion del producto (ej. Cafe Bourbon Especial) */
    private String nombreProducto;

    /** Cantidad en kilogramos del lote */
    private String cantidadKilos;

    /** Fecha en que se recibio o entrego el lote */
    private LocalDate fechaEntrega;

    /** Fecha limite de caducidad del producto */
    private LocalDate fechaCaducidad;

    /** Variedad o clasificacion del grano (Cafe, Frijol, Maiz, Arroz, Trigo) */
    private String tipoGrano;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Lote() {
    }

    /**
     * Constructor completo con todos los atributos de un lote.
     *
     * @param idLote          Identificador unico del lote.
     * @param nombreProducto  Nombre o descripcion del producto.
     * @param cantidadKilos   Cantidad en kilogramos.
     * @param fechaEntrega    Fecha de entrega o recepcion.
     * @param fechaCaducidad  Fecha de caducidad estimada.
     * @param tipoGrano       Tipo o clasificacion del grano.
     */
    public Lote(String idLote, String nombreProducto, String cantidadKilos, LocalDate fechaEntrega, LocalDate fechaCaducidad, String tipoGrano) {
        this.idLote = idLote;
        this.nombreProducto = nombreProducto;
        this.cantidadKilos = cantidadKilos;
        this.fechaEntrega = fechaEntrega;
        this.fechaCaducidad = fechaCaducidad;
        this.tipoGrano = tipoGrano;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================

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
     * Determina si dos lotes son iguales basandose exclusivamente en su {@code idLote}.
     *
     * @param o Objeto a comparar.
     * @return true si ambos lotes tienen el mismo idLote; false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return Objects.equals(idLote, lote.idLote);
    }

    /**
     * Genera el codigo hash coherente con el metodo equals basado en {@code idLote}.
     *
     * @return Valor hash entero.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idLote);
    }

    /**
     * Representacion textual amigable del objeto Lote.
     *
     * @return Cadena con los atributos del lote.
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
