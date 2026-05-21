package com.greenbite.bff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de dominio BFF — agrega datos de múltiples microservicios
 * para el dashboard del usuario autenticado.
 * Patrón: BFF + DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardData {

    private Subscription subscription;
    private List<Product> products;
    private int greenPoints;
    private String userName;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String id;
        private String nombre;
        private String categoria;
        private double precio;
        private String ciudad;
    }
}
