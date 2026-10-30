package com.example.stockmaster

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ProductoTest {

    @Test
    fun testCrearProducto() {
        val producto = Producto(
            id = 1,
            nombre = "Laptop",
            categoria = "Electrónica",
            precio = 1200.0,
            cantidad = 5
        )

        assertEquals(1, producto.id)
        assertEquals("Laptop", producto.nombre)
        assertEquals("Electrónica", producto.categoria)
        assertEquals(1200.0, producto.precio, 0.001)
        assertEquals(5, producto.cantidad)
    }

    @Test
    fun testModificarProducto() {
        val original = Producto(
            id = 1,
            nombre = "Laptop",
            categoria = "Electrónica",
            precio = 1200.0,
            cantidad = 5
        )

        val modificado = original.copy(
            nombre = "Laptop Pro",
            categoria = "Cómputo",
            precio = 1500.0,
            cantidad = 10
        )

        assertEquals(original.id, modificado.id)
        assertNotEquals(original.nombre, modificado.nombre)
        assertEquals("Laptop Pro", modificado.nombre)
        assertEquals("Cómputo", modificado.categoria)
        assertEquals(1500.0, modificado.precio, 0.001)
        assertEquals(10, modificado.cantidad)
    }
}
