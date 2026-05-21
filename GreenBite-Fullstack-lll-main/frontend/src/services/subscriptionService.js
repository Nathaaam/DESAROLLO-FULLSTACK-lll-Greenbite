// Capa de servicio frontend — llama al BFF
import axios from "axios";

const BFF_URL = process.env.REACT_APP_BFF_URL || "http://localhost:8080/api";

const api = axios.create({
  baseURL: BFF_URL,
  timeout: 10000,
});

// Interceptor: adjunta JWT en cada request
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("gb_token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const subscriptionService = {
  getByUserId: (userId) => api.get(`/suscripciones/usuario/${userId}`).then((r) => r.data),
  pause: (id) => api.patch(`/suscripciones/${id}/pausar`).then((r) => r.data),
  cancel: (id) => api.delete(`/suscripciones/${id}`).then((r) => r.data),
  create: (payload) => api.post("/suscripciones", payload).then((r) => r.data),
};

export const catalogService = {
  getProducts: (ciudad) => api.get(`/catalogo?ciudad=${ciudad}`).then((r) => r.data),
};

export const greenPointsService = {
  getBalance: (userId) => api.get(`/greenpoints/${userId}`).then((r) => r.data),
  redeem: (userId, points) => api.post(`/greenpoints/${userId}/canjear`, { points }).then((r) => r.data),
};
