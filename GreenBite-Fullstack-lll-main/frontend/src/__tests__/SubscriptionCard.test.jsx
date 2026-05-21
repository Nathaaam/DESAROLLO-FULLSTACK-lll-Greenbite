import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import { SubscriptionCard } from "../components/SubscriptionCard";
describe("SubscriptionCard", () => {
  const mockSub = {
    id: 1,
    plan: "Semanal",
    estado: "ACTIVA",
    proximaEntrega: "2026-05-15",
    ciudad: "Santiago",
  };

  test("muestra datos de la suscripción activa", () => {
    render(<SubscriptionCard subscription={mockSub} onPause={jest.fn()} onCancel={jest.fn()} />);
    expect(screen.getByText("Semanal")).toBeInTheDocument();
    expect(screen.getByText("ACTIVA")).toBeInTheDocument();
    expect(screen.getByText("Santiago")).toBeInTheDocument();
  });

  test("llama onPause al hacer click en Pausar", async () => {
    const onPause = jest.fn();
    render(<SubscriptionCard subscription={mockSub} onPause={onPause} onCancel={jest.fn()} />);
    fireEvent.click(screen.getByText("Pausar"));
    expect(onPause).toHaveBeenCalledWith(1);
  });

  test("muestra mensaje cuando no hay suscripción", () => {
    render(<SubscriptionCard subscription={null} onPause={jest.fn()} onCancel={jest.fn()} />);
    expect(screen.getByText("No tienes suscripción activa.")).toBeInTheDocument();
  });
});
