package com.greenbite.catalogo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del dominio Product.
 * Verifica las reglas de negocio encapsuladas en la entidad.
 */
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id("abc123")
                .nombre("Tomate Cherry Orgánico")
                .descripcion("Tomates cherry de producción local")
                .categoria("VERDURA")
                .precio(2990.0)
                .stock(100)
                .ciudad("Santiago")
                .temporada("TODO_EL_ANIO")
                .disponible(true)
                .tags(List.of("organico", "local"))
                .build();
    }

    @Test
    @DisplayName("tieneStock devuelve true cuando hay suficiente stock")
    void testTieneStockSuficiente() {
        assertTrue(product.tieneStock(50));
    }

    @Test
    @DisplayName("tieneStock devuelve false cuando el stock es insuficiente")
    void testTieneStockInsuficiente() {
        assertFalse(product.tieneStock(200));
    }

    @Test
    @DisplayName("reservar reduce el stock correctamente")
    void testReservarReduceStock() {
        product.reservar(30);
        assertEquals(70, product.getStock());
        assertTrue(product.isDisponible());
    }

    @Test
    @DisplayName("reservar con stock exacto deja disponible en false")
    void testReservarTodoElStockDeshabilitaProducto() {
        product.reservar(100);
        assertEquals(0, product.getStock());
        assertFalse(product.isDisponible());
    }

    @Test
    @DisplayName("reservar más de lo disponible lanza excepción")
    void testReservarMasDelStockLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> product.reservar(101));
    }

    @Test
    @DisplayName("El producto tiene los datos básicos correctos")
    void testProductoDatosBasicos() {
        assertEquals("Tomate Cherry Orgánico", product.getNombre());
        assertEquals("Santiago", product.getCiudad());
        assertEquals(2990.0, product.getPrecio());
    }
}
