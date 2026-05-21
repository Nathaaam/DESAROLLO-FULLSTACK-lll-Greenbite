package com.greenbite.suscripciones.infrastructure;

import com.greenbite.suscripciones.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para persistencia de suscripciones.
 * Patrón: Repository — abstrae el acceso a datos del dominio.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Encuentra la suscripción activa de un usuario.
     */
    Optional<Subscription> findByUserIdAndEstado(Long userId, Subscription.EstadoSuscripcion estado);

    /**
     * Todas las suscripciones de un usuario.
     */
    List<Subscription> findByUserId(Long userId);
}
