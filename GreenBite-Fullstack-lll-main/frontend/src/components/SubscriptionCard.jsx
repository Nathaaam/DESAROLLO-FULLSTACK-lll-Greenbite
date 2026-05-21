import React from "react";

/**
 * SubscriptionCard — Componente presentacional (Dumb Component).
 * Solo recibe props y renderiza la UI. Sin lógica de negocio.
 * Patrón: Container/Presentational
 */
export function SubscriptionCard({ subscription, onPause, onCancel }) {
  if (!subscription) return <p>No tienes suscripción activa.</p>;

  const statusColor = {
    ACTIVA:   "#2e7d32",
    PAUSADA:  "#e65100",
    CANCELADA:"#c62828",
  };

  return (
    <div style={{ border: "1px solid #ccc", borderRadius: 8, padding: 16, maxWidth: 400 }}>
      <h3 style={{ color: "#1e2a3a" }}>Mi Suscripción</h3>
      <p><strong>Plan:</strong> {subscription.plan}</p>
      <p>
        <strong>Estado:</strong>{" "}
        <span style={{ color: statusColor[subscription.estado] || "#000", fontWeight: 600 }}>
          {subscription.estado}
        </span>
      </p>
      <p><strong>Próxima entrega:</strong> {subscription.proximaEntrega}</p>
      <p><strong>Ciudad:</strong> {subscription.ciudad}</p>

      <div style={{ marginTop: 12, display: "flex", gap: 8 }}>
        {subscription.estado === "ACTIVA" && (
          <button
            onClick={() => onPause(subscription.id)}
            style={{ background: "#e65100", color: "white", border: "none", padding: "8px 16px", borderRadius: 4, cursor: "pointer" }}
          >
            Pausar
          </button>
        )}
        <button
          onClick={() => onCancel(subscription.id)}
          style={{ background: "#c62828", color: "white", border: "none", padding: "8px 16px", borderRadius: 4, cursor: "pointer" }}
        >
          Cancelar
        </button>
      </div>
    </div>
  );
}
