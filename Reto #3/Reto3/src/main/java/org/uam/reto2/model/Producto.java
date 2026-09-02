package org.uam.reto2.model;

/**
 * Modelo de datos que representa una artesanía en el catálogo de la tienda.
 * 
 * Contiene información de identificación, nombre descriptivo, categoría artesanal
 * (Cerámica, Textil, Madera, etc.), precio unitario y cantidad en inventario.
 * Proporciona métodos de acceso estándar para su vinculación con TableColumn de JavaFX.
 * 
 * @author Equipo WAAS
 */
public class Producto {

    // Código alfanumérico del producto artesanal
    private String codigo;

    // Nombre de la pieza artesanal
    private String nombre;

    // Categoría de la artesanía (ej. Cerámica, Textil, Cuero, Madera)
    private String categoria;

    // Precio de venta unitario
    private double precio;

    // Unidades disponibles en stock
    private int cantidad;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Producto() {
    }

    /**
     * Constructor parametrizado para crear una artesanía con todos sus datos.
     * 
     * @param codigo Código identificador.
     * @param nombre Nombre descriptivo de la artesanía.
     * @param categoria Categoría o material principal.
     * @param precio Precio en moneda local.
     * @param cantidad Cantidad disponible.
     */
    public Producto(String codigo, String nombre, String categoria, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    // --- Métodos Getters y Setters requeridos por PropertyValueFactory ---

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
}
