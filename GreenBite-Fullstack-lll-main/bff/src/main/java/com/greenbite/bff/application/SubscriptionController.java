package com.greenbite.bff.application;

import com.greenbite.bff.domain.Subscription;
import com.greenbite.bff.infrastructure.MicroserviceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador BFF para operaciones de suscripciones.
 * Delega al MicroserviceClient y transforma la respuesta para el frontend.
 * Patrón: BFF (Backend For Frontend) + Service Layer
 */
@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SubscriptionController {

    private final MicroserviceClient microserviceClient;

    /**
     * Obtiene la suscripción activa del usuario.
     * GET /api/suscripciones/usuario/{userId}
     */
    @GetMapping("/usuario/{userId}")
    public Mono<ResponseEntity<Subscription>> getByUser(@PathVariable Long userId) {
        return microserviceClient.getSubscriptionByUser(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Pausa la suscripción indicada.
     * PATCH /api/suscripciones/{id}/pausar
     */
    @PatchMapping("/{id}/pausar")
    public Mono<ResponseEntity<Subscription>> pause(@PathVariable Long id) {
        return microserviceClient.pauseSubscription(id)
                .map(ResponseEntity::ok);
    }

    /**
     * Cancela (elimina) la suscripción indicada.
     * DELETE /api/suscripciones/{id}
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> cancel(@PathVariable Long id) {
        return microserviceClient.cancelSubscription(id)
                .thenReturn(ResponseEntity.<Void>noContent().build());
    }
}
