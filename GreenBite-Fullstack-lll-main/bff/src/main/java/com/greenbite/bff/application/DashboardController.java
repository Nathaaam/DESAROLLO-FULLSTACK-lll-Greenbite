package com.greenbite.bff.application;

import com.greenbite.bff.domain.DashboardData;
import com.greenbite.bff.infrastructure.MicroserviceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador BFF que agrega datos de múltiples microservicios
 * en una única respuesta para el dashboard del frontend.
 *
 * Patrón: BFF — evita que el frontend haga múltiples llamadas HTTP.
 * Un solo endpoint entrega suscripción + catálogo + puntos.
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final MicroserviceClient microserviceClient;

    /**
     * Devuelve todos los datos necesarios para renderizar el dashboard.
     * GET /api/dashboard/{userId}
     */
    @GetMapping("/{userId}")
    public Mono<ResponseEntity<DashboardData>> getDashboard(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "Santiago") String ciudad) {

        Mono<DashboardData> dashboardMono = Mono.zip(
                microserviceClient.getSubscriptionByUser(userId)
                        .defaultIfEmpty(new com.greenbite.bff.domain.Subscription()),
                microserviceClient.getProductsByCity(ciudad)
        ).map(tuple -> DashboardData.builder()
                .subscription(tuple.getT1())
                .products(tuple.getT2())
                .greenPoints(tuple.getT1().getGreenPoints())
                .userName("Usuario " + userId)
                .build()
        );

        return dashboardMono.map(ResponseEntity::ok);
    }
}
