package ni.edu.uam.reto2.models;

import java.time.LocalDate;
import java.util.Objects;

public class Lote {

    private String idLote;
    private String nombreProducto;
    private String cantidadKilos;
    private LocalDate fechaEntrega;
    private LocalDate fechaCaducidad;
    private String tipoGrano;

    public Lote() {
    }

    public Lote(String idLote, String nombreProducto, String cantidadKilos, LocalDate fechaEntrega, LocalDate fechaCaducidad, String tipoGrano) {
        this.idLote = idLote;
        this.nombreProducto = nombreProducto;
        this.cantidadKilos = cantidadKilos;
        this.fechaEntrega = fechaEntrega;
        this.fechaCaducidad = fechaCaducidad;
        this.tipoGrano = tipoGrano;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return Objects.equals(idLote, lote.idLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote);
    }

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
