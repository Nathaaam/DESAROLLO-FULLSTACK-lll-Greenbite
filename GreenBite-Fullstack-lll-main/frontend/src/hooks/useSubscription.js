// Patrón: Custom Hook — encapsula lógica de estado de suscripciones
import { useState, useEffect, useCallback } from "react";
import { subscriptionService } from "../services/subscriptionService";

/**
 * useSubscription — Hook personalizado para gestionar el ciclo de vida
 * de suscripciones del usuario autenticado.
 *
 * Patrón aplicado: Custom Hook
 * Problema resuelto: evita duplicar lógica de fetch/estado en cada componente
 * que necesite datos de suscripción.
 */
export function useSubscription(userId) {
  const [subscription, setSubscription] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchSubscription = useCallback(async () => {
    if (!userId) return;
    setLoading(true);
    setError(null);
    try {
      const data = await subscriptionService.getByUserId(userId);
      setSubscription(data);
    } catch (err) {
      setError(err.message || "Error al obtener suscripción");
    } finally {
      setLoading(false);
    }
  }, [userId]);

  useEffect(() => {
    fetchSubscription();
  }, [fetchSubscription]);

  const pauseSubscription = async (id) => {
    try {
      const updated = await subscriptionService.pause(id);
      setSubscription(updated);
    } catch (err) {
      setError(err.message);
    }
  };

  const cancelSubscription = async (id) => {
    try {
      await subscriptionService.cancel(id);
      setSubscription(null);
    } catch (err) {
      setError(err.message);
    }
  };

  return { subscription, loading, error, pauseSubscription, cancelSubscription, refetch: fetchSubscription };
}
