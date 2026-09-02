package org.uam.reto1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    @Test
    public void testCrearProducto() {
        Producto p = new Producto("P100", "Leche Agria 1L", 28.0, 12);

        assertEquals("P100", p.getCodigo());
        assertEquals("Leche Agria 1L", p.getNombre());
        assertEquals(28.0, p.getPrecio());
        assertEquals(12, p.getCantidad());
    }

    @Test
    public void testSettersProducto() {
        Producto p = new Producto();
        p.setCodigo("P101");
        p.setNombre("Pan Dulce");
        p.setPrecio(15.0);
        p.setCantidad(20);

        assertEquals("P101", p.getCodigo());
        assertEquals("Pan Dulce", p.getNombre());
        assertEquals(15.0, p.getPrecio());
        assertEquals(20, p.getCantidad());
    }
}
