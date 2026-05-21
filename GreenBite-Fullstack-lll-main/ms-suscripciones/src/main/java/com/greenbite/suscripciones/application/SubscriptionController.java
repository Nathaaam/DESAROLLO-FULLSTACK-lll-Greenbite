package com.greenbite.suscripciones.application;

import com.greenbite.suscripciones.domain.Subscription;
import com.greenbite.suscripciones.infrastructure.SubscriptionRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST del microservicio de suscripciones.
 * Expone los endpoints CRUD y operaciones de negocio.
 * Patrón: Service Layer + Repository
 */
@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionRepository repository;

    /**
     * Crea una nueva suscripción.
     * POST /api/suscripciones
     */
    @PostMapping
    public ResponseEntity<Subscription> create(@Valid @RequestBody SubscriptionRequest req) {
        Subscription sub = Subscription.builder()
                .userId(req.getUserId())
                .plan(req.getPlan())
                .ciudad(req.getCiudad())
                .estado(Subscription.EstadoSuscripcion.ACTIVA)
                .proximaEntrega(LocalDate.now().plusWeeks(1))
                .greenPoints(0)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(sub));
    }

    /**
     * Lista todas las suscripciones de un usuario.
     * GET /api/suscripciones/usuario/{userId}
     */
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Subscription>> getByUser(@PathVariable Long userId) {
        List<Subscription> subs = repository.findByUserId(userId);
        return subs.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(subs);
    }

    /**
     * Obtiene una suscripción por ID.
     * GET /api/suscripciones/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Pausa una suscripción activa.
     * PATCH /api/suscripciones/{id}/pausar
     */
    @PatchMapping("/{id}/pausar")
    public ResponseEntity<Subscription> pause(@PathVariable Long id) {
        return repository.findById(id).map(sub -> {
            sub.pausar();
            return ResponseEntity.ok(repository.save(sub));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Reactiva una suscripción pausada.
     * PATCH /api/suscripciones/{id}/reactivar
     */
    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<Subscription> reactivate(@PathVariable Long id) {
        return repository.findById(id).map(sub -> {
            sub.reactivar();
            return ResponseEntity.ok(repository.save(sub));
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cancela (elimina lógicamente) una suscripción.
     * DELETE /api/suscripciones/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        return repository.findById(id).map(sub -> {
            sub.cancelar();
            repository.save(sub);
            return ResponseEntity.<Void>noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
