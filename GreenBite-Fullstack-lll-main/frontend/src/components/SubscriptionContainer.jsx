// Patrón: Container/Presentational — componente contenedor (lógica)
import React from "react";
import { SubscriptionCard } from "./SubscriptionCard";
import { useSubscription } from "../hooks/useSubscription";
import { useAppContext } from "../context/AppContext";

/**
 * SubscriptionContainer — Componente contenedor.
 * Gestiona estado, llama al hook y pasa datos al componente presentacional.
 * Patrón: Container/Presentational
 */
export function SubscriptionContainer() {
  const { state } = useAppContext();
  const { subscription, loading, error, pauseSubscription, cancelSubscription } = useSubscription(
    state.user?.id
  );

  if (!state.isAuthenticated) return <p>Debes iniciar sesión.</p>;
  if (loading) return <p>Cargando suscripción...</p>;
  if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

  return (
    <SubscriptionCard
      subscription={subscription}
      onPause={pauseSubscription}
      onCancel={cancelSubscription}
    />
  );
}
