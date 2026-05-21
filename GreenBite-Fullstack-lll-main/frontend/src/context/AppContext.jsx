// Patrón: Observer via Context API + useReducer
import React, { createContext, useContext, useReducer } from "react";

/**
 * AppContext — Estado global de la aplicación GreenBite.
 * Patrón: Observer (Context API + useReducer)
 * Problema resuelto: evitar prop-drilling de autenticación y GreenPoints
 * a través de múltiples niveles de componentes.
 */

const initialState = {
  user: null,
  greenPoints: 0,
  isAuthenticated: false,
  notifications: [],
};

function appReducer(state, action) {
  switch (action.type) {
    case "LOGIN":
      return { ...state, user: action.payload, isAuthenticated: true };
    case "LOGOUT":
      return { ...initialState };
    case "UPDATE_POINTS":
      return { ...state, greenPoints: action.payload };
    case "ADD_NOTIFICATION":
      return { ...state, notifications: [action.payload, ...state.notifications] };
    case "CLEAR_NOTIFICATIONS":
      return { ...state, notifications: [] };
    default:
      return state;
  }
}

const AppContext = createContext(null);

export function AppProvider({ children }) {
  const [state, dispatch] = useReducer(appReducer, initialState);

  const login = (userData) => dispatch({ type: "LOGIN", payload: userData });
  const logout = () => dispatch({ type: "LOGOUT" });
  const updatePoints = (points) => dispatch({ type: "UPDATE_POINTS", payload: points });
  const addNotification = (msg) => dispatch({ type: "ADD_NOTIFICATION", payload: { id: Date.now(), msg } });

  return (
    <AppContext.Provider value={{ state, login, logout, updatePoints, addNotification }}>
      {children}
    </AppContext.Provider>
  );
}

// Hook para consumir el contexto
export function useAppContext() {
  const ctx = useContext(AppContext);
  if (!ctx) throw new Error("useAppContext debe usarse dentro de AppProvider");
  return ctx;
}
