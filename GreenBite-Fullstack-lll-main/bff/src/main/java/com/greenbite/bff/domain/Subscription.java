package com.greenbite.bff.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de suscripción usado por el BFF para exponer al frontend.
 * Patrón: DTO (Data Transfer Object)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    private Long id;
    private Long userId;
    private String plan;
    private String estado;
    private String proximaEntrega;
    private String ciudad;
    private int greenPoints;
}
