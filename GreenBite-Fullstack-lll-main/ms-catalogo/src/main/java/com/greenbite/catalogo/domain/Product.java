package com.greenbite.catalogo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * Entidad de dominio que representa un producto del catálogo GreenBite.
 * Almacenada en MongoDB (Database per Service pattern).
 * Patrón: Domain Model
 */
@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    private String nombre;

    private String descripcion;

    private String categoria;     // VERDURA, FRUTA, LACTEO, CEREAL, etc.

    private double precio;

    private int stock;

    @Indexed
    private String ciudad;        // Disponibilidad por ciudad

    private String temporada;     // TODO_EL_ANIO, VERANO, INVIERNO, OTONO, PRIMAVERA

    private boolean disponible;

    private List<String> tags;   // ej: ["organico", "sin-gluten", "local"]

    /**
     * Regla de negocio: verifica si hay stock suficiente.
     */
    public boolean tieneStock(int cantidad) {
        return this.stock >= cantidad;
    }

    /**
     * Regla de negocio: reduce el stock al reservar unidades.
     */
    public void reservar(int cantidad) {
        if (!tieneStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + nombre);
        }
        this.stock -= cantidad;
        this.disponible = this.stock > 0;
    }
}
