package com.greenbite.suscripciones.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entidad de dominio que representa una suscripción GreenBite.
 * Patrón: Domain Model — encapsula las reglas de negocio de la suscripción.
 */
@Entity
@Table(name = "subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String plan;          // SEMANAL, QUINCENAL, MENSUAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSuscripcion estado;

    private LocalDate proximaEntrega;

    @Column(nullable = false)
    private String ciudad;

    @Builder.Default
    private int greenPoints = 0;

    /**
     * Regla de negocio: pausa la suscripción solo si está activa.
     */
    public void pausar() {
        if (this.estado != EstadoSuscripcion.ACTIVA) {
            throw new IllegalStateException("Solo se puede pausar una suscripción ACTIVA");
        }
        this.estado = EstadoSuscripcion.PAUSADA;
    }

    /**
     * Regla de negocio: reactiva la suscripción si está pausada.
     */
    public void reactivar() {
        if (this.estado != EstadoSuscripcion.PAUSADA) {
            throw new IllegalStateException("Solo se puede reactivar una suscripción PAUSADA");
        }
        this.estado = EstadoSuscripcion.ACTIVA;
    }

    /**
     * Regla de negocio: cancela la suscripción.
     */
    public void cancelar() {
        this.estado = EstadoSuscripcion.CANCELADA;
    }

    public enum EstadoSuscripcion {
        ACTIVA, PAUSADA, CANCELADA
    }
}
