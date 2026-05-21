package com.greenbite.suscripciones.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO para la creación de una nueva suscripción.
 * Patrón: DTO — separa la capa de transporte del dominio.
 */
@Data
public class SubscriptionRequest {

    @NotNull(message = "El userId es obligatorio")
    private Long userId;

    @NotBlank(message = "El plan es obligatorio")
    private String plan;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;
}
