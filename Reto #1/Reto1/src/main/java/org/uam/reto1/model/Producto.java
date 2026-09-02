package org.uam.reto1.model;

/**
 * Modelo de datos que representa un producto del inventario de la pulpería.
 * 
 * Contiene los atributos del producto comercial (código, nombre, precio y existencia)
 * con métodos JavaBean estándar requeridos por {@link javafx.scene.control.cell.PropertyValueFactory}
 * para su enlace con la tabla.
 * 
 * @author Equipo WAAS
 */
public class Producto {

    // Código identificador único del producto
    private String codigo;

    // Nombre o descripción comercial
    private String nombre;

    // Precio unitario de venta
    private double precio;

    // Cantidad disponible en existencia
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    /**
     * Constructor parametrizado completo.
     * 
     * @param codigo Código del producto.
     * @param nombre Nombre descriptivo.
     * @param precio Precio unitario.
     * @param cantidad Existencias en stock.
     */
    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // --- Métodos Getters y Setters ---

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Representación en cadena para depuración.
     * 
     * @return Cadena con los valores del producto.
     */
    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}
