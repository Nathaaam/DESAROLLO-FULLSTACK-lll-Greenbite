package com.greenbite.suscripciones.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del dominio Subscription.
 * Verifica las reglas de negocio encapsuladas en la entidad.
 */
class SubscriptionTest {

    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = Subscription.builder()
                .id(1L)
                .userId(10L)
                .plan("SEMANAL")
                .estado(Subscription.EstadoSuscripcion.ACTIVA)
                .proximaEntrega(LocalDate.now().plusWeeks(1))
                .ciudad("Santiago")
                .greenPoints(50)
                .build();
    }

    @Test
    @DisplayName("Una suscripción ACTIVA puede pausarse")
    void testPausarSuscripcionActiva() {
        subscription.pausar();
        assertEquals(Subscription.EstadoSuscripcion.PAUSADA, subscription.getEstado());
    }

    @Test
    @DisplayName("No se puede pausar una suscripción ya PAUSADA")
    void testPausarSuscripcionPausadaLanzaExcepcion() {
        subscription.pausar();
        assertThrows(IllegalStateException.class, () -> subscription.pausar());
    }

    @Test
    @DisplayName("Una suscripción PAUSADA puede reactivarse")
    void testReactivarSuscripcionPausada() {
        subscription.pausar();
        subscription.reactivar();
        assertEquals(Subscription.EstadoSuscripcion.ACTIVA, subscription.getEstado());
    }

    @Test
    @DisplayName("No se puede reactivar una suscripción ACTIVA")
    void testReactivarSuscripcionActivaLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> subscription.reactivar());
    }

    @Test
    @DisplayName("Cancelar una suscripción cambia su estado a CANCELADA")
    void testCancelarSuscripcion() {
        subscription.cancelar();
        assertEquals(Subscription.EstadoSuscripcion.CANCELADA, subscription.getEstado());
    }

    @Test
    @DisplayName("Los greenPoints iniciales son correctos")
    void testGreenPointsIniciales() {
        assertEquals(50, subscription.getGreenPoints());
    }
}
